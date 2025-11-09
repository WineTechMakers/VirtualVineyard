package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
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

    @Column (length = 140)
    private String address;

    @OneToMany(mappedBy = "warehouse")
    private List<Grape> grapes;

    @OneToMany(mappedBy = "warehouse")
    private List<Bottle> bottles;

    public Long getWarehouse_id() {
        return warehouse_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setWarehouse_id(Long warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Warehouse(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addGrape(Grape grape)
    {
        grapes.add(grape);
    }

    public void addBottle(Bottle bottle)
    {
        bottles.add(bottle);
    }
}
