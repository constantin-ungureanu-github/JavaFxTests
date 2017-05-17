package plot;

import java.awt.image.BufferedImage;
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
        axes = new AxesSystem<>(getWidth(), getHeight(), -8, 8, -8, 8);

        canvasPane = new CanvasPane();

        overlayPane = new ObjectsPane();

        plotPane = new PlotPane(axes);

        axes.getXAxis().getLengthProperty().bind(widthProperty());
        axes.getYAxis().getLengthProperty().bind(heightProperty());

        getChildren().addAll(axes, canvasPane, plotPane, overlayPane);
    }

    public void saveToFile() {
        final WritableImage image = snapshot(new SnapshotParameters(), null);

        final File outputFile = new File("pane.png");
        final BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
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
}
