package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import plot.axis.AxesSystem;

public class Die extends Rectangle {
    private final AxesSystem axes;

    public Die(final AxesSystem axes, final double x, final double y, final double width, final double height) {
        super(width, height);

        this.axes = axes;

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(x).divide(axes.getXAxis().getLength()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(y).divide(axes.getYAxis().getLength()));

        widthProperty().bind(axes.getXAxis().lengthProperty().multiply(width).divide(axes.getXAxis().getLength()));
        heightProperty().bind(axes.getYAxis().lengthProperty().multiply(height).divide(axes.getYAxis().getLength()));

        setStroke(Color.MEDIUMBLUE);
        setFill(Color.ALICEBLUE.deriveColor(1, 1, 1, 0.7));
    }
}
