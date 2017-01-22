import java.util.function.DoubleUnaryOperator;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimatingPlot extends Application {

    private enum NamedFunction {
        SIN(x -> (-Math.sin((2 * Math.PI * x) / 100) * 200) + 200, "Sine"), COS(x -> (-Math.cos((2 * Math.PI * x) / 100) * 200) + 200,
                "Cosine"), LOG(x -> (-Math.log(x) * 20) + 320, "Log");

        private final DoubleUnaryOperator function;
        private final String displayName;

        NamedFunction(final DoubleUnaryOperator function, final String displayName) {
            this.function = function;
            this.displayName = displayName;
        }

        public DoubleUnaryOperator getFunction() {
            return function;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private Transition animation;

    @Override
    public void start(final Stage primaryStage) {

        final Pane pane = new Pane();
        pane.setMinSize(400, 400);
        pane.setPadding(new Insets(10));

        final ComboBox<NamedFunction> functionChoice = new ComboBox<>();
        functionChoice.getItems().addAll(NamedFunction.values());

        functionChoice.valueProperty().addListener((obs, oldFunction, newFunction) -> {
            pane.getChildren().clear();
            if (newFunction != null) {
                final Plot plot = new Plot(newFunction.getFunction(), 400, 400, 0, 400);
                pane.getChildren().add(plot.getPlot());

                if (animation != null) {
                    animation.stop();
                }

                final Circle circle = new Circle(10);
                pane.getChildren().add(circle);

                animation = plot.createTransition(Duration.seconds(5), circle.centerXProperty(), circle.centerYProperty());

                animation.setCycleCount(Animation.INDEFINITE);
                animation.setAutoReverse(true);
                animation.play();
            }
        });

        BorderPane.setMargin(pane, new Insets(20));

        final BorderPane root = new BorderPane(pane, functionChoice, null, null, null);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class Plot {
        private final DoubleUnaryOperator function;
        private final double startX;
        private final double endX;
        private final Canvas plot;

        public Plot(final DoubleUnaryOperator function, final double width, final double height, final double startX, final double endX) {
            this.function = function;
            this.startX = startX;
            this.endX = endX;

            plot = new Canvas(width, height);
            final GraphicsContext gc = plot.getGraphicsContext2D();
            gc.moveTo(startX, function.applyAsDouble(startX));
            for (double x = startX; x < endX; x++) {
                gc.lineTo(x, function.applyAsDouble(x));
            }
            gc.stroke();
        }

        public Canvas getPlot() {
            return plot;
        }

        public Transition createTransition(final Duration cycleDuration, final DoubleProperty x, final DoubleProperty y) {
            return new Transition() {

                {
                    setCycleDuration(cycleDuration);
                }

                @Override
                protected void interpolate(final double frac) {
                    final double xValue = ((1 - frac) * startX) + (frac * endX);
                    final double yValue = function.applyAsDouble(xValue);
                    x.set(xValue);
                    y.set(yValue);
                }
            };
        }

    }

    public static void main(final String[] args) {
        launch(args);
    }
}
