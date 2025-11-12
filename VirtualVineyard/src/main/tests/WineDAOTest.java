import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WineDAOTest extends JpaTestBase{
    @Test
    void testPersistWine() {
        Wine wine = new Wine();
        wine.setName("Pinot Noir");

        em.persist(wine);
        em.flush();

        assertNotNull(wine.getWine_id());
    }

    @Test
    void testFindWineById() {
        Wine wine = new Wine();
        wine.setName("Chardonnay");
        em.persist(wine);
        em.flush();

        Wine found = em.find(Wine.class, wine.getWine_id());
        assertEquals("Chardonnay", found.getName());
    }

    @Test
    void testUpdateWineName() {
        Wine wine = new Wine();
        wine.setName("Old Name");
        em.persist(wine);
        em.flush();

        wine.setName("New Name");
        em.merge(wine);
        em.flush();

        Wine updated = em.find(Wine.class, wine.getWine_id());
        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDeleteWine() {
        Wine wine = new Wine();
        wine.setName("ToDelete");
        em.persist(wine);
        em.flush();

        em.remove(wine);
        em.flush();

        Wine deleted = em.find(Wine.class, wine.getWine_id());
        assertNull(deleted);
    }
}
