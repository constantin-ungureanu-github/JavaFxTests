package org.test;

import javafx.application.Application;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CanvasTests extends Application {
    final static int DEFAULT_WIDTH = 800;
    final static int DEFAULT_HEIGHT = 600;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final Canvas canvas = new ResizableCanvas();
        final Pane stackPane = new StackPane();

        final Pane wrappedPane = new Pane();
        wrappedPane.getChildren().add(canvas);

        canvas.widthProperty().bind(wrappedPane.widthProperty());
        canvas.heightProperty().bind(wrappedPane.heightProperty());

        final ResizablePane overlayPane = new ResizablePane();

        stackPane.getChildren().addAll(wrappedPane, overlayPane);

        primaryStage.setScene(new Scene(stackPane, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        primaryStage.show();

        overlayPane.addGrid();
    }

    class SquareObject extends Rectangle {
        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public SquareObject(final double x, final double y, final double width) {
            super(width, width);
            setTranslateX(x);
            setTranslateY(y);

            cursorProperty().set(Cursor.HAND);

            setOnMouseEntered(onMouseEnteredEventHandler);
            setOnMouseExited(onMouseExitedEventHandler);

            setOnMousePressed(onMousePressedEventHandler);
            setOnMouseDragged(onMouseDraggedEventHandler);
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
    }

    class RoundObject extends Circle {
        public RoundObject(final double x, final double y, final double width) {
            super(width);
            setTranslateX(x);
            setTranslateY(y);

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

    class ResizablePane extends Pane {
        double originalWidth, originalHeight;

        public ResizablePane() {
            widthProperty().addListener(event -> resize());
            heightProperty().addListener(event -> resize());

            setOnMousePressed(onMouseClicked);
        }

        public void addGrid() {
            final double width = getWidth();
            final double height = getHeight();

            final double offsetX = getWidth() / 10;
            final double offsetY = getHeight() / 10;

            for (double x = 0; x <= width; x += offsetX) {
                for (double y = 0; y <= height; y += offsetY) {
                    final RoundObject round = new RoundObject(x, y, 4);
                    round.setStroke(Color.GREEN);
                    round.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));

                    getChildren().add(round);
                }
            }
        }

        private final EventHandler<MouseEvent> onMouseClicked = event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }

            final SquareObject square = new SquareObject(event.getSceneX(), event.getSceneY(), 10);
            square.setStroke(Color.RED);
            square.setFill(Color.RED.deriveColor(1, 1, 1, 0.7));

            getChildren().add(square);
        };

        private void resize() {
            final double width = getWidth();
            final double height = getHeight();

            getChildren().forEach(node -> {
                node.setTranslateX((node.getTranslateX() * width) / originalWidth);
                node.setTranslateY((node.getTranslateY() * height) / originalHeight);
            });

            originalWidth = getWidth();
            originalHeight = getHeight();
        }
    }

    class ResizableCanvas extends Canvas {
        public ResizableCanvas() {
            widthProperty().addListener(event -> draw());
            heightProperty().addListener(event -> draw());
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
}
