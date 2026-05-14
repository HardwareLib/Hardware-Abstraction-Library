package simple.lib.logging.data;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;
import simple.lib.logging.entry.BooleanEntry;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.UnitEntry;

import java.util.Map;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;

@SuppressWarnings("rawtypes")
public class EncoderData extends BaseData {
    public final BooleanEntry connected = new BooleanEntry(false);
    public final BooleanEntry alive = new BooleanEntry(false);
    public final UnitEntry<AngleUnit> position = new UnitEntry<>(Rotations.zero());
    public final UnitEntry<AngularVelocityUnit> velocity = new UnitEntry<>(RotationsPerSecond.zero());

    
    @Override
    public Map<String, Entry> getData(String prefix) {
        return Map.of(
                prefix+"Connected", connected,
                prefix+"Alive", alive,
                prefix+"Position", position,
                prefix+"Velocity", velocity
        );
    }
}
