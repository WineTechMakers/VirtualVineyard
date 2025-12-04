package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.GUI.BottleTypeConverter;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "Bottle",
        uniqueConstraints = @UniqueConstraint(columnNames = {"warehouse_id", "volume"}))
public class Bottle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bottle_id;

    @Column(nullable = false)
    @Convert(converter = BottleTypeConverter.class)
    protected BottleType volume;

    @Column(nullable = false)
    protected int quantity;

    @Column(name = "date_received")
    private LocalDate dateReceived;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "bottle")
    private List<BottledWine> bottledWines;

    public Bottle() {

    }

    public Bottle(BottleType volume, int quantity, Warehouse warehouse) {
        this.volume = volume;
        this.quantity = quantity;
        this.warehouse = warehouse;
        this.dateReceived = LocalDate.now();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Long getBottle_id() {
        return bottle_id;
    }

    public void setBottle_id(Long bottle_id) {
        this.bottle_id = bottle_id;
    }

    public BottleType getVolume() {
        return volume;
    }

    public void setVolume(BottleType volume) {
        this.volume = volume;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public List<BottledWine> getBottledWines() {
        return bottledWines;
    }

    public void setBottledWines(List<BottledWine> bottledWines) {
        this.bottledWines = bottledWines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("volume= ").append(volume.getVolume());
        sb.append(", quantity= ").append(quantity);
        sb.append(", warehouse= ").append(warehouse.getName());
        return sb.toString();
    }
}
