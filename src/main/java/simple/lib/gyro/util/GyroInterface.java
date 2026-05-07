package simple.lib.gyro.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;

public interface GyroInterface {
    public abstract void configure(GyroConfig config);
    public abstract Rotation2d getHeading();
    public default Rotation2d getHeadingRate() {return Rotation2d.kZero;}
    public abstract Rotation3d getOrientation();
    public default Rotation3d getAngularVelocity() {return Rotation3d.kZero;}
}
