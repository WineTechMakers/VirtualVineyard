package bg.tu_varna.sit.virtualvineyard.chain;

import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.dao.GrapeDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.*;

import java.util.ArrayList;
import java.util.List;

public class BottlingFactory {
    private final Warehouse wineWarehouse;
    private final Warehouse bottleWarehouse;
    private final Warehouse grapeWarehouse;


    public BottlingFactory(Warehouse wineWarehouse, Warehouse bottleWarehouse, Warehouse grapeWarehouse) {
        this.wineWarehouse = wineWarehouse;
        this.bottleWarehouse = bottleWarehouse;
        this.grapeWarehouse = grapeWarehouse;
    }

    public void bottleWine(Wine wine)
    {
        for(WineGrape i: wine.getWineGrapes())
        {
            if(i.getGrape().getQuantity()<1)
                throw new IllegalArgumentException("Not enough grape");
        }

        int wineInML = calcProduct(wine.getWineGrapes());
        BottlingInterface chain = initChain();
        if(chain == null)
            throw new IllegalStateException("No bottles available in warehouse");

        List<BottledWine> bottled = chain.handle(wineInML, wine);

        for (BottledWine bw : bottled) {
            bw.setWarehouse(wineWarehouse);
        }

        BottledWineDAO bottledWineDAO = new BottledWineDAO();
        bottledWineDAO.saveAll(bottled);
    }

    private BottlingInterface initChain()
    {
        List<Bottle> bottles = bottleWarehouse.getBottles();
        if(bottles == null || bottles.isEmpty())
            return null;
        //needs sorting -_-
        List<Bottle> sortedBottles = new ArrayList<>(bottles);
        sortedBottles.sort((b1, b2) ->
                Integer.compare(b2.getVolume().getVolume(), b1.getVolume().getVolume())
        );

        BottlingInterface first = new Bottling(sortedBottles.getFirst());
        BottlingInterface current = first;

        for(int i = 1; i < sortedBottles.size(); i++) {
            BottlingInterface next = new Bottling(sortedBottles.get(i));
            current.setNext(next);
            current = next;
        }
        return first;
    }

    private int calcProduct(List<WineGrape> input)
    {
        int res=0;
        for(WineGrape i: input)
        {
            res += (int) (i.getPercentage() * i.getGrape().getWineYield() * 10);//*10 is result of *1000/100. *1000 is 'cause wineYield is in liters. /100 is because of the percentage
            i.getGrape().setQuantity(0);//delete quantity
//            GrapeDAO grapeDAO = new GrapeDAO(); ??
//            grapeDAO.update(i.getGrape());
        }
        return res;
    }
}
