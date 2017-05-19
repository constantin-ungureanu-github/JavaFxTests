package plot;

import java.util.function.Function;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import plot.axis.AxesSystem;

public class Plot extends Region {
    final AxesSystem<Double, Double> axes;
    final Function<Double, Double> function;
    final double tick;

    public Plot(final AxesSystem<Double, Double> axes, final Function<Double, Double> function, final double tick) {
        this.axes = axes;
        this.function = function;
        this.tick = tick;

        setPickOnBounds(false);

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(this.axes.getXAxis().getLength(), axes.getYAxis().getLength());
    }

    public void drawPlot() {
        Platform.runLater(() -> {
            final Path path = new Path();
            path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.7));
            path.setStrokeWidth(2);

            path.setClip(new Rectangle(0, 0, axes.getXAxis().getLength(), axes.getYAxis().getLength()));

            double x = axes.getXAxis().getMinimum();
            double y = function.apply(x);

            path.getElements().add(new MoveTo(axes.applyX(x), axes.applyY(y)));

            x += tick;
            while (x < axes.getXAxis().getMaximum()) {
                y = function.apply(x);

                path.getElements().add(new LineTo(axes.applyX(x), axes.applyY(y)));

                x += tick;
            }

            getChildren().clear();
            getChildren().setAll(path);
        });
    }
}
