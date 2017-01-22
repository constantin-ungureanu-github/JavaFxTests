package org.clock;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TronClockDemo extends Application {

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        final Group root = new Group();
        final Scene scene = new Scene(root, 650, 220, Color.rgb(0, 0, 0));

        // create a canvas node
        final Canvas canvas = new Canvas();

        // bind the dimensions when the user resizes the window.
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        // obtain the GraphicsContext (drawing surface)
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // create three clocks
        final ArcClock blueClock = new ArcClock(20, ArcClock.BLUE1, ArcClock.BLUE2, 200);
        final ArcClock greenClock = new ArcClock(20, ArcClock.BLUE1, ArcClock.GREEN1, 200);
        final ArcClock redClock = new ArcClock(20, ArcClock.BLUE1, ArcClock.RED1, 200);

        // create an animation (update & render loop)
        new AnimationTimer() {
            @Override
            public void handle(final long now) {
                // update clocks
                blueClock.update(now);
                greenClock.update(now);
                redClock.update(now);

                // clear screen
                gc.clearRect(0, 0, primaryStage.getWidth(), primaryStage.getHeight());

                // draw blue clock
                blueClock.draw(gc);
                // save the origin or the current state
                // of the Graphics Context.
                gc.save();

                // shift x coord position the width of a clock plus 20 pixels
                gc.translate(blueClock.maxDiameter + 20, 0);
                greenClock.draw(gc);

                // shift x coord position past the first clock
                gc.translate(blueClock.maxDiameter + 20, 0);
                redClock.draw(gc);

                // reset Graphics Context to last saved point.
                // Translate x, y to (0,0)
                gc.restore();

            }
        }.start();

        // add the single node onto the scene graph
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
