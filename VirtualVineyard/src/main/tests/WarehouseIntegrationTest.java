import bg.tu_varna.sit.virtualvineyard.entities.Grape;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.entities.WarehouseType;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseIntegrationTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Създаваме реален тип склад (без Mocks)
        WarehouseType type = new WarehouseType();
        type.setType(WarehouseContentType.GRAPE_ONLY.toString());

        // 2. Създаваме реален склад
        warehouse = new Warehouse("TestStorage", "Varna", type);

        // ВАЖНО: Тъй като не ползваме Hibernate тук, списъците може да са null.
        // Инициализираме ги ръчно (както би направил Hibernate или конструктора).
        initializeList(warehouse, "grapes");
    }

    @Test
    @DisplayName("Integration: Warehouse correctly tracks Grape quantities and limits")
    void testWarehouseGrapeIntegration() {
        // --- ARRANGE ---
        // Създаваме истински обекти Грозде
        Grape g1 = new Grape("Mavrud", 0, true, 0, null);
        g1.setQuantity(100.0); // Достатъчно количество

        Grape g2 = new Grape("Merlot", 0, true, 0, null);
        g2.setQuantity(10.0);  // Критично малко количество (под 50)

        // --- ACT ---
        // Добавяме ги в склада (използвайки реалния метод addGrape)
        warehouse.addGrape(g1);
        warehouse.addGrape(g2);

        // --- ASSERT ---

        // 1. Проверяваме дали складът "вижда" гроздето
        assertEquals(2, warehouse.getGrapes().size(), "Warehouse should contain 2 grape batches");

        // 2. Проверяваме логиката за критичен лимит
        // g2 е с 10кг, а лимитът по подразбиране е 50. Трябва да върне TRUE.
        boolean isCritical = warehouse.isCriticalLimit();

        assertTrue(isCritical, "System should detect critical limit breach because Merlot is only 10kg");
    }

    @Test
    @DisplayName("Integration: Warehouse stays safe when quantities are high")
    void testWarehouseSafetyCheck() {
        // --- ARRANGE ---
        Grape g1 = new Grape("Chardonnay", 0, false, 0, null);
        g1.setQuantity(60.0); // Над лимита от 50

        // --- ACT ---
        warehouse.addGrape(g1);

        // --- ASSERT ---
        assertFalse(warehouse.isCriticalLimit(), "Warehouse should be SAFE (not critical) when quantity is > 50");
    }

    // --- Helper Method за инициализация на private списъци ---
    // Това е нужно само ако в конструктора на Warehouse.java липсва "this.grapes = new ArrayList<>();"
    private void initializeList(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (field.get(target) == null) {
            field.set(target, new ArrayList<>());
        }
    }
}