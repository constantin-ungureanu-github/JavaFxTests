package plot;

import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;

public class CanvasPane extends Pane {
    private final AxesSystem<Double, Double> axes;
    private final ResizableCanvas canvas;

    public CanvasPane(final AxesSystem<Double, Double> axes) {
        this.axes = axes;

        setPickOnBounds(false);

        canvas = new ResizableCanvas(this.axes);

        getChildren().add(canvas);
    }
}
