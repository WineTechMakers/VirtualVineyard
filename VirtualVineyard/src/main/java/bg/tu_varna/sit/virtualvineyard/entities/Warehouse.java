package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.models.Bottling;
import bg.tu_varna.sit.virtualvineyard.models.BottlingInterface;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "warehouse")
    private List<BottledWine> bottledWines;

    public Warehouse() {

    }

    public Warehouse(String name, String address) {
        this.name = name;
        this.address = address;
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

    public void bottleWine(Wine wine)
    {
        int wineInML = calcProduct(wine.getWineGrapes());
        BottlingInterface chain = initChain();
        if(chain == null)
            throw new IllegalStateException("No bottles available in warehouse");
        List<BottledWine> bottled = chain.handle(wineInML, wine);
        bottledWines.addAll(bottled);
    }

    private BottlingInterface initChain()
    {
        if(bottles == null || bottles.isEmpty())
            return null;
        //needs sorting -_-
        List<Bottle> sortedBottles = new ArrayList<>(bottles);
        sortedBottles.sort((b1, b2) ->
                Integer.compare(b2.getVolume().getVolume(), b1.getVolume().getVolume())
        );

        BottlingInterface first = new Bottling(sortedBottles.getFirst());
        BottlingInterface current = first;

        for(int i = 1; i < sortedBottles.size(); i++) {
            BottlingInterface next = new Bottling(sortedBottles.get(i));
            current.setNext(next);
            current = next;
        }
        return first;
    }

    private int calcProduct(List<WineGrape> input)
    {
        int res=0;
            for(WineGrape i: input)
            {
                res += (int) (i.getPercentage() * i.getGrape().getWineYield() * 10);//*10 is result of *1000/100. *1000 is 'cause wineYield is in liters. /100 is because of the percentage
            }
        return res;
    }

    @Override
    public String toString() {
        return name;
    }
}
