package plot.axis;

import plot.domain.Domain;

/**
 * The axes system.
 * Transformations between domain real values and screen pixels value.
 */
public class AxesSystem {
    private final Domain xDomain;
    private final Domain yDomain;
    private final Axis xAxis;
    private final Axis yAxis;

    public AxesSystem(final double xMin, final double xMax, final double yMin, final double yMax) {
        xAxis = new Axis();
        yAxis = new Axis();

        xDomain = new Domain(xMin, xMax);
        yDomain = new Domain(yMin, yMax);
    }

    public Axis getXAxis() {
        return xAxis;
    }

    public Axis getYAxis() {
        return yAxis;
    }

    public Axis getyAxis() {
        return yAxis;
    }

    public Domain getxDomain() {
        return xDomain;
    }

    /**
     * Transform X coordinates from real to pixels.
     *
     * @param x
     *            the x
     * @return the real coordinates
     */
    public double applyX(final double x) {
        final double s = xAxis.getScale();
        final double sx = s * xAxis.getLength() / xDomain.getInterval();
        final double t = xAxis.getTranslate();

        return sx * x + s * t;
    }

    /**
     * Transform Y coordinates from real to pixels.
     *
     * @param y
     *            the y
     * @return the real coordinates
     */
    public double applyY(final double y) {
        final double s = yAxis.getScale();
        final double sy =  s * yAxis.getLength() / yDomain.getInterval();
        final double t = yAxis.getTranslate();

        return sy * y + s * t;
    }

    /**
     * Transform coordinates from pixels to real.
     *
     * @param x
     *            the x
     * @return the pixels coordinates
     */
    public double invertX(final double x) {
        final double s = xAxis.getScale();
        final double sx = s * xAxis.getLength() / xDomain.getInterval();
        final double t = xAxis.getTranslate();

        return (x - s * t) / (sx);
    }

    /**
     * Transform coordinates from pixels to real.
     *
     * @param y
     *            the y
     * @return the pixels coordinates
     */
    public double invertY(final double y) {
        final double s = yAxis.getScale();
        final double sy = s * yAxis.getLength() / yDomain.getInterval();
        final double t = yAxis.getTranslate();

        return (y - s * t) / (sy);
    }
}
