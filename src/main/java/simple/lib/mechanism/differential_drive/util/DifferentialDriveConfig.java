package simple.lib.mechanism.differential_drive.util;

import simple.lib.motor.util.MotorConfig;

public class DifferentialDriveConfig {

    public final MotorConfig motorConfig = new MotorConfig();

    public int frontLeftId = 0;
    public int frontRightId = 0;
    public int backLeftId = 0;
    public int backRightId = 0;

    public boolean invertRearMotors = false;

    public double gearing = 1.0;
}
