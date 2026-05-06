package simple.lib.logging.entry;

import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Unit;

public class UnitEntry<U extends Unit> implements Entry<Measure<U>> {
    Measure<U> value;
    public UnitEntry(Measure<U> initialValue) {
        this.value = initialValue;
    }
    @Override
    public void update(Measure<U> value) {
        this.value = value;
    }

    @Override
    public Measure<U> getValue() {
        return value;
    }
}
