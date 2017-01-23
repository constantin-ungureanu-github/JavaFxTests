package org.draggable;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class DragNodes2 extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final Group root = new Group();

        final CanvasPane canvasPane = new CanvasPane(300, 300);

        final GraphicsContext context = canvasPane.getCanvas().getGraphicsContext2D();
        drawShapes(context);

        final Circle circle1 = drawCircle(50, Color.RED, 0, 0);
        final Circle circle2 = drawCircle(50, Color.BLUE, 100, 100);
        final Line line = drawLine(circle1.getLayoutX(), circle1.getLayoutY(), circle2.getLayoutX(), circle2.getLayoutY(), 20);

        final Pane overlay = new Pane();
        overlay.getChildren().addAll(circle1, circle2, line);

        final MouseGestures mg = new MouseGestures();
        mg.makeDraggable(circle1);
        mg.makeDraggable(circle2);
        mg.makeDraggable(line);

        root.getChildren().addAll(canvasPane, overlay);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private Line drawLine(final double x1, final double y1, final double x2, final double y2, final double value) {
        final Line line = new Line(x1, y1, x2, y2);
        line.setStrokeWidth(value);
        return line;
    }

    private Circle drawCircle(final double radious, final Color color, final double x, final double y) {
        final Circle circle = new Circle(radious);
        circle.setStroke(color);
        circle.setFill(color.deriveColor(1, 1, 1, 0.7));
        circle.relocate(x, y);
        return circle;
    }

    private void drawShapes(final GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeRoundRect(10, 10, 230, 230, 10, 10);
    }

    private static class MouseGestures {
        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public void makeDraggable(final Node node) {
            node.setOnMousePressed(onMousePressedEventHandler);
            node.setOnMouseDragged(onMouseDraggedEventHandler);
            node.setOnMouseReleased(onMouseReleasedEventHandler);
        }

        final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();

            final Node node = ((Node) (event.getSource()));
            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        };

        final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            final Node node = ((Node) (event.getSource()));
            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);
        };

        final EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            final Node node = ((Node) (event.getSource()));
            node.setTranslateX(0);
            node.setTranslateY(0);
        };
    }

    private static class CanvasPane extends Pane {
        private final Canvas canvas;

        public CanvasPane(final double width, final double height) {
            canvas = new Canvas(width, height);
            getChildren().add(canvas);
        }

        public Canvas getCanvas() {
            return canvas;
        }

        @Override
        protected void layoutChildren() {
            final double x = snappedLeftInset();
            final double y = snappedTopInset();
            final double w = snapSize(getWidth()) - x - snappedRightInset();
            final double h = snapSize(getHeight()) - y - snappedBottomInset();
            canvas.setLayoutX(x);
            canvas.setLayoutY(y);
            canvas.setWidth(w);
            canvas.setHeight(h);
        }
    }
}
