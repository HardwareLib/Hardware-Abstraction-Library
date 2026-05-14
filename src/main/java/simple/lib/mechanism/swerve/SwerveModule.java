package simple.lib.mechanism.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import simple.lib.controls.PositionControl;
import simple.lib.controls.VelocityControl;
import simple.lib.encoder.Encoder;
import simple.lib.mechanism.swerve.util.SwerveDriveConfig;
import simple.lib.motor.Motor;
import simple.lib.motor.util.MotorConfig;

import static edu.wpi.first.units.Units.*;

public class SwerveModule {
    private Motor drive;
    private Motor steer;
    private Encoder encoder;

    private final double wheelRadius;

    private final PositionControl position = new PositionControl(Radians.zero());
    private final VelocityControl velocity = new VelocityControl(RadiansPerSecond.zero());

    public SwerveModule(SwerveDriveConfig.ModuleConfig config, SwerveDriveConfig swerveConfig) {
        MotorConfig steerConfig = swerveConfig.steerConfig;
        MotorConfig driveConfig = swerveConfig.driveConfig;
        if (config.encoderId != -1) {
            encoder = new Encoder(config.encoderId, (config.encoderType != null) ? config.encoderType : swerveConfig.encoderType,swerveConfig.encoderConfig);
            steerConfig.feedback.setFusedFeedbackSource(encoder);
        }
        steerConfig.feedback.continousWrap = true;
        steerConfig.feedback.sensorToMechanismRatio = swerveConfig.steerGearing;
        driveConfig.feedback.sensorToMechanismRatio = swerveConfig.driveGearing;

        steerConfig.outputConfig.outputDirection = config.steerDirection;
        driveConfig.outputConfig.outputDirection = config.driveDirection;

        drive = new Motor(config.driveId,swerveConfig.driveConfig,(config.driveController != null) ? config.driveController : swerveConfig.driveController);
        steer = new Motor(config.steerId,swerveConfig.steerConfig,(config.steerController != null) ? config.steerController : swerveConfig.steerController);
        wheelRadius = (config.wheelDiameter != null ? (config.wheelDiameter.in(Meters) / 2.0) : (swerveConfig.wheelDiameter.in(Meters) / 2.0));
    }

    public void runState(SwerveModuleState state) {
        steer.setControl(position.withPosition(state.angle.getMeasure()));
        drive.setControl(velocity.withVelocity(RadiansPerSecond.of(state.speedMetersPerSecond/wheelRadius)));
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(drive.getData().velocity.getValue().in(RadiansPerSecond) * (wheelRadius), Rotation2d.fromRadians(steer.getData().position.getValue().in(Radians)));
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(drive.getData().position.getValue().in(Radians) * (wheelRadius), Rotation2d.fromRadians(steer.getData().position.getValue().in(Radians)));
    }

    public Rotation2d getModuleHeading() {
        return Rotation2d.fromRadians(steer.getData().position.getValue().in(Radians));
    }
}
