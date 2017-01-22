package org.canvas;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CanvasDoodleTest extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Canvas Doodle Test");
        final Group root = new Group();

        final Rectangle rect = new Rectangle(400, 400);
        drawBackground(rect);
        root.getChildren().add(rect);

        final Canvas canvas = new Canvas(200, 200);
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        reset(canvas, Color.BLUE);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> gc.clearRect(e.getX() - 2, e.getY() - 2, 5, 5));

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() > 1) {
                reset(canvas, Color.BLUE);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    private void drawBackground(final Rectangle rectangle) {
        rectangle.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.RED),
                new Stop(1, Color.YELLOW)));
    }

    private void reset(final Canvas canvas, final Color color) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
