package simple.lib.logging.data;

import edu.wpi.first.units.*;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.logging.entry.Entry;

import java.util.Map;

import static edu.wpi.first.units.Units.*;

@SuppressWarnings("rawtypes")
public class BaseData {
    public Map<String, Entry> getData() {
        return Map.of();
    }

    protected static Unit getCurrent(Unit preferred) {
        // Check if it is a current unit
        if (preferred instanceof CurrentUnit) {
            return preferred;
        } else {
            // Otherwise use SI units
            return Amps.getBaseUnit();
        }
    }

    protected static Unit getVoltage(Unit preferred) {
        // Check if it is a current unit
        if (preferred instanceof VoltageUnit) {
            return preferred;
        } else {
            // Otherwise use SI units
            return Volts;
        }
    }

    protected static Unit getTemperature(Unit preferred) {
        if (preferred instanceof TemperatureUnit) {
            return preferred;
        } else {
            return Celsius;
        }
    }

    protected static Unit getDistance(Unit preferred) {
        if (preferred instanceof DistanceUnit) {
            return preferred;
        } else {
            return Meters;
        }
    }

    protected static Unit getLinearVelocity(Unit preferred) {
        if (preferred instanceof LinearVelocityUnit) {
            return preferred;
        } else {
            return MetersPerSecond;
        }
    }

    protected static Unit getLinearAcceleration(Unit preferred) {
        if (preferred instanceof LinearAccelerationUnit) {
            return preferred;
        } else {
            return MetersPerSecondPerSecond;
        }
    }

    protected static Unit getAngle(Unit preferred) {
        if (preferred instanceof AngleUnit) {
            return preferred;
        } else {
            return Rotations;
        }
    }

    protected static Unit getAngularVelocity(Unit preferred) {
        if (preferred instanceof AngularVelocity) {
            return preferred;
        } else {
            return RotationsPerSecond;
        }
    }

    protected static Unit getAngularAcceleration(Unit preferred) {
        if (preferred instanceof AngularAcceleration) {
            return preferred;
        } else {
            return RotationsPerSecondPerSecond;
        }
    }
}
