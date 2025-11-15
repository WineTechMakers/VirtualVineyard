package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "WarehouseType")
public class WarehouseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouse_type_id;

    @Column
    private String type;

    @OneToMany(mappedBy = "warehouseType")
    private List<Warehouse> warehouses;

    public WarehouseType() {
    }

    public WarehouseType(String type) {
        this.type = type;
    }

    public Long getWarehouse_type_id() {
        return warehouse_type_id;
    }

    public void setWarehouse_type_id(Long warehouse_type_id) {
        this.warehouse_type_id = warehouse_type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void addWarehouse(Warehouse warehouse){
        warehouses.add(warehouse);
    }
}
