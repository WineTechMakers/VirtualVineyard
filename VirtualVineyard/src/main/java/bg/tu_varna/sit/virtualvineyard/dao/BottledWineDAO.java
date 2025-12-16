package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDate;
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

    public void saveAll(List<BottledWine> list)
    {
        try {
            completeAction(() -> {
                for (BottledWine bw : list) {
                    entityManager.persist(bw);
                }
            });
        }
        catch (Exception e) {
            completeAction(() -> {
                for (BottledWine bw : list) {
                    entityManager.merge(bw);
                }
            });
        }
    }

    public List<BottledWine> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
                        "SELECT bw FROM BottledWine bw WHERE bw.productionDate BETWEEN :start AND :end",
                        BottledWine.class)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
    }
}
