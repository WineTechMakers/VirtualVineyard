package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;

import java.util.ArrayList;
import java.util.List;

public class Bottling implements BottlingInterface
{
    private BottlingInterface next;
    private final Bottle bottle;

    public Bottling(Bottle bottle)
    {
        this.bottle = bottle;
    }

    @Override
    public List<BottledWine> handle(int wineinML, Wine wine)
    {
        List<BottledWine> res = new ArrayList<>();
        if(bottle.getVolume().getVolume() <= wineinML)
        {
            int resultingBottles = wineinML / bottle.getVolume().getVolume();
            wineinML = wineinML % bottle.getVolume().getVolume();
            res.add( new BottledWine(wine, bottle, resultingBottles) );
            if(next!=null && wineinML > 0)
                res.addAll(next.handle(wineinML, wine));
        }
        return res;
    }
    @Override
    public void setNext(BottlingInterface next)
    {
        this.next = next;
    }
}
