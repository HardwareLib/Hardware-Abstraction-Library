package simple.lib.encoder.util;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotations;

public class EncoderConfig {
    public Angle offset = Radians.zero();
    public double SensorToMechanismRatio = 1.0;
    public boolean inverted = false;
    /**
     * Only used for PWM encoders if you are not using PWM encoders you don't need to set this setting.
     */
    public AngleUnit defaultUnit = Rotations;

    /**
     * Only used for Quadrature encoders if you are not using Quadrature encoders you don't need to set this setting.
     */
    public int bPin = 0;
}
