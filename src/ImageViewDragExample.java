import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageViewDragExample extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final Image image = createImage();
        final DraggableImageView imageView = new DraggableImageView(image);

        final Pane root = new Pane(imageView);

        final Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Image createImage() {
        final WritableImage image = new WritableImage(50, 50);
        for (int y = 0 ; y < 50; y++) {
            for (int x = 0 ; x < 50 ; x++) {
                Color color ;
                if (((x > 20) && (x < 30)) || ((y > 20) && (y < 30))) {
                    color = Color.AZURE;
                } else {
                    color = Color.CORNFLOWERBLUE ;
                }
                image.getPixelWriter().setColor(x, y, color);
            }
        }
        return image ;
    }

    public static class DraggableImageView extends ImageView {
        private double mouseX ;
        private double mouseY ;
        public DraggableImageView(final Image image) {
            super(image);

            setOnMousePressed(event -> {
                mouseX = event.getSceneX() ;
                mouseY = event.getSceneY() ;
            });

            setOnMouseDragged(event -> {
               final double deltaX = event.getSceneX() - mouseX ;
               final double deltaY = event.getSceneY() - mouseY ;
               relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
               mouseX = event.getSceneX() ;
               mouseY = event.getSceneY() ;
            });
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
