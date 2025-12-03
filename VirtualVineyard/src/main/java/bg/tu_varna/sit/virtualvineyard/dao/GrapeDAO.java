package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Grape;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import jakarta.persistence.NoResultException;

public class GrapeDAO extends AbstractDAO<Grape> {
    public GrapeDAO() {
        setEntityClass(Grape.class);
    }

    public Grape findByWarehouseAndName(Warehouse w, String name) {
        try {
            return entityManager.createQuery(
                            "SELECT g FROM Grape g WHERE g.warehouse = :w AND g.name = :n", Grape.class)
                    .setParameter("w", w)
                    .setParameter("n", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
