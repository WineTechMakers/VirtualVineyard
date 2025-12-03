package bg.tu_varna.sit.virtualvineyard.chain;

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
    public List<BottledWine> handle(int wineInML, Wine wine)
    {
        List<BottledWine> res = new ArrayList<>();

        int volume = bottle.getVolume().getVolume(); //ml
        int availableBottles = bottle.getQuantity();

        if(volume <= wineInML && availableBottles > 0)
        {
            int possibleBottles = wineInML / volume;
            int usedBottles = Math.min(possibleBottles, availableBottles);

            int remainingWine = wineInML - usedBottles * volume;

            if (usedBottles > 0) {
                res.add(new BottledWine(wine, bottle, usedBottles));
                bottle.setQuantity(availableBottles - usedBottles);
            }

            if (next != null)
                res.addAll(next.handle(remainingWine, wine));
            return res;
        }

        if (next != null)
            return next.handle(wineInML, wine);

        return res;
    }
    @Override
    public void setNext(BottlingInterface next)
    {
        this.next = next;
    }
}
