
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BufferedImageConversion extends Application {
    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final String imagePath = "panda.jpg";
        final Image image = new Image(imagePath);

        final ImageView imageView = new ImageView(image);

        final Button saveBtn = new Button("Save Image");
        saveBtn.setOnAction(e -> saveToFile(image));

        final VBox root = new VBox(10, imageView, saveBtn);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("");
        stage.show();
    }

    public static void saveToFile(final Image image) {
        final File outputFile = new File("panda.png");
        final BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
