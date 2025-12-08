package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.WarehouseType;
import jakarta.persistence.NoResultException;

public class WarehouseTypeDAO extends AbstractDAO<WarehouseType>{

    public WarehouseTypeDAO() {
        setEntityClass(WarehouseType.class);
    }

    public WarehouseType findByName(String type) {
        try {
            return entityManager.createQuery(
                            "SELECT w FROM WarehouseType w WHERE w.type = :type", WarehouseType.class)
                    .setParameter("type", type)
                    .getSingleResult();
        } catch (NoResultException e) {

            return null;
        }
    }
}
