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
    private final AxesSystem axes;

    public LayeredPlot(final AxesSystem axes, final Pane... panes) {
        this.axes = axes;

        axes.getXAxis().lengthProperty().bind(widthProperty());
        axes.getYAxis().lengthProperty().bind(heightProperty());

        axes.getXAxis().invertProperty().set(false);
        axes.getYAxis().invertProperty().set(true);

        axes.getXAxis().translateProperty().bind(widthProperty().divide(2));
        axes.getYAxis().translateProperty().bind(heightProperty().divide(2));

        axes.getXAxis().scaleProperty().set(1);
        axes.getYAxis().scaleProperty().set(1);

        getChildren().addAll(panes);

        setOnScroll(event -> {
            event.consume();
            final double deltaY = event.getDeltaY();

            double zoomFactor = 1.05;
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }

            axes.getXAxis().scaleProperty().set(axes.getXAxis().getScale() * zoomFactor);
            axes.getYAxis().scaleProperty().set(axes.getYAxis().getScale() * zoomFactor);
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
}
