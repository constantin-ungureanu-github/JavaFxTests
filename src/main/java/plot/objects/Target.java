package plot.objects;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import plot.axis.AxesSystem;

public class Target extends Circle {
    final AxesSystem axes;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public Target(final AxesSystem axes, final double x, final double y, final double width) {
        super(width);

        this.axes = axes;

        setStroke(Color.BLACK);
        setFill(Color.WHITE.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(x).divide(axes.getXAxis().getLength()));
        translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(y).divide(axes.getYAxis().getLength()));

        cursorProperty().set(Cursor.HAND);

        setOnMouseEntered(event -> {
            setScaleX(2);
            setScaleY(2);
        });

        setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });

        setOnMousePressed(event -> {
            translateXProperty().unbind();
            translateYProperty().unbind();

            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();

            final Node node = ((Node) (event.getSource()));

            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();

            event.consume();
        });

        setOnMouseDragged(event -> {
            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            final Node node = ((Node) (event.getSource()));

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);
        });

        setOnMouseReleased(event -> {
            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            translateXProperty().bind(axes.getXAxis().lengthProperty().multiply(newTranslateX).divide(axes.getXAxis().getLength()));
            translateYProperty().bind(axes.getYAxis().lengthProperty().multiply(newTranslateY).divide(axes.getYAxis().getLength()));

            event.consume();
        });
    }
}
