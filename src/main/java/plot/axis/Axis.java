package plot.axis;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Side;
import javafx.scene.layout.Region;

public class Axis<T extends Number> extends Region {

    // value
    DoubleProperty length = new SimpleDoubleProperty();

    // min
    double min;

    // max
    double max;

    // unit

    // side
    Side side;

    // inverted

    // granularity

    // translate

    // scale

    // padding

    public Axis() {
        setPickOnBounds(false);
    }

    public Axis(final double min, final double max)  {
        this.min = min;
        this.max = max;

        setPickOnBounds(false);
    }

    public DoubleProperty getLengthProperty() {
        return length;
    }

    public Double getLength() {
        return length.get();
    }

    public void setLength(final Double length) {
        this.length.set(length);
    }

    public void setSide(final Side side) {
        this.side = side;
    }

    public double getMaximum() {
        return max;
    }

    public double getMinimum() {
        return min;
    }
}
