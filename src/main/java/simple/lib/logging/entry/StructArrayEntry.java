package simple.lib.logging.entry;

import edu.wpi.first.util.struct.StructSerializable;

public class StructArrayEntry<T extends StructSerializable> implements Entry<T[]> {
    private T[] value;
    public StructArrayEntry(T[] initialValue) {
        this.value = initialValue;
    }
    /**
     * @param value the new value of the entry
     */
    @Override
    public void update(T[] value) {
        this.value = value;
    }

    /**
     * @return
     */
    @Override
    public T[] getValue() {
        return value;
    }
}
