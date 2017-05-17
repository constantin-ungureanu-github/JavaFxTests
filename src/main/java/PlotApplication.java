

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import plot.CanvasPane;
import plot.PlotPane;
import plot.ResizablePane;

public class PlotApplication extends Application {
    final static int DEFAULT_WIDTH = 800;
    final static int DEFAULT_HEIGHT = 600;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        final Pane wrappedPane = new CanvasPane();

        final ResizablePane overlayPane = new ResizablePane();

        final Pane plotPane = new PlotPane();

        final Pane stackPane = new StackPane();
        stackPane.getChildren().addAll(plotPane, overlayPane, wrappedPane);

        primaryStage.setScene(new Scene(stackPane, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.AZURE));
        primaryStage.show();

        overlayPane.addGrid();
    }
}
