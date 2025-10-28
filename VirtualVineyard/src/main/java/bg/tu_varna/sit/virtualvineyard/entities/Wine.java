package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "Wine")
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wine_id;

    @Column (nullable = false)
    private String name;

    @OneToMany(mappedBy = "wine")
    private List<WineGrape> wineGrapes;

    public Wine() {}

    public Wine(String name) {
        this.name = name;
    }

    public List<WineGrape> getWineGrapes() {
        return wineGrapes;
    }
    public void setWineGrapes(List<WineGrape> wineGrapes) {
        this.wineGrapes = wineGrapes;
    }
}