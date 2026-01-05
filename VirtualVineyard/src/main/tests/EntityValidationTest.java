import bg.tu_varna.sit.virtualvineyard.entities.Grape;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse; // Assuming this exists
import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityValidationTest {

    @Test
    void grape_shouldNormalizeAndValidateNames() {
        // 1. Test Normalization (Mixed Case -> Title Case)
        Grape g1 = new Grape("Mavrud", 0, true, 0, null);
        Grape g2 = new Grape("MaVrUd", 0, true, 0, null);
        Grape g3 = new Grape("mAVRUD", 0, true, 0, null);
        Grape g4 = new Grape("MAVRUD", 0, true, 0, null);

        assertEquals("Mavrud", g1.getName(), "Standard name failed");
        assertEquals("Mavrud", g2.getName(), "Mixed case failed");
        assertEquals("Mavrud", g3.getName(), "Lower/Upper mix failed");
        assertEquals("Mavrud", g4.getName(), "All caps failed");

        // 2. Test Numbers are Allowed (Alphanumeric)
        Grape gNumbers = new Grape("Mavrud2024", 0, true, 0, null);
        assertEquals("Mavrud2024", gNumbers.getName(), "Should accept numbers in name");

        // 3. Test Invalid Input (Cyrillic / Symbols) - Expect Exception
        assertThrows(IllegalArgumentException.class, () -> {
            new Grape("мавруд", 0, true, 0, null);
        }, "Should reject Cyrillic 'мавруд'");

        assertThrows(IllegalArgumentException.class, () -> {
            new Grape("Mavrud!", 0, true, 0, null);
        }, "Should reject special characters like '!'");
    }

    @Test
    void wine_shouldNormalizeAndValidateNames() {
        // 1. Test Normalization
        Wine w1 = new Wine("Cabernet");
        Wine w2 = new Wine("cABERNET");
        Wine w3 = new Wine("CABERNET");

        assertEquals("Cabernet", w1.getName());
        // NOTE: These will fail until you fix the bug in Wine.java (this.name = normalize(name))
        assertEquals("Cabernet", w2.getName(), "Wine name should be normalized");
        assertEquals("Cabernet", w3.getName(), "Wine name should be normalized");

        // 2. Test Numbers
        Wine wNumbers = new Wine("Cabernet5");
        assertEquals("Cabernet5", wNumbers.getName(), "Should accept numbers");

        // 3. Test Invalid Input
        assertThrows(IllegalArgumentException.class, () -> {
            new Wine("Каберне");
        }, "Should reject Cyrillic");
    }

    @Test
    void warehouse_shouldNormalizeAndValidateNames() {
        // Assuming Warehouse has a constructor similar to: new Warehouse(name, location, capacity)
        // Or just a setter if you use the default constructor.

        // 1. Test Normalization
        Warehouse wh1 = new Warehouse("MainStorage", "", null);
        Warehouse wh2 = new Warehouse("mainSTORAGE", "", null);

        assertEquals("Mainstorage", wh1.getName());
        assertEquals("Mainstorage", wh2.getName(), "Warehouse name should be normalized");

        // 2. Test Invalid Input
        assertThrows(IllegalArgumentException.class, () -> {
            new Warehouse("Склад #1", "", null);
        }, "Should reject Cyrillic/Symbols");
    }
}