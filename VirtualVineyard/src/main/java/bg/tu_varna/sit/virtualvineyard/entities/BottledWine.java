package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "BottledWine")
public class BottledWine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bottled_wine_id")
    private Long bottledWineId;

    @ManyToOne
    @JoinColumn(name = "wine_id", nullable = false)
    private Wine wine;

    @ManyToOne
    @JoinColumn(name = "bottle_id", nullable = false)
    private Bottle bottle;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    public BottledWine() {
    }

    public BottledWine(Wine wine, Bottle bottle, int quantity) {
        this.wine = wine;
        this.bottle = bottle;
        this.quantity = quantity;
        this.productionDate = LocalDate.now();
    }

    public BottledWine(Wine wine, Bottle bottle, int quantity, LocalDate productionDate) {
        this.wine = wine;
        this.bottle = bottle;
        this.quantity = quantity;
        this.productionDate = productionDate;
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

    public Long getId() {
        return bottledWineId;
    }

    public void setId(Long id) {
        this.bottledWineId = id;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }
}
