package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import plot.axis.AxesSystem;

public class Candidate extends Circle {
    private final AxesSystem axes;

    public Candidate(final AxesSystem axes, final double x, final double y, final double width) {
        super(width);

        this.axes = axes;

        setStroke(Color.BLACK);
        setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(x).divide(axes.getXAxis().getLength() * axes.getXAxis().getScale()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(axes.getYAxis().scaleProperty()).multiply(y).divide(axes.getYAxis().getLength() * axes.getYAxis().getScale()));

        setOnMouseEntered(event -> {
            setScaleX(2);
            setScaleY(2);
        });

        setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });
    }
}
