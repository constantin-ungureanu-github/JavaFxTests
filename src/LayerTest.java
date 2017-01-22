import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LayerTest extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Node layerA = createLayerA();
        final Node layerB = createLayerB();
        final Parent root = new StackPane(layerA, layerB);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(250);
        primaryStage.setHeight(200);
        primaryStage.show();
    }

    private Node createLayerA() {
        final AnchorPane layerA = new AnchorPane();
        final Button buttonA = new Button("Button A");
        AnchorPane.setLeftAnchor(buttonA, 10d);
        AnchorPane.setTopAnchor(buttonA, 10d);
        buttonA.setOnMouseClicked(e -> System.out.println("Button A clicked"));
        layerA.getChildren().setAll(buttonA);
        layerA.setPickOnBounds(false);
        return layerA;
    }

    private Node createLayerB() {
        final AnchorPane layerB = new AnchorPane();
        final Button buttonB = new Button("Button B");
        AnchorPane.setRightAnchor(buttonB, 10d);
        AnchorPane.setBottomAnchor(buttonB, 10d);
        buttonB.setOnMouseClicked(e -> System.out.println("Button B clicked"));
        layerB.getChildren().setAll(buttonB);
        layerB.setPickOnBounds(false);
        return layerB;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
