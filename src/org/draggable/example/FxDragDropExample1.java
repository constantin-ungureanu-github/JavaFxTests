package org.draggable.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxDragDropExample1 extends Application {
    // Create the TextFields
    TextField sourceFld = new TextField("This is the Source Text");
    TextField targetFld = new TextField("Drag and drop the source text here");

    // Create the LoggingArea
    TextArea loggingArea = new TextArea("");

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Set the Size of the TextFields
        sourceFld.setPrefSize(200, 20);
        targetFld.setPrefSize(200, 20);

        // Create the Labels
        final Label sourceLbl = new Label("Source Node:");
        final Label targetLbl = new Label("Target Node:");

        // Create the GridPane
        final GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(20);

        // Add the Labels and Fields to the Pane
        pane.addRow(0, sourceLbl, sourceFld);
        pane.addRow(1, targetLbl, targetFld);

        // Add mouse event handlers for the source
        sourceFld.setOnMousePressed(event -> {
            sourceFld.setMouseTransparent(true);
            writelog("Event on Source: mouse pressed");
            event.setDragDetect(true);
        });

        sourceFld.setOnMouseReleased(event -> {
            sourceFld.setMouseTransparent(false);
            writelog("Event on Source: mouse released");
        });

        sourceFld.setOnMouseDragged(event -> {
            writelog("Event on Source: mouse dragged");
            event.setDragDetect(false);
        });

        sourceFld.setOnDragDetected(event -> {
            sourceFld.startFullDrag();
            writelog("Event on Source: drag detected");
        });

        // Add mouse event handlers for the target
        targetFld.setOnMouseDragEntered(event -> writelog("Event on Target: mouse dragged"));

        targetFld.setOnMouseDragOver(event -> writelog("Event on Target: mouse drag over"));

        targetFld.setOnMouseDragReleased(event -> {
            targetFld.setText(sourceFld.getSelectedText());
            writelog("Event on Target: mouse drag released");
        });

        targetFld.setOnMouseDragExited(event -> writelog("Event on Target: mouse drag exited"));

        // Create the VBox
        final VBox root = new VBox();
        // Add the Pane and The LoggingArea to the VBox
        root.getChildren().addAll(pane, loggingArea);
        // Set the Style of the VBox
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        // Create the Scene
        final Scene scene = new Scene(root, 300, 200);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("A Press Drag Release Example");
        // Display the Stage
        stage.show();
    }

    // Helper Method for Logging
    private void writelog(final String text) {
        loggingArea.appendText(text + "\n");
    }
}
