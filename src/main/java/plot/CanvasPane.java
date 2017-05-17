package plot;

import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {
    final ResizableCanvas canvas = new ResizableCanvas();

    public CanvasPane() {
        setPickOnBounds(false);
        getChildren().add(canvas);
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
    }
}
