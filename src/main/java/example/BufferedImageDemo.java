package example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BufferedImageDemo extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final HBox box = new HBox();
        box.setStyle("-fx-background-color:yellow;");

        final int[] types = { BufferedImage.TYPE_3BYTE_BGR, BufferedImage.TYPE_4BYTE_ABGR, BufferedImage.TYPE_INT_RGB, BufferedImage.TYPE_INT_ARGB };
        for (final int t : types) {
            final BufferedImage bimg = new BufferedImage(100, 100, t);
            final Graphics2D graphics = bimg.createGraphics();
            graphics.setColor(Color.CYAN);
            graphics.fillOval(0, 0, 40, 40);
            graphics.dispose();

            final WritableImage img = SwingFXUtils.toFXImage(bimg, null);
            final ImageView iview = new ImageView(img);

            box.getChildren().add(iview);
        }
        final Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }
}
