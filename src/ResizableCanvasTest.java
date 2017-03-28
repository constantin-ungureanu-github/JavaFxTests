import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResizableCanvasTest extends Application {
    class ResizableCanvas extends Canvas {

        public ResizableCanvas() {
            widthProperty().addListener(event -> draw());
            heightProperty().addListener(event -> draw());
        }

        private void draw() {
            final double width = getWidth();
            final double height = getHeight();

            final GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, width, height);

            graphicsContext.setStroke(Color.RED);
            graphicsContext.strokeLine(0, 0, width, height);
            graphicsContext.strokeLine(0, height, width, 0);
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(final double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(final double width) {
            return getHeight();
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final ResizableCanvas canvas = new ResizableCanvas();

        final StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);

        canvas.widthProperty().bind(stackPane.widthProperty());
        canvas.heightProperty().bind(stackPane.heightProperty());

        stage.setScene(new Scene(stackPane));
        stage.setTitle("Resizable Canvas");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
