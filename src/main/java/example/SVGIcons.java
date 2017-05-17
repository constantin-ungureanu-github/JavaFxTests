package example;

import java.util.function.IntFunction;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class SVGIcons extends Application {
    private static final int SIZE = 16;

    @Override
    public void start(final Stage stage) {
        final VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().add(createRow((size) -> lines(size)));
        root.getChildren().add(createRow((size) -> curve(size)));
        root.getChildren().add(createRow((size) -> arc(size)));

        final Scene scene = new Scene(root);
        stage.setTitle("SVGIcons");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createRow(final IntFunction<SVGPath> path) {
        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);
        for (int i = 2; i < 6; i++) {
            row.getChildren().add(path.apply(i * SIZE));
        }
        return row;
    }

    private SVGPath lines(final int size) {
        final SVGPath path = new SVGPath();
        path.setFill(Color.ALICEBLUE);
        path.setStroke(Color.BLUE);
        path.setContent("M0," + size + "L" + (size / 2) + ",0 " + size + "," + size + " " + (size / 2) + "," + ((2 * size) / 3) + "z");
        return path;
    }

    private SVGPath curve(final int size) {
        final SVGPath path = new SVGPath();
        path.setFill(Color.HONEYDEW);
        path.setStroke(Color.GREEN);
        path.setContent("M0,0Q" + size + ",0," + size + "," + size + "L0," + size + "z");
        return path;
    }

    private SVGPath arc(final int size) {
        final SVGPath path = new SVGPath();
        path.setFill(Color.MISTYROSE);
        path.setStroke(Color.RED);
        path.setContent("M0,0A" + (size / 2) + "," + size + ",0,1,0," + size + ",0z");
        return path;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
