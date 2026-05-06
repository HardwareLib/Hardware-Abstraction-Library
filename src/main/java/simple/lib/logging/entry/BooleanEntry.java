package simple.lib.logging.entry;

public class BooleanEntry implements Entry<Boolean> {
    boolean value = false;
    public BooleanEntry(boolean initial) {
        this.value = initial;
    }

    @Override
    public void update(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
