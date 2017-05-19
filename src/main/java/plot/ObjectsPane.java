package plot;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;
import plot.objects.Candidate;
import plot.objects.Field;
import plot.objects.Target;
import plot.objects.Wafer;

public class ObjectsPane extends Pane {
    final AxesSystem<Double, Double> axes;
    final EventHandler<MouseEvent> mousePressedEvent;

    public ObjectsPane(final AxesSystem<Double, Double> axes) {
        this.axes = axes;

        setPickOnBounds(false);

        cursorProperty().set(Cursor.CROSSHAIR);

        mousePressedEvent = event -> {
            Platform.runLater(() -> {
                getChildren().add(new Target(axes, event.getSceneX(), event.getSceneY(), 5));
            });
        };
    }

    public void addObjects() {
        Platform.runLater(() -> {
            final double width = axes.getXAxis().getLength();
            final double height = axes.getYAxis().getLength();

            final double offsetX = width / 10;
            final double offsetY = height / 10;

            final Wafer wafer = new Wafer(axes, axes.applyX(0), axes.applyY(0), 200, 200);
            getChildren().add(wafer);

            final Field field1 = new Field(axes, axes.applyX(0), axes.applyY(0), 40, 60);
            final Field field2 = new Field(axes, axes.applyX(1), axes.applyY(0), 40, 60);
            final Field field3 = new Field(axes, axes.applyX(2), axes.applyY(0), 40, 60);
            final Field field4 = new Field(axes, axes.applyX(-1), axes.applyY(0), 40, 60);
            final Field field5 = new Field(axes, axes.applyX(-2), axes.applyY(0), 40, 60);
            final Field field6 = new Field(axes, axes.applyX(-3), axes.applyY(0), 40, 60);
            getChildren().addAll(field1, field2, field3, field4, field5, field6);

            for (double x = 0; x <= width; x += offsetX) {
                for (double y = 0; y <= height; y += offsetY) {
                    final Candidate candidate = new Candidate(axes, x, y, 4);
                    getChildren().add(candidate);
                    candidate.setOnMousePressed(mousePressedEvent);
                }
            }
        });
    }
}
