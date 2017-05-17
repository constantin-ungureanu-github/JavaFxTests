package plot;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import plot.objects.Candidate;
import plot.objects.Target;

public class ObjectsPane extends Pane {
    public ObjectsPane() {
        setPickOnBounds(false);
        setOnMousePressed(onMouseClicked);
        cursorProperty().set(Cursor.CROSSHAIR);
    }

    public void addGrid() {
        final double width = getWidth();
        final double height = getHeight();

        final double offsetX = getWidth() / 10;
        final double offsetY = getHeight() / 10;

        for (double x = 0; x <= width; x += offsetX) {
            for (double y = 0; y <= height; y += offsetY) {
                final Candidate candidate = new Candidate(this, x, y, 4);
                getChildren().add(candidate);
            }
        }
    }

    private final EventHandler<MouseEvent> onMouseClicked = event -> {
        if (!event.isPrimaryButtonDown()) {
            return;
        }

        final Target target = new Target(this, event.getSceneX(), event.getSceneY(), 5);

        getChildren().add(target);
    };
}
