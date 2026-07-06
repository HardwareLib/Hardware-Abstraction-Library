package simple.lib.logging.data;

import edu.wpi.first.units.*;
import simple.lib.logging.entry.BooleanEntry;
import simple.lib.logging.entry.DoubleEntry;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.UnitEntry;

import java.util.Map;

import static edu.wpi.first.units.Units.*;

@SuppressWarnings("rawtypes")
public class MotorData extends BaseData {
    public final BooleanEntry connected = new BooleanEntry(false);
    public final BooleanEntry alive = new BooleanEntry(false);
    public final UnitEntry<CurrentUnit> supplyCurrent = new UnitEntry<>(Amps.zero());
    public final UnitEntry<CurrentUnit> statorCurrent = new UnitEntry<>(Amps.zero());
    public final UnitEntry<CurrentUnit> torqueCurrent = new UnitEntry<>(Amps.zero());

    public final UnitEntry<TemperatureUnit> temperature = new UnitEntry<>(Celsius.zero());

    public final UnitEntry<VoltageUnit> voltage = new UnitEntry<>(Volts.zero());
    public final UnitEntry<AngleUnit> position = new UnitEntry<>(Rotations.zero());
    public final UnitEntry<AngularVelocityUnit> velocity = new UnitEntry<>(RotationsPerSecond.zero());
    public final UnitEntry<AngularAccelerationUnit> acceleration = new UnitEntry<>(RotationsPerSecondPerSecond.zero());
    public final DoubleEntry output = new DoubleEntry(0.0);

    public double[] positionQueue; // DO NOT USE THIS IN LOGGING 
    public double[] timestampQueue;

    @Override
    public Map<String, Entry> getData(String prefix) {
        return Map.ofEntries(
                Map.entry(prefix+"Connected", connected),
                Map.entry(prefix+"Supply Current", supplyCurrent),
                Map.entry(prefix+"Stator Current", statorCurrent),
                Map.entry(prefix+"Torque Current", torqueCurrent),
                Map.entry(prefix+"Temperature", temperature),
                Map.entry(prefix+"Voltage", voltage),
                Map.entry(prefix+"Position", position),
                Map.entry(prefix+"Velocity", velocity),
                Map.entry(prefix+"Acceleration", acceleration),
                Map.entry(prefix+"Output", output),
                Map.entry(prefix+"Alive", alive)
        );
    }
}
