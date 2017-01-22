import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PhysicsTest extends Application {

    public static List<Circle> circles = new ArrayList<>();

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        final Group root = new Group();

        final Circle circle1 = new Circle(50);
        circle1.setStroke(Color.GREEN);
        circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 7));
        circle1.relocate(100, 100);

        final Circle circle2 = new Circle(50);
        circle2.setStroke(Color.BLUE);
        circle2.setFill(Color.BLUE.deriveColor(1, 1, 1, 7));
        circle2.relocate(200, 200);

        final MouseGestures mg = new MouseGestures();
        mg.makeDraggable(circle1);
        mg.makeDraggable(circle2);

        circles.add(circle1);
        circles.add(circle2);

        root.getChildren().addAll(circle1, circle2);

        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }

    public static class MouseGestures {

        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public void makeDraggable(final Node node) {
            node.setOnMousePressed(circleOnMousePressedEventHandler);
            node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
            node.setOnDragOver(onDragOverEventHandler(node));
        }

        private EventHandler<? super DragEvent> onDragOverEventHandler(final Node node) {
            return event -> {
                if ((event.getGestureSource() != node) && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            };
        }

        EventHandler<MouseEvent> circleOnMousePressedEventHandler = t -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            final Circle p = ((Circle) (t.getSource()));

            orgTranslateX = p.getCenterX();
            orgTranslateY = p.getCenterY();
        };

        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = t -> {
            final double offsetX = t.getSceneX() - orgSceneX;
            final double offsetY = t.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            final Circle p = ((Circle) (t.getSource()));

            p.setCenterX(newTranslateX);
            p.setCenterY(newTranslateY);

            for (final Circle c : circles) {

                if (c == p) {
                    continue;
                }

                if (c.getBoundsInParent().intersects(p.getBoundsInParent())) {
                    System.out.println("Overlapping!");
                }
            }
        };
    }
}
