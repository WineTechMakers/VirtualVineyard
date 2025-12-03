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
    private final Warehouse grapeWarehouse;

    private final GrapeDAO grapeDAO = new GrapeDAO();
    private final BottleDAO bottleDAO = new BottleDAO();
    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();

    public BottlingFactory(Warehouse wineWarehouse, Warehouse bottleWarehouse, Warehouse grapeWarehouse) {
        this.wineWarehouse = wineWarehouse;
        this.bottleWarehouse = bottleWarehouse;
        this.grapeWarehouse = grapeWarehouse;
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

    //compute liters of wine that can be produced
    public double computeTotalLitersAvailable(List<WineGrape> grapes) {
        double totalLiters = 0;

        for (WineGrape wg : grapes) {
            Grape g = wg.getGrape();

            double percentage = wg.getPercentage() / 100.0;
            double kgUsed = g.getQuantity() * percentage;

            double liters = kgUsed * g.getWineYield(); // L = kg * yield

            totalLiters += liters;
        }
        return totalLiters;
    }

    //subtract used grape quantity
    public void consumeGrapesForProduction(List<WineGrape> grapes) {
        for (WineGrape wg : grapes) {
            Grape g = wg.getGrape();

            double percentage = wg.getPercentage() / 100.0;
            double kgUsed = g.getQuantity() * percentage;

            g.setQuantity(g.getQuantity() - kgUsed);

            grapeDAO.update(g);
        }
    }

//    public void bottleWine(Wine wine)
//    {
//        for(WineGrape i: wine.getWineGrapes())
//        {
//            if(i.getGrape().getQuantity()<1)
//                throw new IllegalArgumentException("Not enough grape");
//        }
//
//        int wineInML = calcProduct(wine.getWineGrapes());
//        BottlingInterface chain = initChain();
//        if(chain == null)
//            throw new IllegalStateException("No bottles available in warehouse");
//
//        List<BottledWine> bottled = chain.handle(wineInML, wine);
//
//        for (BottledWine bw : bottled) {
//            bw.setWarehouse(wineWarehouse);
//        }
//
//        BottledWineDAO bottledWineDAO = new BottledWineDAO();
//        bottledWineDAO.saveAll(bottled);
//    }
//
//    private BottlingInterface initChain()
//    {
//        List<Bottle> bottles = bottleWarehouse.getBottles();
//        if(bottles == null || bottles.isEmpty())
//            return null;
//        //needs sorting -_-
//        List<Bottle> sortedBottles = new ArrayList<>(bottles);
//        sortedBottles.sort((b1, b2) ->
//                Integer.compare(b2.getVolume().getVolume(), b1.getVolume().getVolume())
//        );
//
//        BottlingInterface first = new Bottling(sortedBottles.getFirst());
//        BottlingInterface current = first;
//
//        for(int i = 1; i < sortedBottles.size(); i++) {
//            BottlingInterface next = new Bottling(sortedBottles.get(i));
//            current.setNext(next);
//            current = next;
//        }
//        return first;
//    }
//
//    private int calcProduct(List<WineGrape> input)
//    {
//        int res=0;
//        for(WineGrape i: input)
//        {
//            res += (int) (i.getPercentage() * i.getGrape().getWineYield() * 10);//*10 is result of *1000/100. *1000 is 'cause wineYield is in liters. /100 is because of the percentage
//            i.getGrape().setQuantity(0);//delete quantity
////            GrapeDAO grapeDAO = new GrapeDAO(); ??
////            grapeDAO.update(i.getGrape());
//        }
//        return res;
//    }
}
