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
        final double ix = xAxis.getInvert().equals(false) ? 1 : -1;
        final double sx = xAxis.getLength() / xDomain.getInterval();

        return (ix * sx * xAxis.getScale() * x) + xAxis.getScale() * xAxis.getTranslate();
    }

    /**
     * Transform Y coordinates from real to pixels.
     *
     * @param y
     *            the y
     * @return the real coordinates
     */
    public double applyY(final double y) {
        final double iy = yAxis.getInvert().equals(false) ? 1 : -1;
        final double sy = yAxis.getLength() / yDomain.getInterval();

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
        final double sx = xAxis.getLength() / xDomain.getInterval();

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
        final double sy = yAxis.getLength() / yDomain.getInterval();

        return iy * ((y - yAxis.getScale() * yAxis.getTranslate()) / sy * yAxis.getScale());
    }
}
