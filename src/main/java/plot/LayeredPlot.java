package plot;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import plot.axis.AxesSystem;

public class LayeredPlot extends StackPane {
    final AxesSystem<Double, Double> axes;

    final CanvasPane canvasPane;

    final ObjectsPane overlayPane;

    final Pane plotPane;

    public LayeredPlot() {
        axes = new AxesSystem<>(-10, 10, -10, 10);

        axes.getXAxis().lengthProperty().bind(widthProperty());
        axes.getYAxis().lengthProperty().bind(heightProperty());

        axes.getXAxis().translateProperty().bind(widthProperty().divide(2));
        axes.getYAxis().translateProperty().bind(heightProperty().divide(2));

        axes.getXAxis().scaleProperty().bind(scaleXProperty());
        axes.getYAxis().scaleProperty().bind(scaleYProperty());

        axes.getXAxis().invertProperty().set(false);
        axes.getYAxis().invertProperty().set(true);

        canvasPane = new CanvasPane(axes);

        overlayPane = new ObjectsPane(axes);

        plotPane = new PlotPane(axes);

        getChildren().addAll(canvasPane, plotPane, overlayPane);

        setOnScroll(event -> {
            event.consume();
            final double deltaY = event.getDeltaY();

            double zoomFactor = 1.05;
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            setScaleX(getScaleX() * zoomFactor);
            setScaleY(getScaleY() * zoomFactor);

//            axes.getXAxis().lengthProperty().multiply(zoomFactor);
//            axes.getYAxis().lengthProperty().multiply(zoomFactor);

            event.consume();
        });
    }

    public void chooseAndSaveToFile() {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        final File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                final WritableImage writableImage = snapshot(new SnapshotParameters(), null);
                final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);

                ImageIO.write(renderedImage, "png", file);
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addGrid() {
        overlayPane.addObjects();
    }
}
