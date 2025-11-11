package bg.tu_varna.sit.virtualvineyard.models;

import java.io.Serializable;
import java.util.Objects;

public class BottledWineID implements Serializable {
    private Long wine;
    private Long bottle;

    public BottledWineID() {}

    public BottledWineID(Long wine, Long bottle) {
        this.wine = wine;
        this.bottle = bottle;
    }

    public Long getWine() {
        return wine;
    }

    public void setWine(Long wine) {
        this.wine = wine;
    }

    public Long getBottle() {
        return bottle;
    }

    public void setBottle(Long bottle) {
        this.bottle = bottle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BottledWineID)) return false;
        BottledWineID that = (BottledWineID) o;
        return Objects.equals(wine, that.wine) &&
                Objects.equals(bottle, that.bottle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wine, bottle);
    }
}
