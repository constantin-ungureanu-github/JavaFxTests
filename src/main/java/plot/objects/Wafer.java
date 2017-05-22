package plot.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import plot.axis.AxesSystem;

public class Wafer extends Ellipse {
    private final AxesSystem axes;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public Wafer(final AxesSystem axes, final double x, final double y, final double radiusX, final double radiusY) {
        super(radiusX, radiusY);

        this.axes = axes;

        setStroke(Color.DARKBLUE);
        setFill(Color.LIGHTBLUE.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(x / (axes.getXAxis().getLength() * axes.getXAxis().getScale())));
        translateYProperty().bind(axes.getYAxis().lengthProperty().subtract(axes.getYAxis().lengthProperty().multiply(axes.getYAxis().scaleProperty()).multiply((axes.getYAxis().getLength() - y) / (axes.getYAxis().getLength() * axes.getYAxis().getScale()))));

        radiusXProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(radiusX).divide(axes.getXAxis().getLength() * axes.getXAxis().getScale()));
        radiusYProperty().bind(axes.getYAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(radiusY).divide(axes.getYAxis().getLength() * axes.getYAxis().getScale()));

        setOnMousePressed(event -> {
            event.consume();

            translateXProperty().unbind();
            translateYProperty().unbind();

            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();

            orgTranslateX = getTranslateX();
            orgTranslateY = getTranslateY();
        });

        setOnMouseDragged(event -> {
            event.consume();

            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            setTranslateX(newTranslateX);
            setTranslateY(newTranslateY);
        });

        setOnMouseReleased(event -> {
            event.consume();

            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()).multiply(newTranslateX / (axes.getXAxis().getLength() * axes.getXAxis().getScale())));
            translateYProperty().bind(axes.getYAxis().lengthProperty().subtract(axes.getYAxis().lengthProperty().multiply(axes.getYAxis().scaleProperty()).multiply((axes.getYAxis().getLength() - newTranslateY) / (axes.getYAxis().getLength() * axes.getYAxis().getScale()))));
        });
    }
}
