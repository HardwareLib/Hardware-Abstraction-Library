package simple.lib.logging.entry;

public class DoubleArrayEntry implements Entry<double[]> {
    double[] value = new double[] {};
    public DoubleArrayEntry(double[] initialValue) {
        this.value = initialValue;
    }

    @Override
    public void update(double[] value) {
        this.value = value;
    }

    @Override
    public double[] getValue() {
        return value;
    }
}
