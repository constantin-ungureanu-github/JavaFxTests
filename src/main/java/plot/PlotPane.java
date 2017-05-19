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

        this.axes.getXAxis().lengthProperty().addListener(event -> drawPlot());
        this.axes.getYAxis().lengthProperty().addListener(event -> drawPlot());

        this.axes.getXAxis().scaleProperty().addListener(event -> drawPlot());
        this.axes.getYAxis().scaleProperty().addListener(event -> drawPlot());

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
