package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;

import java.util.List;

public interface BottlingInterface
{
    List<BottledWine> handle(int WineinML, Wine wine);
    void setNext(BottlingInterface next);
}
