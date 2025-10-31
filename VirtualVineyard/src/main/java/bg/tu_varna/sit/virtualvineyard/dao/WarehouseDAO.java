package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;

public class WarehouseDAO extends AbstractDAO<Warehouse> {
    public WarehouseDAO() {
        setEntityClass(Warehouse.class);
    }
}
