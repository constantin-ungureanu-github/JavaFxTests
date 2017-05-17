package example;

import java.util.ArrayList;
import java.util.List;

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

public class DragNodes extends Application {
    public static List<Circle> circles = new ArrayList<>();

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final Group root = new Group();

        final Canvas canvas = new Canvas(300, 300);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        final Circle circle1 = new Circle(50);
        circle1.setStroke(Color.GREEN);
        circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));
        circle1.relocate(100, 100);

        final Circle circle2 = new Circle(50);
        circle2.setStroke(Color.BLUE);
        circle2.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.7));
        circle2.relocate(200, 200);

        final Line line = new Line(circle1.getLayoutX(), circle1.getLayoutY(), circle2.getLayoutX(), circle2.getLayoutY());
        line.setStrokeWidth(20);

        final Pane overlay = new Pane();
        overlay.getChildren().addAll(circle1, circle2, line);

        final MouseGestures mg = new MouseGestures();
        mg.makeDraggable(circle1);
        mg.makeDraggable(circle2);
        mg.makeDraggable(line);

        root.getChildren().addAll(canvas, overlay);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void drawShapes(final GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeRoundRect(10, 10, 230, 230, 10, 10);
    }

    public static class MouseGestures {

        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public void makeDraggable(final Node node) {
            node.setOnMousePressed(circleOnMousePressedEventHandler);
            node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        }

        EventHandler<MouseEvent> circleOnMousePressedEventHandler = t -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            if (t.getSource() instanceof Circle) {
                final Circle p1 = ((Circle) (t.getSource()));

                orgTranslateX = p1.getCenterX();
                orgTranslateY = p1.getCenterY();

            } else {

                final Node p2 = ((Node) (t.getSource()));

                orgTranslateX = p2.getTranslateX();
                orgTranslateY = p2.getTranslateY();

            }
        };

        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = t -> {
            final double offsetX = t.getSceneX() - orgSceneX;
            final double offsetY = t.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            if (t.getSource() instanceof Circle) {
                final Circle p1 = ((Circle) (t.getSource()));

                p1.setCenterX(newTranslateX);
                p1.setCenterY(newTranslateY);

            } else {
                final Node p2 = ((Node) (t.getSource()));

                p2.setTranslateX(newTranslateX);
                p2.setTranslateY(newTranslateY);
            }
        };
    }
}
