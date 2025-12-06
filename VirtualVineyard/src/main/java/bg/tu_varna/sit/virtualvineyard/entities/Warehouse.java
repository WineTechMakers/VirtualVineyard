package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Warehouse")
public class Warehouse { //<T>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouse_id;

    @Column (length = 60, nullable = false)
    private String name;

    @Column (length = 140)
    private String address;

    @Column (name = "critical_limit")
    private int criticalLimit = 50;

    @ManyToOne
    @JoinColumn(name = "warehouse_type_id", nullable = false)
    private WarehouseType warehouseType;

    @OneToMany(mappedBy = "warehouse")
    private List<Grape> grapes;

    @OneToMany(mappedBy = "warehouse")
    private List<Bottle> bottles;

    @OneToMany(mappedBy = "warehouse")
    private List<BottledWine> bottledWines;

    public Warehouse() {

    }

    public Warehouse(String name, String address, WarehouseType warehouseType) {
        this.name = name;
        this.address = address;
        this.warehouseType = warehouseType;
    }

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

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public int getCriticalLimit() {
        return criticalLimit;
    }

    public void setCriticalLimit(int criticalLimit) {
        this.criticalLimit = criticalLimit;
    }

    public void addGrape(Grape grape)
    {
        grapes.add(grape);
    }

    public void addBottle(Bottle bottle)
    {
        bottles.add(bottle);
    }

    public void addBottledWine(BottledWine bottledWine){
        bottledWines.add(bottledWine);
    }

    public void addBottledWines(List<BottledWine> bottledWines){
        this.bottledWines.addAll(bottledWines);
    }

    public List<BottledWine> getBottledWines(){
        return bottledWines;
    }

    public List<Grape> getGrapes(){
        return grapes;
    }

    public List<Bottle> getBottles(){
        return bottles;
    }

    public boolean isCriticalLimit(){
        if(warehouseType.getType().equals(WarehouseContentType.GRAPE_ONLY.toString())) {
            for (Grape grape : grapes) {
                if(grape.getQuantity() < criticalLimit) {
                    return true;
                }
            }
        }
        else if(warehouseType.getType().equals(WarehouseContentType.BOTTLE_ONLY.toString())) {
            for (Bottle bottle : bottles) {
                if(bottle.getQuantity() < criticalLimit) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
