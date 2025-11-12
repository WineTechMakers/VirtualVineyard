package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;

import java.util.List;

public class BottledWineDAO extends AbstractDAO<BottledWine> {
    public BottledWineDAO() {
        setEntityClass(BottledWine.class);
    }

    public List<BottledWine> findByWine(Wine wine) {
        return entityManager
                .createQuery("SELECT bw FROM BottledWine bw WHERE bw.wine = :wine", BottledWine.class)
                .setParameter("wine", wine)
                .getResultList();
    }
}
