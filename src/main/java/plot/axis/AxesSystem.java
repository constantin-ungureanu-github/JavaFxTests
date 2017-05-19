package plot.axis;

/**
 * The axes system.
 * Provides the transformations.
 *
 * @param <X>
 *            the generic type
 * @param <Y>
 *            the generic type
 */
public class AxesSystem<X extends Number, Y extends Number> {
    private final Axis<X> xAxis;
    private final Axis<Y> yAxis;

    /**
     * Instantiates a new axes system.
     *
     * @param xMinimum
     *            the x minimum
     * @param xMaximum
     *            the x maximum
     * @param yMinimum
     *            the y minimum
     * @param yMaximum
     *            the y maximum
     */
    public AxesSystem(final double xMinimum, final double xMaximum, final double yMinimum, final double yMaximum) {
        xAxis = new Axis<>(xMinimum, xMaximum);
        yAxis = new Axis<>(yMinimum, yMaximum);
    }

    /**
     * Gets the X axis.
     *
     * @return the Y axis
     */
    public Axis<X> getXAxis() {
        return xAxis;
    }

    /**
     * Gets the Y axis.
     *
     * @return the Y axis
     */
    public Axis<Y> getYAxis() {
        return yAxis;
    }

    /**
     * Transform coordinates from real to pixels.
     *
     * @param x
     *            the x
     * @return the real coordinates
     */
    public double applyX(final double x) {
        final double ix = xAxis.getInvert().equals(false) ? 1 : -1;
        final double sx = xAxis.getLength() / (xAxis.getMaximum() - xAxis.getMinimum());

        return (ix * sx * xAxis.getScale() * x) + xAxis.getScale() * xAxis.getTranslate();
    }

    /**
     * Transform coordinates from real to pixels.
     * Apply translation, scaling and inversion.
     *
     * @param y
     *            the y
     * @return the real coordinates
     */
    public double applyY(final double y) {
        final double iy = yAxis.getInvert().equals(false) ? 1 : -1;
        final double sy = yAxis.getLength() / (yAxis.getMaximum() - yAxis.getMinimum());

        return (iy * sy * yAxis.getScale() * y) + yAxis.getScale() * yAxis.getTranslate();
    }

    /**
     * Transform coordinates from pixels to real.
     *
     * @param x
     *            the x
     * @return the pixels coordinates
     */
    public double invertX(final double x) {
        final double ix = xAxis.getInvert().equals(false) ? 1 : -1;
        final double sx = xAxis.getLength() / (xAxis.getMaximum() - xAxis.getMinimum());

        return ix * ((x - xAxis.getScale() * xAxis.getTranslate()) / sx * xAxis.getScale());
    }

    /**
     * Transform coordinates from pixels to real.
     *
     * @param y
     *            the y
     * @return the pixels coordinates
     */
    public double invertY(final double y) {
        final double iy = yAxis.getInvert().equals(false) ? 1 : -1;
        final double sy = yAxis.getLength() / (yAxis.getMaximum() - yAxis.getMinimum());

        return iy * ((y - yAxis.getScale() * yAxis.getTranslate()) / sy * yAxis.getScale());
    }
}
