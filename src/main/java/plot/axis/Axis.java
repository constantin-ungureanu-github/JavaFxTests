package plot.axis;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Side;
import javafx.scene.layout.Region;

class Axis<T extends Number> extends Region {

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
        setPickOnBounds(false);

        this.min = min;
        this.max = max;
    }

    public void setSide(final Side side) {
        this.side = side;
    }

    public double getUpperBound() {
        return max;
    }

    public double getLowerBound() {
        return min;
    }
}
