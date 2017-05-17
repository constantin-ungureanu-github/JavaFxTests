package example;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class LetterBoxingDemo extends Application {
    private static final int MAX_HEIGHT = 400;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws IOException {
        final Pane root = createPane();

        final Scene scene = new Scene(new Group(root));
        stage.setScene(scene);
        stage.show();

        letterbox(scene, root);
        stage.setFullScreen(true);
    }

    private StackPane createPane() {
        final StackPane stack = new StackPane();
        final Pane content = new Pane();

        final ImageView imageView = new ImageView("panda.jpg");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);

        imageView.setFitHeight(MAX_HEIGHT);
        content.getChildren().add(imageView);

        stack.getChildren().add(content);
        stack.setStyle("-fx-background-color: midnightblue");

        return stack;
    }

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        final SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(final Scene scene, final double ratio, final double initHeight, final double initWidth, final Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(final ObservableValue<? extends Number> observableValue, final Number oldValue, final Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            final double scaleFactor = (newWidth / newHeight) > ratio ? newHeight / initHeight : newWidth / initWidth;

            if (scaleFactor >= 1) {
                final Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}
