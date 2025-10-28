package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouse_id;

    @Column (length = 60, nullable = false)
    private String name;

    @OneToMany(mappedBy = "warehouse")
    private List<Grape> grapes;

    @OneToMany(mappedBy = "warehouse")
    private List<Bottle> bottles;

    public void setId(Long warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public Long getId() {
        return warehouse_id;
    }
}
