package plot.objects;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Target extends Circle {
    Pane parent;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public Target(final Pane parent, final double x, final double y, final double width) {
        super(width);

        this.parent = parent;
        setStroke(Color.BLACK);
        setFill(Color.WHITE.deriveColor(1, 1, 1, 0.7));

        translateXProperty().bind(parent.widthProperty().multiply(x).divide(parent.getWidth()));
        translateYProperty().bind(parent.heightProperty().multiply(y).divide(parent.getHeight()));

        cursorProperty().set(Cursor.HAND);

        setOnMouseEntered(onMouseEnteredEventHandler);
        setOnMouseExited(onMouseExitedEventHandler);

        setOnMousePressed(onMousePressedEventHandler);
        setOnMouseDragged(onMouseDraggedEventHandler);
        setOnMouseReleased(onMouseReleasedEventHandler);
    }

    private final EventHandler<MouseEvent> onMouseEnteredEventHandler = event -> {
        setScaleX(2);
        setScaleY(2);
    };

    private final EventHandler<MouseEvent> onMouseExitedEventHandler = event -> {
        setScaleX(1);
        setScaleY(1);
    };

    private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
        if (!event.isPrimaryButtonDown()) {
            return;
        }

        translateXProperty().unbind();
        translateYProperty().unbind();

        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        final Node node = ((Node) (event.getSource()));

        orgTranslateX = node.getTranslateX();
        orgTranslateY = node.getTranslateY();

        event.consume();
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
        if (!event.isPrimaryButtonDown()) {
            return;
        }

        final double offsetX = event.getSceneX() - orgSceneX;
        final double offsetY = event.getSceneY() - orgSceneY;

        final double newTranslateX = orgTranslateX + offsetX;
        final double newTranslateY = orgTranslateY + offsetY;

        final Node node = ((Node) (event.getSource()));

        node.setTranslateX(newTranslateX);
        node.setTranslateY(newTranslateY);
    };

    private final EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
        translateXProperty().bind(parent.widthProperty().multiply(event.getSceneX()).divide(parent.getWidth()));
        translateYProperty().bind(parent.heightProperty().multiply(event.getSceneY()).divide(parent.getHeight()));

        event.consume();
    };
}
