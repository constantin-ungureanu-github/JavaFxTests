package plot.layer;

import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;

public class CanvasLayer extends Pane {
    private final AxesSystem axes;
    private final ResizableCanvas canvas;

    public CanvasLayer(final AxesSystem axes) {
        this.axes = axes;

        setPickOnBounds(false);

        canvas = new ResizableCanvas(axes);

        getChildren().add(canvas);
    }
}
