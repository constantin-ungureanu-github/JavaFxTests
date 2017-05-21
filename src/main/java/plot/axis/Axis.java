package plot.axis;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Axis {

    // Actual value in pixels.
    private final DoubleProperty lengthProperty = new SimpleDoubleProperty(0);

    // Translation
    private final DoubleProperty translateProperty = new SimpleDoubleProperty(0);

    // Scaling
    private final DoubleProperty scaleProperty = new SimpleDoubleProperty(1);

    // Inversion
    private final BooleanProperty invertProperty = new SimpleBooleanProperty(false);

    public Axis() {
    }

    public DoubleProperty lengthProperty() {
        return lengthProperty;
    }

    public Double getLength() {
        return lengthProperty.get();
    }

    public DoubleProperty translateProperty() {
        return translateProperty;
    }

    public Double getTranslate() {
        return translateProperty.get();
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
    }

    public Double getScale() {
        return scaleProperty.get();
    }

    public BooleanProperty invertProperty() {
        return invertProperty;
    }

    public Double getInvert() {
        return invertProperty.get() == false ? 1.0 : -1.0;
    }
}
