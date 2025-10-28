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
    protected BottleType quantity;

    @Column(nullable = false)
    protected int count;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    public Bottle() {

    }

    public Bottle(BottleType quantity, int count) {
        this.quantity = quantity;
        this.count = count;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
