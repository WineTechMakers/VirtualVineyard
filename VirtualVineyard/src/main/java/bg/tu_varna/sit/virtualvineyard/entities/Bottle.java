package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import jakarta.persistence.*;

@Entity
@Table (name = "Bottle")
public class Bottle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bottle_id;

    @Column(nullable = false)
    protected BottleType volume;

    @Column(nullable = false)
    protected int quantity;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    public Bottle() {

    }

    public Bottle(BottleType volume, int quantity) {
        this.volume = volume;
        this.quantity = quantity;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
