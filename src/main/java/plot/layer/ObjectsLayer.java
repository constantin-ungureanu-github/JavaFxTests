package plot.layer;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;

public class ObjectsLayer extends Pane {
    final AxesSystem axes;

    public ObjectsLayer(final AxesSystem axes) {
        this.axes = axes;

        setPickOnBounds(false);

        cursorProperty().set(Cursor.CROSSHAIR);
    }
}
