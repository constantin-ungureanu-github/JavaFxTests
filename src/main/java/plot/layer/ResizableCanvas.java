package plot.layer;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import plot.axis.AxesSystem;

class ResizableCanvas extends Canvas {
    private final AxesSystem axes;
    private double orgWidth;
    private double orgHeight;

    public ResizableCanvas(final AxesSystem axes) {
        this.axes = axes;

        setPickOnBounds(false);

        widthProperty().bind(axes.getXAxis().lengthProperty().multiply(axes.getXAxis().scaleProperty()));
        heightProperty().bind(axes.getYAxis().lengthProperty().multiply(axes.getYAxis().scaleProperty()));

        orgWidth = getWidth();
        orgHeight = getHeight();

        widthProperty().addListener(event -> drawCanvas());
        heightProperty().addListener(event -> drawCanvas());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    private void drawCanvas() {
        Platform.runLater(() -> {
            final double width = getWidth();
            final double height = getHeight();

            final GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, orgWidth, orgHeight);

            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);

            gc.setStroke(Color.RED);
            gc.strokeRoundRect(10, 10, width - 20, height - 20, 10, 10);

            orgWidth = width;
            orgHeight = height;
        });
    }
}
