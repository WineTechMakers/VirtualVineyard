package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import jakarta.persistence.NoResultException;

public class BottleDAO extends AbstractDAO<Bottle> {
    public BottleDAO() {
        setEntityClass(Bottle.class);
    }

    public Bottle findByWarehouseAndType(Warehouse warehouse, BottleType type) {
        try {
            return entityManager.createQuery(
                            "SELECT b FROM Bottle b WHERE b.warehouse = :wh AND b.volume = :vol",
                            Bottle.class)
                    .setParameter("wh", warehouse)
                    .setParameter("vol", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
