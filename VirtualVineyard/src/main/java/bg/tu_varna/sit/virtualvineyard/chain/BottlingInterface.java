package bg.tu_varna.sit.virtualvineyard.chain;

import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;

import java.util.List;

public interface BottlingInterface
{
    List<BottledWine> handle(int wineInML, Wine wine);
    void setNext(BottlingInterface next);
}
