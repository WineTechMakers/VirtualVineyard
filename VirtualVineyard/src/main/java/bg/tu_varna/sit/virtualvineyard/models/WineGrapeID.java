package bg.tu_varna.sit.virtualvineyard.models;

import java.io.Serializable;
import java.util.Objects;

public class WineGrapeID implements Serializable {
    private Long wine;
    private Long grape;

    public WineGrapeID() {}

    public WineGrapeID(Long wine, Long grape) {
        this.wine = wine;
        this.grape = grape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WineGrapeID)) return false;
        WineGrapeID wg = (WineGrapeID) o;
        return Objects.equals(wine, wg.wine) &&
                Objects.equals(grape, wg.grape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wine, grape);
    }
}
