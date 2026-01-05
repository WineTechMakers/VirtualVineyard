package bg.tu_varna.sit.virtualvineyard.chain;

import bg.tu_varna.sit.virtualvineyard.dao.BottleDAO;
import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.dao.GrapeDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class BottlingFactory {
    private static final Logger logger = LogManager.getLogger(BottlingFactory.class);
    private final Warehouse wineWarehouse;
    private final Warehouse bottleWarehouse;
    private final GrapeDAO grapeDAO;
    private final BottleDAO bottleDAO;
    private final BottledWineDAO bottledWineDAO;

    public BottlingFactory(Warehouse wineWarehouse, Warehouse bottleWarehouse, GrapeDAO grapeDAO, BottleDAO bottleDAO, BottledWineDAO bottledWineDAO)
    {
        this.wineWarehouse = wineWarehouse;
        this.bottleWarehouse = bottleWarehouse;
        this.grapeDAO = grapeDAO;
        this.bottleDAO = bottleDAO;
        this.bottledWineDAO = bottledWineDAO;
    }

    public BottlingFactory(Warehouse wineWarehouse, Warehouse bottleWarehouse) {
        this(
                wineWarehouse,
                bottleWarehouse,
                new GrapeDAO(),
                new BottleDAO(),
                new BottledWineDAO()
        );
    }

    public void bottleWine(Wine wine, LocalDate productionDate) {
        double liters = computeTotalLitersAvailable(wine.getWineGrapes());

        if (liters <= 0)
        {
            logger.error("Not enough grapes to produce wine");
            throw new IllegalArgumentException("Not enough grapes to produce wine.");
        }

        int totalML = (int) (liters * 1000);

        BottlingInterface chain = initChain();
        if (chain == null)
        {
            logger.error("No bottles available in warehouse '{}'", bottleWarehouse.getName());
            throw new IllegalStateException("No bottles available.");
        }

        List<BottledWine> bottled = chain.handle(totalML, wine);

        if (bottled.isEmpty()) {
            logger.error("No wine produced due to not sufficient quantity of grapes or bottles.");
            throw new IllegalStateException("No wine produced due to not sufficient quantity of grapes or bottles");
        }

        for (BottledWine bw : bottled) {
            bw.setProductionDate(productionDate);
            bw.setWarehouse(wineWarehouse);
            BottledWine existingBw = bottledWineDAO.findByWineAndBottleAndProductionDate(bw.getWine(), bw.getBottle(), bw.getProductionDate());
            if (existingBw != null) {
                existingBw.setQuantity(existingBw.getQuantity() + bw.getQuantity());
                bottledWineDAO.update(existingBw);
            } else {
                bottledWineDAO.create(bw);
            }
        }
        logger.info("Bottled Production of wine '{}' in warehouse '{}'", wine.getName(), wineWarehouse.getName());

        Set<Bottle> usedBottles = new HashSet<>();
        for (BottledWine bw : bottled)
            usedBottles.add(bw.getBottle());

        for (Bottle b : usedBottles) {
            //quantity was already reduced inside the Bottling.java handle() method
            bottleDAO.update(b);
        }
        consumeGrapesForProduction(wine.getWineGrapes());
    }

    private BottlingInterface initChain() {

        List<Bottle> bottles = bottleWarehouse.getBottles();
        if (bottles == null || bottles.isEmpty())
            return null;

        List<Bottle> sorted = new ArrayList<>(bottles);
        sorted.sort((b1, b2) ->
                Integer.compare(b2.getVolume().getVolume(), b1.getVolume().getVolume())
        );

        BottlingInterface first = new Bottling(sorted.getFirst());
        BottlingInterface current = first;

        for (int i = 1; i < sorted.size(); i++) {
            BottlingInterface next = new Bottling(sorted.get(i));
            current.setNext(next);
            current = next;
        }
        return first;
    }

    private double calculateMaxRawBatchKg(List<WineGrape> wineGrapes) {
        double maxTotalMixKg = Double.MAX_VALUE;

        for (WineGrape wg : wineGrapes) {
            double requiredRatio = wg.getPercentage() / 100.0;
            double availableStock = wg.getGrape().getQuantity();

            if (requiredRatio > 0) {
                //how much TOTAL mix could we make if this grape was the only limit?
                //example: Have 10kg, need 50% (0.5). Max mix = 10 / 0.5 = 20kg.
                double maxPossibleWithThisGrape = availableStock / requiredRatio;

                if (maxPossibleWithThisGrape < maxTotalMixKg) {
                    maxTotalMixKg = maxPossibleWithThisGrape;
                }
            }
        }

        //if no grapes or if logic failed
        if (maxTotalMixKg == Double.MAX_VALUE) return 0;

        return maxTotalMixKg;
    }

    public double computeTotalLitersAvailable(List<WineGrape> wineGrapes) {
        //maximum weight of the grape mixture
        double limitingBatchKg = calculateMaxRawBatchKg(wineGrapes);
        double totalLiters = 0;

        for (WineGrape wg : wineGrapes) {
            double ratio = wg.getPercentage() / 100.0;

            //exact kg of curr grape used in the batch
            double kgUsedOfThisGrape = limitingBatchKg * ratio;

            //convert curr grape's mass to liquid based on its specific yield
            double liters = kgUsedOfThisGrape * wg.getGrape().getWineYield();

            totalLiters += liters;
        }
        return totalLiters;
    }

    public void consumeGrapesForProduction(List<WineGrape> wineGrapes) {
        //recalculate the limit to ensure we only subtract what is physically possible
        //could pass this value in from the main method?
        double limitingBatchKg = calculateMaxRawBatchKg(wineGrapes);

        for (WineGrape wg : wineGrapes) {
            Grape g = wg.getGrape();
            double ratio = wg.getPercentage() / 100.0;

            //subtract only the portion that fits in the limiting batch
            double kgUsed = limitingBatchKg * ratio;

            //safety check -> if it goes below 0 due to rounding
            double newQuantity = Math.max(0, g.getQuantity() - kgUsed);

            g.setQuantity(newQuantity);
            grapeDAO.update(g);
        }
    }
}
