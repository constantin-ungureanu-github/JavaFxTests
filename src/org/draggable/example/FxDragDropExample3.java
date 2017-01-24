package org.draggable.example;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxDragDropExample3 extends Application {
    ListView<Fruit> sourceView = new ListView<>();
    ListView<Fruit> targetView = new ListView<>();
    TextArea loggingArea = new TextArea("");

    static final DataFormat FRUIT_LIST = new DataFormat("FruitList");

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Create the Labels
        final Label sourceListLbl = new Label("Source List: ");
        final Label targetListLbl = new Label("Target List: ");
        final Label messageLbl = new Label("Select one or more fruits from a list, drag and drop them to another list");

        // Set the Size of the Views and the LoggingArea
        sourceView.setPrefSize(200, 200);
        targetView.setPrefSize(200, 200);
        loggingArea.setMaxSize(410, 200);

        // Add the fruits to the Source List
        sourceView.getItems().addAll(getFruitList());

        // Allow multiple-selection in lists
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        targetView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create the GridPane
        final GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        // Add the Labels and Views to the Pane
        pane.add(messageLbl, 0, 0, 3, 1);
        pane.addRow(1, sourceListLbl, targetListLbl);
        pane.addRow(2, sourceView, targetView);

        // Add mouse event handlers for the source
        sourceView.setOnDragDetected(event -> {
            writelog("Event on Source: drag detected");
            dragDetected(event, sourceView);
        });

        sourceView.setOnDragOver(event -> {
            writelog("Event on Source: drag over");
            dragOver(event, sourceView);
        });

        sourceView.setOnDragDropped(event -> {
            writelog("Event on Source: drag dropped");
            dragDropped(event, sourceView);
        });

        sourceView.setOnDragDone(event -> {
            writelog("Event on Source: drag done");
            dragDone(event, sourceView);
        });

        // Add mouse event handlers for the target
        targetView.setOnDragDetected(event -> {
            writelog("Event on Target: drag detected");
            dragDetected(event, targetView);
        });

        targetView.setOnDragOver(event -> {
            writelog("Event on Target: drag over");
            dragOver(event, targetView);
        });

        targetView.setOnDragDropped(event -> {
            writelog("Event on Target: drag dropped");
            dragDropped(event, targetView);
        });

        targetView.setOnDragDone(event -> {
            writelog("Event on Target: drag done");
            dragDone(event, targetView);
        });

        // Create the VBox
        final VBox root = new VBox();
        // Add the Pane and The LoggingArea to the VBox
        root.getChildren().addAll(pane, loggingArea);
        // Set the Style of the VBox
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("A Drag and Drop Example for Custom Data Types");
        stage.show();
    }

    // Create the Fruit List
    private ObservableList<Fruit> getFruitList() {
        final ObservableList<Fruit> list = FXCollections.<Fruit>observableArrayList();

        final Fruit apple = new Fruit("Apple");
        final Fruit orange = new Fruit("Orange");
        final Fruit papaya = new Fruit("Papaya");
        final Fruit mango = new Fruit("Mango");
        final Fruit grape = new Fruit("Grape");
        final Fruit guava = new Fruit("Guava");

        list.addAll(apple, orange, papaya, mango, grape, guava);

        return list;
    }

    private void dragDetected(final MouseEvent event, final ListView<Fruit> listView) {
        // Make sure at least one item is selected
        final int selectedCount = listView.getSelectionModel().getSelectedIndices().size();

        if (selectedCount == 0) {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        final Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        final ArrayList<Fruit> selectedItems = getSelectedFruits(listView);

        final ClipboardContent content = new ClipboardContent();
        content.put(FRUIT_LIST, selectedItems);

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(final DragEvent event, final ListView<Fruit> listView) {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        final Dragboard dragboard = event.getDragboard();

        if ((event.getGestureSource() != listView) && dragboard.hasContent(FRUIT_LIST)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDropped(final DragEvent event, final ListView<Fruit> listView) {
        boolean dragCompleted = false;

        // Transfer the data to the target
        final Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(FRUIT_LIST)) {
            final ArrayList<Fruit> list = (ArrayList<Fruit>) dragboard.getContent(FRUIT_LIST);
            listView.getItems().addAll(list);
            // Data transfer is successful
            dragCompleted = true;
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    private void dragDone(final DragEvent event, final ListView<Fruit> listView) {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        final TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE) {
            removeSelectedFruits(listView);
        }

        event.consume();
    }

    private ArrayList<Fruit> getSelectedFruits(final ListView<Fruit> listView) {
        // Return the list of selected Fruit in an ArratyList, so it is
        // serializable and can be stored in a Dragboard.
        final ArrayList<Fruit> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        return list;
    }

    private void removeSelectedFruits(final ListView<Fruit> listView) {
        // Get all selected Fruits in a separate list to avoid the shared list issue
        final List<Fruit> selectedList = new ArrayList<>();

        for (final Fruit fruit : listView.getSelectionModel().getSelectedItems()) {
            selectedList.add(fruit);
        }

        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().removeAll(selectedList);
    }

    // Helper Method for Logging
    private void writelog(final String text) {
        loggingArea.appendText(text + "\n");
    }
}
