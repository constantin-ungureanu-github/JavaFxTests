package plot.objects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import plot.axis.AxesSystem;

public class Wafer extends Ellipse {
    private final AxesSystem<Double, Double> axes;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public Wafer(final AxesSystem<Double, Double> axes, final double x, final double y, final double radiusX, final double radiusY) {
        super(radiusX, radiusY);

        this.axes = axes;

        setStroke(Color.DARKBLUE);
        setFill(Color.LIGHTBLUE.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(x).divide(axes.getXAxis().getLength()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(y).divide(axes.getYAxis().getLength()));

        radiusXProperty().bind(axes.getXAxis().lengthProperty().multiply(radiusX).divide(axes.getXAxis().getLength()));
        radiusYProperty().bind(axes.getYAxis().lengthProperty().multiply(radiusY).divide(axes.getYAxis().getLength()));

        setOnMouseClicked(event -> {
            System.out.println(axes.invertX(event.getSceneX()));
            System.out.println(axes.invertY(event.getSceneY()));
        });

        setOnMousePressed(event -> {
            event.consume();

            translateXProperty().unbind();
            translateYProperty().unbind();

            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();

            final Node node = ((Node) (event.getSource()));

            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        });

        setOnMouseDragged(event -> {
            event.consume();

            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            final Node node = ((Node) (event.getSource()));

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);
        });

        setOnMouseReleased(event -> {
            event.consume();

            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(newTranslateX).divide(axes.getXAxis().getLength()));
            translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(newTranslateY).divide(axes.getYAxis().getLength()));
        });
    }
}
