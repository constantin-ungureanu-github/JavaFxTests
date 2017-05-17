package plot;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    public ResizableCanvas() {
        widthProperty().addListener(event -> draw());
        heightProperty().addListener(event -> draw());

        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);

        setPickOnBounds(false);
    }

    private void draw() {
        final double width = getWidth();
        final double height = getHeight();

        final GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);

        gc.setStroke(Color.RED);
        gc.strokeRoundRect(10, 10, width - 20, height - 20, 10, 10);
    }

    @Override
    public boolean isResizable() {
        return true;
    }
}
