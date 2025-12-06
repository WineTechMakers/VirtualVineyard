package bg.tu_varna.sit.virtualvineyard.chain;

import bg.tu_varna.sit.virtualvineyard.dao.BottleDAO;
import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.dao.GrapeDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.*;

import java.util.*;

public class BottlingFactory {
    private final Warehouse wineWarehouse;
    private final Warehouse bottleWarehouse;

    private final GrapeDAO grapeDAO = new GrapeDAO();
    private final BottleDAO bottleDAO = new BottleDAO();
    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();

    public BottlingFactory(Warehouse wineWarehouse, Warehouse bottleWarehouse) {
        this.wineWarehouse = wineWarehouse;
        this.bottleWarehouse = bottleWarehouse;
    }

    public void bottleWine(Wine wine) {
        double liters = computeTotalLitersAvailable(wine.getWineGrapes());

        if (liters <= 0)
            throw new IllegalArgumentException("Not enough grapes to produce wine.");

        int totalML = (int) (liters * 1000);

        BottlingInterface chain = initChain();
        if (chain == null)
            throw new IllegalStateException("No bottles available.");

        List<BottledWine> bottled = chain.handle(totalML, wine);

        if (bottled.isEmpty()) {
            throw new IllegalStateException("Not enough bottle capacity for the produced wine.");
        }

        for (BottledWine bw : bottled)
            bw.setWarehouse(wineWarehouse);

        bottledWineDAO.saveAll(bottled);

        Set<Bottle> usedBottles = new HashSet<>();
        for (BottledWine bw : bottled) {
            usedBottles.add(bw.getBottle());
        }

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
                // How much TOTAL mix could we make if this grape was the only limit?
                // Example: Have 10kg, need 50% (0.5). Max mix = 10 / 0.5 = 20kg.
                double maxPossibleWithThisGrape = availableStock / requiredRatio;

                if (maxPossibleWithThisGrape < maxTotalMixKg) {
                    maxTotalMixKg = maxPossibleWithThisGrape;
                }
            }
        }

        // If no grapes or logic failed, return 0
        if (maxTotalMixKg == Double.MAX_VALUE) return 0;

        return maxTotalMixKg;
    }

    public double computeTotalLitersAvailable(List<WineGrape> wineGrapes) {
        // First, find out the maximum weight of the grape mixture we can form
        double limitingBatchKg = calculateMaxRawBatchKg(wineGrapes);
        double totalLiters = 0;

        for (WineGrape wg : wineGrapes) {
            double ratio = wg.getPercentage() / 100.0;

            // Calculate exact kg of THIS grape used in the batch
            double kgUsedOfThisGrape = limitingBatchKg * ratio;

            // Convert that specific grape's mass to liquid based on ITS specific yield
            double liters = kgUsedOfThisGrape * wg.getGrape().getWineYield();

            totalLiters += liters;
        }
        return totalLiters;
    }

    public void consumeGrapesForProduction(List<WineGrape> wineGrapes) {
        // Recalculate the limit to ensure we only subtract what is physically possible
        // (Or you could pass this value in from the main method to save performance)
        double limitingBatchKg = calculateMaxRawBatchKg(wineGrapes);

        for (WineGrape wg : wineGrapes) {
            Grape g = wg.getGrape();
            double ratio = wg.getPercentage() / 100.0;

            // Only subtract the portion that fits in the limiting batch
            double kgUsed = limitingBatchKg * ratio;

            // Safety check to ensure we don't go below zero due to floating point rounding
            double newQuantity = Math.max(0, g.getQuantity() - kgUsed);

            g.setQuantity(newQuantity);
            grapeDAO.update(g);
        }
    }
}
