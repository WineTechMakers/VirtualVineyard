package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Grape;

public class GrapeDAO extends AbstractDAO<Grape> {
    public GrapeDAO() {
        setEntityClass(Grape.class);
    }
}
