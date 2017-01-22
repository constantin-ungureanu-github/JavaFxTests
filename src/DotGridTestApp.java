import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Test app for DotGrid
 */
public class DotGridTestApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setScene(new Scene(new DotGrid(), Color.WHITE));
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    class DotGrid extends Pane {
        private static final double SPACING_X = 25;
        private static final double SPACING_Y = 20;
        private static final double RADIUS = 1.5;
        private final Canvas canvas = new Canvas();

        public DotGrid() {
            getChildren().add(canvas);
        }

        @Override
        protected void layoutChildren() {
            final int top = (int) snappedTopInset();
            final int right = (int) snappedRightInset();
            final int bottom = (int) snappedBottomInset();
            final int left = (int) snappedLeftInset();
            final int w = (int) getWidth() - left - right;
            final int h = (int) getHeight() - top - bottom;
            canvas.setLayoutX(left);
            canvas.setLayoutY(top);
            if ((w != canvas.getWidth()) || (h != canvas.getHeight())) {
                canvas.setWidth(w);
                canvas.setHeight(h);

                final GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, w, h);
                gc.setFill(Color.gray(0, 0.2));

                for (int x = 0; x < w; x += SPACING_X) {
                    for (int y = 0; y < h; y += SPACING_Y) {
                        final double offsetY = (y % (2 * SPACING_Y)) == 0 ? SPACING_X / 2 : 0;
                        gc.fillOval((x - RADIUS) + offsetY, y - RADIUS, RADIUS + RADIUS, RADIUS + RADIUS);
                    }
                }
            }
        }
    }
}