package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Bottle;

public class BottleDAO extends AbstractDAO<Bottle> {
    public BottleDAO() {
        setEntityClass(Bottle.class);
    }
}
