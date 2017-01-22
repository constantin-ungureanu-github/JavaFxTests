package org.canvas;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class CanvasTest extends Application {
    private final Canvas canvas = new Canvas(200, 200);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Group root = new Group();

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Canvas Test");
        moveCanvas(0, 0);
        drawDShape();
        drawRadialGradient(Color.RED, Color.YELLOW);
        drawLinearGradient(Color.BLUE, Color.GREEN);
        drawDropShadow(Color.GRAY, Color.BLUE, Color.GREEN, Color.RED);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    private void moveCanvas(final int x, final int y) {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
    }

    private void drawDShape() {
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
        gc.closePath();
    }

    private void drawRadialGradient(final Color firstColor, final Color lastColor) {
        gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true, CycleMethod.REFLECT, new Stop(0.0, firstColor), new Stop(1.0, lastColor)));
        gc.fill();
    }

    private void drawLinearGradient(final Color firstColor, final Color secondColor) {
        final LinearGradient lg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.REFLECT, new Stop(0.0, firstColor), new Stop(1.0, secondColor));
        gc.setStroke(lg);
        gc.setLineWidth(20);
        gc.stroke();
    }

    private void drawDropShadow(final Color firstColor, final Color secondColor, final Color thirdColor, final Color fourthColor) {
        gc.applyEffect(new DropShadow(20, 20, 0, firstColor));
        gc.applyEffect(new DropShadow(20, 0, 20, secondColor));
        gc.applyEffect(new DropShadow(20, -20, 0, thirdColor));
        gc.applyEffect(new DropShadow(20, 0, -20, fourthColor));
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
