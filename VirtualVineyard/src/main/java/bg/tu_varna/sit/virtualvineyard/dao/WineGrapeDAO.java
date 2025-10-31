package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.WineGrape;

public class WineGrapeDAO extends AbstractDAO<WineGrape> {
    public WineGrapeDAO() {
        setEntityClass(WineGrape.class);
    }
}
