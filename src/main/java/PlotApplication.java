import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import plot.LayeredPlot;
import plot.axis.AxesSystem;
import plot.layer.CanvasLayer;
import plot.layer.ObjectsLayer;
import plot.layer.PlotLayer;
import plot.objects.Candidate;
import plot.objects.Field;
import plot.objects.Target;
import plot.objects.Wafer;

public class PlotApplication extends Application {
    final static int DEFAULT_WIDTH = 800;
    final static int DEFAULT_HEIGHT = 600;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final AxesSystem axes = new AxesSystem(-10, 10, -10, 10);
        final Pane canvasPane = new CanvasLayer(axes);
        final Pane overlayPane = new ObjectsLayer(axes);
        final Pane plotPane = new PlotLayer(axes);

        final LayeredPlot plot = new LayeredPlot(axes, canvasPane, overlayPane, plotPane);

        primaryStage.setScene(new Scene(plot, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.AZURE));
        primaryStage.show();

        addObjects(axes, overlayPane);
    }

    public void addObjects(final AxesSystem axes, final Pane pane) {
        Platform.runLater(() -> {
            final double width = axes.getXAxis().getLength();
            final double height = axes.getYAxis().getLength();

            final double offsetX = width / 10;
            final double offsetY = height / 10;

            final Wafer wafer = new Wafer(axes, axes.applyX(0), axes.applyY(0), 200, 200);
            pane.getChildren().add(wafer);

            final Field field1 = new Field(axes, axes.applyX(0), axes.applyY(0), 40, 60);
            final Field field2 = new Field(axes, axes.applyX(1), axes.applyY(0), 40, 60);
            final Field field3 = new Field(axes, axes.applyX(2), axes.applyY(0), 40, 60);
            final Field field4 = new Field(axes, axes.applyX(-1), axes.applyY(0), 40, 60);
            final Field field5 = new Field(axes, axes.applyX(-2), axes.applyY(0), 40, 60);
            final Field field6 = new Field(axes, axes.applyX(-3), axes.applyY(0), 40, 60);
            pane.getChildren().addAll(field1, field2, field3, field4, field5, field6);

            for (double x = 0; x <= width; x += offsetX) {
                for (double y = 0; y <= height; y += offsetY) {
                    final Candidate candidate = new Candidate(axes, x, y, 4);
                    pane.getChildren().add(candidate);
                    candidate.setOnMousePressed(event -> {
                        Platform.runLater(() -> {
                            pane.getChildren().add(new Target(axes, event.getSceneX(), event.getSceneY(), 5));
                        });
                    });
                }
            }
        });
    }
}
