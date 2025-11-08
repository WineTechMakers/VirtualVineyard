package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Grape")
public class Grape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grape_id;

    @Column (length = 50, nullable = false)
    private String name;//Name of grape

    @Column (nullable = false)
    private int quantity;//quantity in warehouse

    @Column (nullable = false)
    private boolean isBlack;//either black-0 or white-1

    @Column (nullable = false)
    private int wineYield;//how much wine can be made per 1kg of grape (in liters)

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "grape")
    private List<WineGrape> wineGrapes;

    public Grape() {}

    public Grape(String name, int quantity, boolean isBlack, int wineYield) {
        this.name = name;
        this.quantity = quantity;
        this.isBlack = isBlack;
        this.wineYield = wineYield;
    }

    public List<WineGrape> getWineGrapes() {
        return wineGrapes;
    }
    public void setWineGrapes(List<WineGrape> wineGrapes) {
        this.wineGrapes = wineGrapes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public int getWineYield() {
        return wineYield;
    }

    public void setWineYield(int wineYield) {
        this.wineYield = wineYield;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}