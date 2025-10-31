package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Wine;

public class WineDAO extends AbstractDAO<Wine> {
    public WineDAO() {
        setEntityClass(Wine.class);
    }
}
