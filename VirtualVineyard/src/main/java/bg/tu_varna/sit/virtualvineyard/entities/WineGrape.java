package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.models.WineGrapeID;
import jakarta.persistence.*;

@Entity
@Table(name = "WineGrape")
@IdClass(WineGrapeID.class)
public class WineGrape
{
    //private List<Wine> wines; //collection of wine recipes

    @Id
    @ManyToOne
    @JoinColumn(name = "wine_id", nullable = false)
    private Wine wine;

    @Id
    @ManyToOne
    @JoinColumn(name = "grape_id", nullable = false)
    private Grape grape;

    @Column(nullable = false)
    private int percentage; // of each grape in the wine, should add to 100%

    public WineGrape() {}

    public WineGrape(Wine wine, Grape grape, int percentage) {
        this.wine = wine;
        this.grape = grape;
        this.percentage = percentage;
    }
}
