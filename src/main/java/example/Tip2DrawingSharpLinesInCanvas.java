package example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tip2DrawingSharpLinesInCanvas extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final MyCanvas canvasBlurry = new MyCanvas(false);
        final MyCanvas canvasSharp = new MyCanvas(true);

        final Label labelBlurry = new Label("Blurry");
        final Label labelSharp = new Label("Sharp");

        VBox.setMargin(canvasBlurry, new Insets(10));
        VBox.setMargin(canvasSharp, new Insets(10));

        VBox.setMargin(labelBlurry, new Insets(10, 10, 0, 10));
        VBox.setMargin(labelSharp, new Insets(10, 10, 0, 10));

        final VBox box = new VBox();
        box.getChildren().add(labelBlurry);
        box.getChildren().add(canvasBlurry);
        box.getChildren().add(labelSharp);
        box.getChildren().add(canvasSharp);

        stage.setScene(new Scene(box));
        stage.setTitle("Tip 2: Sharp Lines in Canvas");
        stage.show();
    }

    class MyCanvas extends Canvas {

        public MyCanvas(final boolean drawSharpLines) {

            setWidth(150);
            setHeight(150);

            final double w = getWidth();
            final double h = getHeight();

            final GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, w, h);

            gc.setStroke(Color.GRAY);
            gc.strokeRect(0, 0, w, h);

            for (double y = 20; y <= (h - 20); y += 10) {
                if (drawSharpLines) {
                    // Snap the y coordinate
                    gc.strokeLine(10, snap(y), w - 10, snap(y));
                } else {
                    gc.strokeLine(10, y, w - 10, y);
                }
            }
        }

        private double snap(final double y) {
            return ((int) y) + .5;
        }
    }
}
