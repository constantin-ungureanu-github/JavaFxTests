package plot;

import java.util.function.Function;

import javafx.scene.layout.Pane;
import plot.axis.AxesSystem;

public class PlotPane extends Pane {
    final AxesSystem<Double, Double> axes;
    final Function<Double, Double> function;
    final Plot plot;

    public PlotPane(final AxesSystem<Double, Double> axes) {
        this.axes = axes;

        setPickOnBounds(false);

        axes.getXAxis().getLengthProperty().bind(widthProperty());
        axes.getYAxis().getLengthProperty().bind(heightProperty());

        widthProperty().addListener(event -> drawPlot());
        heightProperty().addListener(event -> drawPlot());

        function = x -> Math.sinh(x);
        plot = new Plot(axes, function, 0.01);
    }

    public void drawPlot() {
        getChildren().clear();

        plot.drawPlot();

        plot.setOnMouseClicked(event -> {
            System.out.println(axes.invertX(event.getSceneX()));
            System.out.println(axes.invertY(event.getSceneY()));
        });

        getChildren().add(plot);
    }
}
