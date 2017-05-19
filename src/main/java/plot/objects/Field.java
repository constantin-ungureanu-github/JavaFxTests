package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import plot.axis.AxesSystem;

public class Field extends Rectangle {
    private final AxesSystem<Double, Double> axes;

    public Field(final AxesSystem<Double, Double> axes, final double x, final double y, final double width, final double height) {
        super(width, height);

        this.axes = axes;

        setStroke(Color.DARKBLUE);
        setFill(Color.ALICEBLUE.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(this.axes.getXAxis().lengthProperty().multiply(x).divide(this.axes.getXAxis().getLength()));
        translateYProperty().bind(this.axes.getYAxis().lengthProperty().multiply(y).divide(this.axes.getYAxis().getLength()));
        widthProperty().bind(this.axes.getXAxis().lengthProperty().multiply(width).divide(this.axes.getXAxis().getLength()));
        heightProperty().bind(this.axes.getYAxis().lengthProperty().multiply(height).divide(this.axes.getYAxis().getLength()));

        setOnMouseClicked(event -> {
            System.out.println(this.axes.invertX(event.getSceneX()));
            System.out.println(this.axes.invertY(event.getSceneY()));
        });
    }
}
