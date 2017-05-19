package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import plot.axis.AxesSystem;

public class Candidate extends Circle {
    private final AxesSystem<Double, Double> axes;

    public Candidate(final AxesSystem<Double, Double> axes, final double x, final double y, final double width) {
        super(width);

        this.axes = axes;

        setStroke(Color.BLACK);
        setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(x).divide(axes.getXAxis().getLength()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(y).divide(axes.getYAxis().getLength()));

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
