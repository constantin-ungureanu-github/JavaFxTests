package org.canvas.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxCanvasExample5 extends Application {
    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Create the Canvas
        final Canvas canvas = new Canvas(400, 200);
        // Get the graphics context of the canvas
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        // Load the Image
        final String imagePath = "file:\\java-logo.gif";
        final Image image = new Image(imagePath);
        // Draw the Image
        gc.drawImage(image, 10, 10, 200, 200);
        gc.drawImage(image, 220, 50, 100, 70);

        // Create the Pane
        final Pane root = new Pane();
        // Set the Style-properties of the Pane
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        // Add the Canvas to the Pane
        root.getChildren().add(canvas);
        // Create the Scene
        final Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("Drawing an Image on a Canvas");
        // Display the Stage
        stage.show();
    }
}
