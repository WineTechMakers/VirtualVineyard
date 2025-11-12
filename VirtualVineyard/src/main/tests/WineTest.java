import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WineTest {
    @Test
    void testWineNameCanBeSetAndRetrieved() {
        Wine wine = new Wine();
        wine.setName("Merlot");
        assertEquals("Merlot", wine.getName());
    }

    @Test
    void testToStringReturnsReadableFormat() {
        Wine wine = new Wine();
        wine.setName("Cabernet");
        String result = wine.toString();
        assertTrue(result.contains("Cabernet"));
    }

    @Test
    void testDefaultWineHasNoGrapes() {
        Wine wine = new Wine();
        assertNull(wine.getWineGrapes(), "New wine should start with no grapes");
    }
}
