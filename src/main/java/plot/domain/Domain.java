package plot.domain;

public class Domain {

    private final double min;

    private final double max;

    public Domain(final double min, final double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getInterval() {
        return max - min;
    }
}
