import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.entities.WarehouseType;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BottleTest {

    @Test
    void testBottleVolumeCanBeSet() {
        Bottle bottle = new Bottle();
        bottle.setVolume(BottleType.ML750);
        assertEquals(BottleType.ML750, bottle.getVolume());
    }

    @Test
    void testToStringContainsVolume() {
        Bottle bottle = new Bottle();
        bottle.setWarehouse(new Warehouse("Elka's warehouse", "Varna", new WarehouseType(WarehouseContentType.BOTTLE_ONLY.toString())));
        bottle.setVolume(BottleType.ML187);
        String s = bottle.toString();
        assertTrue(s.contains("187"));
    }
}