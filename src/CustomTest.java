import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CustomTest extends Application {
    final static int DEFAULT_WIDTH = 800;
    final static int DEFAULT_HEIGHT = 600;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final ResizableCanvas canvas = new ResizableCanvas();
        final Pane wrappedPane = new CanvasPane(canvas);

        final ResizablePane overlayPane = new ResizablePane();
        final Pane stackPane = new StackPane();
        stackPane.getChildren().addAll(wrappedPane, overlayPane);

        primaryStage.setScene(new Scene(stackPane, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.AZURE));
        primaryStage.show();

        overlayPane.addGrid();
    }

    class CanvasPane extends Pane {
        public CanvasPane(final ResizableCanvas canvas) {
            getChildren().add(canvas);
            canvas.widthProperty().bind(widthProperty());
            canvas.heightProperty().bind(heightProperty());
        }
    }

    class ResizablePane extends Pane {
        double originalWidth, originalHeight;

        public ResizablePane() {
            setOnMousePressed(onMouseClicked);
            cursorProperty().set(Cursor.CROSSHAIR);
            setPickOnBounds(false);
        }

        public void addGrid() {
            final double width = getWidth();
            final double height = getHeight();

            final double offsetX = getWidth() / 10;
            final double offsetY = getHeight() / 10;

            for (double x = 0; x <= width; x += offsetX) {
                for (double y = 0; y <= height; y += offsetY) {
                    final Candidate candidate = new Candidate(this, x, y, 4);
                    getChildren().add(candidate);
                }
            }
        }

        private final EventHandler<MouseEvent> onMouseClicked = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            final Target target = new Target(this, event.getSceneX(), event.getSceneY(), 5);

            getChildren().add(target);
        };
    }

    class ResizableCanvas extends Canvas {
        DoubleProperty myScale = new SimpleDoubleProperty(1.0);

        public ResizableCanvas() {
            widthProperty().addListener(event -> draw());
            heightProperty().addListener(event -> draw());

            scaleXProperty().bind(myScale);
            scaleYProperty().bind(myScale);

            setPickOnBounds(false);
        }

        private void draw() {
            final double width = getWidth();
            final double height = getHeight();

            final GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);

            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);

            gc.setStroke(Color.RED);
            gc.strokeRoundRect(10, 10, width - 20, height - 20, 10, 10);
        }

        @Override
        public boolean isResizable() {
            return true;
        }
    }

    class Target extends Circle {
        Pane parent;
        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public Target(final Pane parent, final double x, final double y, final double width) {
            super(width);

            this.parent = parent;
            setStroke(Color.BLACK);
            setFill(Color.WHITE.deriveColor(1, 1, 1, 0.7));

            translateXProperty().bind(parent.widthProperty().multiply(x).divide(parent.getWidth()));
            translateYProperty().bind(parent.heightProperty().multiply(y).divide(parent.getHeight()));

            cursorProperty().set(Cursor.HAND);

            setOnMouseEntered(onMouseEnteredEventHandler);
            setOnMouseExited(onMouseExitedEventHandler);

            setOnMousePressed(onMousePressedEventHandler);
            setOnMouseDragged(onMouseDraggedEventHandler);
            setOnMouseReleased(onMouseReleasedEventHandler);
        }

        private final EventHandler<MouseEvent> onMouseEnteredEventHandler = event -> {
            setScaleX(2);
            setScaleY(2);
        };

        private final EventHandler<MouseEvent> onMouseExitedEventHandler = event -> {
            setScaleX(1);
            setScaleY(1);
        };

        private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            translateXProperty().unbind();
            translateYProperty().unbind();

            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();

            final Node node = ((Node) (event.getSource()));

            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();

            event.consume();
        };

        private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            final double offsetX = event.getSceneX() - orgSceneX;
            final double offsetY = event.getSceneY() - orgSceneY;

            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            final Node node = ((Node) (event.getSource()));

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);
        };

        private final EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            translateXProperty().bind(parent.widthProperty().multiply(event.getSceneX()).divide(parent.getWidth()));
            translateYProperty().bind(parent.heightProperty().multiply(event.getSceneY()).divide(parent.getHeight()));

            event.consume();
        };
    }

    class Candidate extends Circle {
        final Pane parent;

        public Candidate(final Pane parent, final double x, final double y, final double width) {
            super(width);

            this.parent = parent;
            setStroke(Color.BLACK);
            setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));

            translateXProperty().bind(parent.widthProperty().multiply(x).divide(parent.getWidth()));
            translateYProperty().bind(parent.heightProperty().multiply(y).divide(parent.getHeight()));

            setOnMouseEntered(onMouseEnteredEventHandler);
            setOnMouseExited(onMouseExitedEventHandler);
        }

        private final EventHandler<MouseEvent> onMouseEnteredEventHandler = event -> {
            setScaleX(2);
            setScaleY(2);
        };

        private final EventHandler<MouseEvent> onMouseExitedEventHandler = event -> {
            setScaleX(1);
            setScaleY(1);
        };
    }
}
