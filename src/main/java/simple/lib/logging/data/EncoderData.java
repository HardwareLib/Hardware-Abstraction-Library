package simple.lib.logging.data;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;
import simple.lib.logging.entry.BooleanEntry;
import simple.lib.logging.entry.UnitEntry;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;

public class EncoderData extends BaseData {
    public BooleanEntry connected = new BooleanEntry(false);
    public BooleanEntry alive = new BooleanEntry(false);
    public UnitEntry<AngleUnit> position = new UnitEntry<>(Rotations.zero());
    public UnitEntry<AngularVelocityUnit> velocity = new UnitEntry<>(RotationsPerSecond.zero());
}
