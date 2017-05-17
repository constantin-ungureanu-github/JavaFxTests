package plot.objects;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Candidate extends Circle {
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
