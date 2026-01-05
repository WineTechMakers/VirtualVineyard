package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.Normalizer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "Wine")
public class Wine implements Normalizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wine_id;

    @Column (length = 60, nullable = false)
    private String name;

    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WineGrape> wineGrapes;

    @OneToMany(mappedBy = "wine") //CascadeType.PERSIST?
    private List<BottledWine> bottledWines;

    public Wine() {}

    public Wine(String name) {
        setName(name);
    }

    public Long getWine_id() {
        return wine_id;
    }

    public void setWine_id(Long wine_id) {
        this.wine_id = wine_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name=normalize(name);
        if(hasInvalidCharacters(name)){
            throw new IllegalArgumentException("Allowed latin characters and numbers only!");
        }
        this.name = name;
    }

    public List<WineGrape> getWineGrapes() {
        return wineGrapes;
    }

    public void setWineGrapes(List<WineGrape> wineGrapes) {
        this.wineGrapes = wineGrapes;
    }

    public List<BottledWine> getBottledWines() {
        return bottledWines;
    }

    public void setBottledWines(List<BottledWine> bottledWines) {
        this.bottledWines = bottledWines;
    }

    @Override
    public String toString() {
        return this.name;
    }
}