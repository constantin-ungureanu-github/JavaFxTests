package plot.axis;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Axis<T extends Number> {

    // Actual value in pixels.
    private final DoubleProperty lengthProperty = new SimpleDoubleProperty(0);

    // Domain minimum value
    private final DoubleProperty minimumProperty = new SimpleDoubleProperty(0);

    // Domain maximum value
    private final DoubleProperty maximumProperty = new SimpleDoubleProperty(0);

    // Translation
    private final DoubleProperty translateProperty = new SimpleDoubleProperty(0);

    // Scaling
    private final DoubleProperty scaleProperty = new SimpleDoubleProperty(1);

    // Inversion
    private final BooleanProperty invertProperty = new SimpleBooleanProperty(false);

    public Axis(final double minimum, final double maximum) {
        setMaximum(maximum);
        setMinimum(minimum);
    }

    public DoubleProperty lengthProperty() {
        return lengthProperty;
    }

    public Double getLength() {
        return lengthProperty.get();
    }

//    public void setLength(final Double length) {
//        this.lengthProperty.set(length);
//    }

    public DoubleProperty minimumProperty() {
        return minimumProperty;
    }

    public Double getMinimum() {
        return minimumProperty.get();
    }

    public void setMinimum(final Double minimum) {
        this.minimumProperty.set(minimum);
    }

    public DoubleProperty maximumProperty() {
        return minimumProperty;
    }

    public Double getMaximum() {
        return maximumProperty.get();
    }

    public void setMaximum(final Double maximum) {
        this.maximumProperty.set(maximum);
    }

    public DoubleProperty translateProperty() {
        return translateProperty;
    }

    public Double getTranslate() {
        return translateProperty.get();
    }

    public void setTranslate(final Double translate) {
        this.translateProperty.set(translate);
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
    }

    public Double getScale() {
        return scaleProperty.get();
    }

    public void setScale(final Double scale) {
        this.scaleProperty.set(scale);
    }

    public BooleanProperty invertProperty() {
        return invertProperty;
    }

    public Boolean getInvert() {
        return invertProperty.get();
    }

    public void setInvert(final Boolean invert) {
        this.invertProperty.set(invert);
    }
}
