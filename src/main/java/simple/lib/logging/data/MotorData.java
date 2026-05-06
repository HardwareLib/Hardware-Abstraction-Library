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
    public BooleanEntry connected = new BooleanEntry(false);
    public BooleanEntry alive = new BooleanEntry(false);
    public UnitEntry<CurrentUnit> supplyCurrent = new UnitEntry<>(Amps.zero());
    public UnitEntry<CurrentUnit> statorCurrent = new UnitEntry<>(Amps.zero());
    public UnitEntry<CurrentUnit> torqueCurrent = new UnitEntry<>(Amps.zero());

    public UnitEntry<TemperatureUnit> temperature = new UnitEntry<>(Celsius.zero());

    public UnitEntry<VoltageUnit> voltage = new UnitEntry<>(Volts.zero());
    public UnitEntry<AngleUnit> position = new UnitEntry<>(Rotations.zero());
    public UnitEntry<AngularVelocityUnit> velocity = new UnitEntry<>(RotationsPerSecond.zero());
    public UnitEntry<AngularAccelerationUnit> acceleration = new UnitEntry<>(RotationsPerSecondPerSecond.zero());
    public DoubleEntry output = new DoubleEntry(0.0);

    @Override
    public Map<String, Entry> getData() {
        return Map.of(
                "Supply Current", supplyCurrent,
                "Stator Current", statorCurrent,
                "Torque Current", torqueCurrent,
                "Temperature", temperature,
                "Voltage", voltage,
                "Position", position,
                "Velocity", velocity,
                "Acceleration", acceleration,
                "Output", output
        );
    }
}
