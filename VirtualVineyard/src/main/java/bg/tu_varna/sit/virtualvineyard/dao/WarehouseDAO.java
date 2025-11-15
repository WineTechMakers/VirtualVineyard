package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import java.util.List;

public class WarehouseDAO extends AbstractDAO<Warehouse> {
    public WarehouseDAO() {
        setEntityClass(Warehouse.class);
    }

    public List<Warehouse> findByContentType(WarehouseContentType type) {
        return entityManager.createQuery(
                        "SELECT w FROM Warehouse w WHERE w.warehouseType.type = :type",
                        Warehouse.class
                )
                .setParameter("type", type.toString())
                .getResultList();
    }
}
