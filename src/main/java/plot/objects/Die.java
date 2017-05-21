package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import plot.axis.AxesSystem;

public class Die extends Rectangle {
    private final AxesSystem axes;

    public Die(final AxesSystem axes, final double x, final double y, final double width, final double height) {
        super(width, height);

        this.axes = axes;

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(x).divide(axes.getXAxis().getLength() * axes.getXAxis().getScale()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(axes.getYAxis().scaleProperty()).multiply(y).divide(axes.getYAxis().getLength() * axes.getYAxis().getScale()));

        widthProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(width).divide(axes.getXAxis().getLength() * axes.getXAxis().getScale()));
        heightProperty().bind(axes.getYAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(height).divide(axes.getYAxis().getLength() * axes.getXAxis().getScale()));

        setStroke(Color.MEDIUMBLUE);
        setFill(Color.ALICEBLUE.deriveColor(1, 1, 1, 0.7));
    }
}
