package plot;

import java.util.function.Function;

import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;

public class PlotPane extends Pane {
    public PlotPane() {
        setPickOnBounds(false);

        widthProperty().addListener(event -> {
            drawPlot();
        });

        heightProperty().addListener(event -> {
            drawPlot();
        });
    }

    public void drawPlot() {
        getChildren().clear();

        final AxesSystem<Double, Double> axes = new AxesSystem<>(getWidth(), getHeight(), -8, 8, -8, 8);
        final Function<Double, Double> function = x -> Math.sinh(x);

        final Plot plot = new Plot(axes, function, 0.01);
        plot.drawPlot();

        plot.setOnMouseClicked(event -> {
            System.out.println(axes.invertX(event.getSceneX()));
            System.out.println(axes.invertY(event.getSceneY()));
        });

        getChildren().add(plot);
    }
}
