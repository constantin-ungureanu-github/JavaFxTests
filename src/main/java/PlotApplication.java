

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import plot.LayeredPlot;

public class PlotApplication extends Application {
    final static int DEFAULT_WIDTH = 800;
    final static int DEFAULT_HEIGHT = 600;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final LayeredPlot plot = new LayeredPlot();
        primaryStage.setScene(new Scene(plot, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.AZURE));
        primaryStage.show();

        plot.addGrid();
    }
}
