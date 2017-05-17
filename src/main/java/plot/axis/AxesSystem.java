package plot.axis;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.layout.Region;

public class AxesSystem<X extends Number, Y extends Number> extends Region {
    private final Axis<X> xAxis;
    private final Axis<Y> yAxis;

    public AxesSystem(final double width, final double height, final double xLow, final double xHi, final double yLow, final double yHi) {
        setPickOnBounds(false);

        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        xAxis = new Axis<>(xLow, xHi);

        xAxis.setSide(Side.BOTTOM);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height / 2);

        yAxis = new Axis<>(yLow, yHi);
        yAxis.setSide(Side.LEFT);
        yAxis.setPrefHeight(height);

        yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

        getChildren().setAll(xAxis, yAxis);
    }

    public Double getMinX() {
        return xAxis.getLowerBound();
    }

    public Double getMinY() {
        return yAxis.getLowerBound();
    }

    public Double getMaxX() {
        return xAxis.getUpperBound();
    }

    public Double getMaxY() {
        return xAxis.getUpperBound();
    }

    public double applyX(final double x) {
        final double tx = xAxis.getPrefWidth() / 2;
        final double sx = xAxis.getPrefWidth() / (xAxis.getUpperBound() - xAxis.getLowerBound());

        return (x * sx) + tx;
    }

    public double applyY(final double y) {
        final double ty = yAxis.getPrefHeight() / 2;
        final double sy = yAxis.getPrefHeight() / (yAxis.getUpperBound() - yAxis.getLowerBound());

        return (-y * sy) + ty;
    }

    public double invertX(final double x) {
        return 2 * (x - getWidth() / 2) / (getWidth() / xAxis.getUpperBound());
    }

    public double invertY(final double y) {
        return -1 * 2 * (y - getHeight() / 2) / (getHeight() / yAxis.getUpperBound());
    }
}
