package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.models.BottledWineID;
import bg.tu_varna.sit.virtualvineyard.models.WineGrapeID;
import jakarta.persistence.*;

@Entity
@Table(name = "BottledWine")
@IdClass(BottledWineID.class)
public class BottledWine {
    @Id
    @ManyToOne
    @JoinColumn(name = "wine_id", nullable = false)
    private Wine wine;

    @Id
    @ManyToOne
    @JoinColumn(name = "bottle_id", nullable = false)
    private Bottle bottle;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    public BottledWine() {
    }

    public BottledWine(Wine wine, Bottle bottle, int quantity) {
        this.wine = wine;
        this.bottle = bottle;
        this.quantity = quantity;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
