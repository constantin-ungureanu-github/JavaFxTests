import java.util.function.Function;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CartesianPlot extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final Axes axes = new Axes(400, 300, -8, 8, 1, -6, 6, 1);

        final Plot plot = new Plot(x -> .25 * (x + 4) * (x + 1) * (x - 2), -8, 8, 0.1, axes);

        final StackPane layout = new StackPane(plot);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");

        stage.setTitle("y = \u00BC(x+4)(x+1)(x-2)");
        stage.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        stage.show();
    }

    class Axes extends Pane {
        private final NumberAxis xAxis;
        private final NumberAxis yAxis;

        public Axes(final int width, final int height, final double xLow, final double xHi, final double xTickUnit, final double yLow, final double yHi,
                final double yTickUnit) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(false);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(false);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

            getChildren().setAll(xAxis, yAxis);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }

        public NumberAxis getYAxis() {
            return yAxis;
        }
    }

    class Plot extends Pane {
        public Plot(final Function<Double, Double> function, final double xMin, final double xMax, final double xInc, final Axes axes) {
            final Path path = new Path();
            path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.7));
            path.setStrokeWidth(2);

            path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));

            double x = xMin;
            double y = function.apply(x);

            path.getElements().add(new MoveTo(mapX(x, axes), mapY(y, axes)));

            x += xInc;
            while (x < xMax) {
                y = function.apply(x);

                path.getElements().add(new LineTo(mapX(x, axes), mapY(y, axes)));

                x += xInc;
            }

            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            getChildren().setAll(axes, path);
        }

        private double mapX(final double x, final Axes axes) {
            final double tx = axes.getPrefWidth() / 2;
            final double sx = axes.getPrefWidth() / (axes.getXAxis().getUpperBound() - axes.getXAxis().getLowerBound());

            return (x * sx) + tx;
        }

        private double mapY(final double y, final Axes axes) {
            final double ty = axes.getPrefHeight() / 2;
            final double sy = axes.getPrefHeight() / (axes.getYAxis().getUpperBound() - axes.getYAxis().getLowerBound());

            return (-y * sy) + ty;
        }
    }
}
