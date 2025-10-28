package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Grape")
public class Grape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grape_id;

    @Column (nullable = false)
    private String name;//Name of grape

    @Column (nullable = false)
    private int quantity;//quantity in warehouse

    @Column (nullable = false)
    private boolean isBlack;//either black-0 or white-1

    @Column (nullable = false)
    private int product;//how much wine can be made per 1kg of grape

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "grape")
    private List<WineGrape> wineGrapes;

    public Grape() {}

    public Grape(String name, int quantity, boolean isBlack, int product) {
        this.name = name;
        this.quantity = quantity;
        this.isBlack = isBlack;
        this.product = product;
    }

    public List<WineGrape> getWineGrapes() {
        return wineGrapes;
    }
    public void setWineGrapes(List<WineGrape> wineGrapes) {
        this.wineGrapes = wineGrapes;
    }
}