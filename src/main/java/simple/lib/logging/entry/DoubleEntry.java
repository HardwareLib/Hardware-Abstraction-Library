package simple.lib.logging.entry;

public class DoubleEntry implements Entry<Double> {
    double value = 0.0;
    public DoubleEntry(double initialValue) {
        this.value = initialValue;
    }

    @Override
    public void update(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }
}
