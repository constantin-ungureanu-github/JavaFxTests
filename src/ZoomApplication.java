import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ZoomApplication extends Application {
    static public class PannableCanvas extends Pane {
        DoubleProperty myScale = new SimpleDoubleProperty(1.0);

        public PannableCanvas() {
            setPrefSize(600, 600);
            setStyle("-fx-background-color: lightgrey; -fx-border-color: blue;");

            // add scale transform
            scaleXProperty().bind(myScale);
            scaleYProperty().bind(myScale);
        }

        public void addGrid() {
            final double w = getBoundsInLocal().getWidth();
            final double h = getBoundsInLocal().getHeight();

            // add grid
            final Canvas grid = new Canvas(w, h);

            // don't catch mouse events
            grid.setMouseTransparent(true);

            final GraphicsContext gc = grid.getGraphicsContext2D();

            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1);

            // draw grid lines
            final double offset = 50;
            for (double i = offset; i < w; i += offset) {
                // vertical
                gc.strokeLine(i, 0, i, h);
                // horizontal
                gc.strokeLine(0, i, w, i);
            }

            getChildren().add(grid);

            grid.toBack();
        }

        public double getScale() {
            return myScale.get();
        }

        public void setScale(final double scale) {
            myScale.set(scale);
        }

        public void setPivot(final double x, final double y) {
            setTranslateX(getTranslateX() - x);
            setTranslateY(getTranslateY() - y);
        }
    }

    /**
     * Mouse drag context used for scene and nodes.
     */
    class DragContext {
        double mouseAnchorX;
        double mouseAnchorY;

        double translateAnchorX;
        double translateAnchorY;
    }

    class NodeGestures {

        private final DragContext nodeDragContext = new DragContext();

        PannableCanvas canvas;

        public NodeGestures(final PannableCanvas canvas) {
            this.canvas = canvas;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            nodeDragContext.mouseAnchorX = event.getSceneX();
            nodeDragContext.mouseAnchorY = event.getSceneY();

            final Node node = (Node) event.getSource();

            nodeDragContext.translateAnchorX = node.getTranslateX();
            nodeDragContext.translateAnchorY = node.getTranslateY();
        };

        private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            final double scale = canvas.getScale();

            final Node node = (Node) event.getSource();

            node.setTranslateX(nodeDragContext.translateAnchorX + ((event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
            node.setTranslateY(nodeDragContext.translateAnchorY + ((event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

            event.consume();

        };
    }

    class SceneGestures {
        private static final double MAX_SCALE = 10.0d;
        private static final double MIN_SCALE = .1d;

        private final DragContext sceneDragContext = new DragContext();

        PannableCanvas canvas;

        public SceneGestures(final PannableCanvas canvas) {
            this.canvas = canvas;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        public EventHandler<ScrollEvent> getOnScrollEventHandler() {
            return onScrollEventHandler;
        }

        private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

            // right mouse button => panning
            if (!event.isSecondaryButtonDown()) {
                return;
            }

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = canvas.getTranslateX();
            sceneDragContext.translateAnchorY = canvas.getTranslateY();

        };

        private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

            // right mouse button => panning
            if (!event.isSecondaryButtonDown()) {
                return;
            }

            canvas.setTranslateX((sceneDragContext.translateAnchorX + event.getSceneX()) - sceneDragContext.mouseAnchorX);
            canvas.setTranslateY((sceneDragContext.translateAnchorY + event.getSceneY()) - sceneDragContext.mouseAnchorY);

            event.consume();
        };

        /**
         * Mouse wheel handler: zoom to pivot point
         */
        private final EventHandler<ScrollEvent> onScrollEventHandler = event -> {

            double scale = canvas.getScale();
            final double oldScale = scale;

            scale *= Math.pow(1.01, event.getDeltaY());

            if (scale <= MIN_SCALE) {
                scale = MIN_SCALE;
            } else if (scale >= MAX_SCALE) {
                scale = MAX_SCALE;
            }

            final double f = (scale / oldScale) - 1;

            final double dx = (event.getSceneX() - ((canvas.getBoundsInParent().getWidth() / 2) + canvas.getBoundsInParent().getMinX()));
            final double dy = (event.getSceneY() - ((canvas.getBoundsInParent().getHeight() / 2) + canvas.getBoundsInParent().getMinY()));

            canvas.setScale(scale);
            canvas.setPivot(f * dx, f * dy);

            event.consume();

        };

    }

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final Group group = new Group();

        final PannableCanvas canvas = new PannableCanvas();

        canvas.setTranslateX(100);
        canvas.setTranslateY(100);

        // create sample nodes which can be dragged
        final NodeGestures nodeGestures = new NodeGestures(canvas);

        final Label label1 = new Label("Draggable node 1");
        label1.setTranslateX(10);
        label1.setTranslateY(10);
        label1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        final Label label2 = new Label("Draggable node 2");
        label2.setTranslateX(100);
        label2.setTranslateY(100);
        label2.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label2.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        final Label label3 = new Label("Draggable node 3");
        label3.setTranslateX(200);
        label3.setTranslateY(200);
        label3.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label3.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        final Circle circle1 = new Circle(300, 300, 50);
        circle1.setStroke(Color.ORANGE);
        circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
        circle1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        circle1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        final Rectangle rect1 = new Rectangle(100, 100);
        rect1.setTranslateX(450);
        rect1.setTranslateY(450);
        rect1.setStroke(Color.BLUE);
        rect1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        rect1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        rect1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        canvas.getChildren().addAll(label1, label2, label3, circle1, rect1);

        group.getChildren().add(canvas);

        // create scene which can be dragged and zoomed
        final Scene scene = new Scene(group, 1024, 768);

        final SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();

        canvas.addGrid();
    }
}
