import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DrawOnCanvas extends Application {
    final static int CANVAS_WIDTH = 400;
    final static int CANVAS_HEIGHT = 400;

    double x0, y0, x1, y1;
    Image image;

    @Override
    public void start(final Stage primaryStage) {

        final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    graphicsContext.lineTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                event -> {
                });
//        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
//            x0 = event.getX();
//            y0 = event.getY();
//        });
//
//        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
//        });
//
//        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
//            x1 = event.getX();
//            y1 = event.getY();
//
//            final double x = (x0 > x1) ? x1 : x0;
//            final double y = (y0 > y1) ? y1 : y0;
//            final double w = (x0 > x1) ? x0 - x1 : x1 - x0;
//            final double h = (y0 > y1) ? y0 - y1 : y1 - y0;
//
//            graphicsContext.drawImage(image, x, y, w, h);
//        });

        final Group root = new Group();
        final VBox vBox = new VBox();
        vBox.getChildren().addAll(canvas);
        root.getChildren().add(vBox);
        final Scene scene = new Scene(root, 400, 425);
        primaryStage.setTitle("java-buddy.blogspot.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    private void initDraw(final GraphicsContext graphicsContext) {
        final double canvasWidth = graphicsContext.getCanvas().getWidth();
        final double canvasHeight = graphicsContext.getCanvas().getHeight();

        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(5);

        graphicsContext.fill();
        graphicsContext.strokeRect(0, 0, canvasWidth, canvasHeight);

        graphicsContext.setLineWidth(1);
    }
}
