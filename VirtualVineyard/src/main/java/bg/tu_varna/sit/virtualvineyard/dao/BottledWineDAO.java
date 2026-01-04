package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public List<BottledWine> findByWineAndProductionDate(Wine wine, LocalDate productionDate) {
        return entityManager.createQuery(
                        "SELECT bw FROM BottledWine bw WHERE bw.wine = :wine AND bw.productionDate = :productionDate",
                        BottledWine.class)
                .setParameter("wine", wine)
                .setParameter("productionDate", productionDate)
                .getResultList();
    }

    public BottledWine findByWineAndBottleAndProductionDate(Wine wine, Bottle bottle, LocalDate productionDate) {
        return entityManager.createQuery(
                        "SELECT bw FROM BottledWine bw WHERE bw.wine = :wine AND bw.bottle = :bottle AND bw.productionDate = :productionDate",
                        BottledWine.class)
                .setParameter("wine", wine)
                .setParameter("bottle", bottle)
                .setParameter("productionDate", productionDate)
                .getResultStream().findFirst().orElse(null);
    }
}