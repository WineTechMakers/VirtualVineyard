package bg.tu_varna.sit.virtualvineyard.test;

import bg.tu_varna.sit.virtualvineyard.chain.BottlingFactory;
import bg.tu_varna.sit.virtualvineyard.dao.BottleDAO;
import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.dao.GrapeDAO;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BottlingFactoryTest {

    // The Class Under Test
    private BottlingFactory bottlingFactory;

    // Mocks for dependencies
    @Mock
    private Warehouse mockWineWarehouse;
    @Mock
    private Warehouse mockBottleWarehouse;
    @Mock
    private GrapeDAO mockGrapeDAO;
    @Mock
    private BottleDAO mockBottleDAO;
    @Mock
    private BottledWineDAO mockBottledWineDAO;

    @BeforeEach
    void setUp() {
        // --- THE CLEAN SETUP ---
        // We inject the mocks directly via the constructor.
        // No reflection or "hacking" required!
        bottlingFactory = new BottlingFactory(
                mockWineWarehouse,
                mockBottleWarehouse,
                mockGrapeDAO,
                mockBottleDAO,
                mockBottledWineDAO
        );
    }

    @Test
    @DisplayName("✅ Successful Bottling - Single Grape, Single Bottle Type")
    void bottleWine_shouldProduceBottledWine_whenResourcesAreSufficient() {
        // --- ARRANGE ---

        // 1. Setup Data: 100kg of Grape, Yield 1.0 = 100 Liters of Wine
        Grape mockGrape = mock(Grape.class);
        when(mockGrape.getQuantity()).thenReturn(100.0);
        when(mockGrape.getWineYield()).thenReturn(1.0);

        WineGrape mockWineGrape = mock(WineGrape.class);
        when(mockWineGrape.getGrape()).thenReturn(mockGrape);
        when(mockWineGrape.getPercentage()).thenReturn(100);

        Wine mockWine = mock(Wine.class);
        when(mockWine.getName()).thenReturn("Cabernet Test");

        List<WineGrape> wineGrapes = new ArrayList<>();
        wineGrapes.add(mockWineGrape);
        when(mockWine.getWineGrapes()).thenReturn(wineGrapes);

        // 2. Setup Bottles: 750ml bottles, plenty available
        Bottle mockBottle = mock(Bottle.class);
        BottleType mockType = mock(BottleType.class);

        when(mockType.getVolume()).thenReturn(750); // 750ml
        when(mockBottle.getVolume()).thenReturn(mockType);
        when(mockBottle.getQuantity()).thenReturn(200);

        List<Bottle> availableBottles = new ArrayList<>();
        availableBottles.add(mockBottle);

        when(mockBottleWarehouse.getBottles()).thenReturn(availableBottles);
        when(mockWineWarehouse.getName()).thenReturn("Wine Storage A");

        // --- ACT ---
        bottlingFactory.bottleWine(mockWine, LocalDate.now());

        // --- ASSERT ---

        // Verify BottledWine was saved
        ArgumentCaptor<List<BottledWine>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockBottledWineDAO).saveAll(captor.capture());

        List<BottledWine> savedWines = captor.getValue();
        assertFalse(savedWines.isEmpty(), "Should have produced bottled wine");
        assertEquals(1, savedWines.size());

        // 100L / 0.75L = 133.33 -> 133 bottles
        assertEquals(133, savedWines.getFirst().getQuantity());

        // Verify updates
        verify(mockBottleDAO, times(1)).update(mockBottle);
        verify(mockGrapeDAO, times(1)).update(mockGrape);
    }

    @Test
    @DisplayName("❌ Bottling Fails - Not Enough Grapes")
    void bottleWine_shouldThrowException_whenNotEnoughGrapes() {
        // --- ARRANGE ---
        Grape mockGrape = mock(Grape.class);
        when(mockGrape.getQuantity()).thenReturn(0.0); // 0 grapes

        WineGrape mockWineGrape = mock(WineGrape.class);
        when(mockWineGrape.getGrape()).thenReturn(mockGrape);
        when(mockWineGrape.getPercentage()).thenReturn(100);

        Wine mockWine = mock(Wine.class);
        List<WineGrape> wineGrapes = Collections.singletonList(mockWineGrape);
        when(mockWine.getWineGrapes()).thenReturn(wineGrapes);

        // --- ACT & ASSERT ---
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bottlingFactory.bottleWine(mockWine, LocalDate.now());
        });

        assertEquals("Not enough grapes to produce wine.", exception.getMessage());
        verifyNoInteractions(mockBottledWineDAO);
    }

    @Test
    @DisplayName("❌ Bottling Fails - No Bottles in Warehouse")
    void bottleWine_shouldThrowException_whenNoBottlesAvailable() {
        // --- ARRANGE ---
        // Setup Grapes
        Grape mockGrape = mock(Grape.class);
        when(mockGrape.getQuantity()).thenReturn(100.0);
        when(mockGrape.getWineYield()).thenReturn(1.0);

        WineGrape mockWineGrape = mock(WineGrape.class);
        when(mockWineGrape.getGrape()).thenReturn(mockGrape);
        when(mockWineGrape.getPercentage()).thenReturn(100);

        Wine mockWine = mock(Wine.class);
        when(mockWine.getWineGrapes()).thenReturn(Collections.singletonList(mockWineGrape));

        // Setup Empty Warehouse
        when(mockBottleWarehouse.getBottles()).thenReturn(new ArrayList<>());
        when(mockBottleWarehouse.getName()).thenReturn("Empty Warehouse");

        // --- ACT & ASSERT ---
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            bottlingFactory.bottleWine(mockWine, LocalDate.now());
        });

        assertEquals("No bottles available.", exception.getMessage());
        verifyNoInteractions(mockBottledWineDAO);
    }

    @Test
    @DisplayName("✅ Complex Bottling - Mixed Grapes & Multiple Bottle Types")
    void bottleWine_shouldHandleMultipleResources_whenProcessingComplexBatch() {
        // --- ARRANGE ---

        // 1. Setup Grapes (The "Limiting Reagent" Scenario)
        // We want a 50/50 mix.
        // Grape A: Plenty (200kg)
        Grape grapeA = mock(Grape.class);
        when(grapeA.getQuantity()).thenReturn(200.0);
        when(grapeA.getWineYield()).thenReturn(1.0); // 1kg = 1L

        // Grape B: Scarce (50kg) -> This will limit the total batch!
        Grape grapeB = mock(Grape.class);
        when(grapeB.getQuantity()).thenReturn(50.0);
        when(grapeB.getWineYield()).thenReturn(1.0); // 1kg = 1L

        // 2. Setup Recipe (50% A, 50% B)
        WineGrape wgA = mock(WineGrape.class);
        when(wgA.getGrape()).thenReturn(grapeA);
        when(wgA.getPercentage()).thenReturn(50);

        WineGrape wgB = mock(WineGrape.class);
        when(wgB.getGrape()).thenReturn(grapeB);
        when(wgB.getPercentage()).thenReturn(50);

        Wine mockWine = mock(Wine.class);
        when(mockWine.getName()).thenReturn("Special Blend");
        List<WineGrape> wineGrapes = new ArrayList<>();
        wineGrapes.add(wgA);
        wineGrapes.add(wgB);
        when(mockWine.getWineGrapes()).thenReturn(wineGrapes);

        /* CALCULATION CHECK:
           - Recipe needs 50% A, 50% B.
           - Grape B has 50kg. It requires 0.5 ratio. Max Batch = 50 / 0.5 = 100kg Total Mix.
           - Grape A has 200kg. Max Batch = 200 / 0.5 = 400kg.
           - LIMIT is 100kg Total Mix.

           - Used A: 100kg * 0.5 = 50kg -> 50 Liters
           - Used B: 100kg * 0.5 = 50kg -> 50 Liters
           - TOTAL WINE = 100 Liters (100,000 ml)
        */

        // 3. Setup Bottles (Chain of Responsibility)
        // Big Bottle (750ml) - Limited stock (only 100 bottles)
        Bottle bigBottle = mock(Bottle.class);
        BottleType bigType = mock(BottleType.class);
        when(bigType.getVolume()).thenReturn(750);
        when(bigBottle.getVolume()).thenReturn(bigType);
        when(bigBottle.getQuantity()).thenReturn(100); // Can hold 75,000ml max

        // Small Bottle (187ml) - Plenty stock (1000 bottles)
        Bottle smallBottle = mock(Bottle.class);
        BottleType smallType = mock(BottleType.class);
        when(smallType.getVolume()).thenReturn(187);
        when(smallBottle.getVolume()).thenReturn(smallType);
        when(smallBottle.getQuantity()).thenReturn(1000);

        // Add to warehouse (Order shouldn't matter as factory sorts them)
        List<Bottle> warehouseBottles = new ArrayList<>();
        warehouseBottles.add(smallBottle);
        warehouseBottles.add(bigBottle);

        when(mockBottleWarehouse.getBottles()).thenReturn(warehouseBottles);
        when(mockWineWarehouse.getName()).thenReturn("Main Cellar");

        // --- ACT ---
        bottlingFactory.bottleWine(mockWine, LocalDate.now());

        // --- ASSERT ---

        // 1. Capture the list of saved bottled wines
        ArgumentCaptor<List<BottledWine>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockBottledWineDAO).saveAll(captor.capture());
        List<BottledWine> results = captor.getValue();

        // We expect 2 items: one batch of 750s, one batch of 187s
        assertEquals(2, results.size(), "Should produce two types of bottled wine");

        /*
           VOLUME CHECK:
           - Total Wine: 100,000 ml
           - Step 1: Fill Big Bottles (750ml)
             - Available: 100 bottles
             - Used: 100 * 750 = 75,000 ml
             - Remaining Wine: 25,000 ml

           - Step 2: Fill Small Bottles (187ml) with remaining 25,000 ml
             - 25,000 / 187 = 133.68 -> 133 Bottles
        */

        // Verify quantities (find specific bottles in list since order varies)
        BottledWine bigBatch = results.stream()
                .filter(bw -> bw.getBottle().equals(bigBottle))
                .findFirst().orElseThrow();

        BottledWine smallBatch = results.stream()
                .filter(bw -> bw.getBottle().equals(smallBottle))
                .findFirst().orElseThrow();

        assertEquals(100, bigBatch.getQuantity(), "Should use all 100 big bottles");
        assertEquals(133, smallBatch.getQuantity(), "Should fill 133 small bottles with remainder");

        // 2. Verify Updates
        // Both bottle types should be updated in DB
        verify(mockBottleDAO, times(1)).update(bigBottle);
        verify(mockBottleDAO, times(1)).update(smallBottle);

        // Both grape types should be updated in DB
        verify(mockGrapeDAO, times(1)).update(grapeA);
        verify(mockGrapeDAO, times(1)).update(grapeB);
    }
}