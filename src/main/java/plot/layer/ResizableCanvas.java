package plot.layer;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import plot.axis.AxesSystem;

class ResizableCanvas extends Canvas {
    private final AxesSystem axes;

    public ResizableCanvas(final AxesSystem axes) {
        this.axes = axes;

        setPickOnBounds(false);

        widthProperty().bind(axes.getXAxis().lengthProperty());
        heightProperty().bind(axes.getYAxis().lengthProperty());

        axes.getXAxis().lengthProperty().addListener(event -> drawCanvas());
        axes.getYAxis().lengthProperty().addListener(event -> drawCanvas());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    private void drawCanvas() {
        Platform.runLater(() -> {
            final double width = axes.getXAxis().getLength();
            final double height = axes.getYAxis().getLength();

            final GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);

            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);

            gc.setStroke(Color.RED);
            gc.strokeRoundRect(10, 10, width - 20, height - 20, 10, 10);
        });
    }
}
