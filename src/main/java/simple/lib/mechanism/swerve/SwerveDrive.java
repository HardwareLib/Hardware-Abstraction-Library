package simple.lib.mechanism.swerve;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.gyro.Gyro;
import simple.lib.logging.data.SwerveDriveData;
import simple.lib.mechanism.swerve.util.SwerveDriveConfig;
import simple.lib.motor.Motor.MotorController;
import simple.lib.motor.util.MotorConfig;

public class SwerveDrive extends SubsystemBase {
    private SwerveDriveConfig config;
    private SwerveModule[] modules = new SwerveModule[4];
    private SwerveDriveKinematics kinematics;
    private SwerveDrivePoseEstimator poseEstimator;
    private Gyro gyro;
    private Rotation2d internalEstimatedHeading = Rotation2d.kZero;
    private SwerveModulePosition[] previousPositions;
    private SwerveModulePosition[] deltas;
    private SwerveModuleState[] intendedStates;
    private SwerveDriveData data = new SwerveDriveData();

    public SwerveDrive(SwerveDriveConfig config) {
        this.config = config;
        for (int i=0; i<4; i++) {
            modules[i] = new SwerveModule(
                switch (i) {
                    case 0 -> config.frontLeftConfig;
                    case 1 -> config.frontRightConfig;
                    case 2 -> config.backLeftConfig;
                    case 3 -> config.backRightConfig;
                    default -> throw new IllegalStateException("Unexpected Swerve Module Index: " + i);
                }, config);
        }
        if (config.gyroType != null) {
            gyro = new Gyro(config.gyroId, config.gyroType,config.gyroConfig);
        }
        kinematics = new SwerveDriveKinematics(config.getModuleTranslations());
        poseEstimator = new SwerveDrivePoseEstimator(kinematics,(gyro != null) ? gyro.getHeading() : Rotation2d.kZero, new SwerveModulePosition[]{new SwerveModulePosition(),new SwerveModulePosition(),new SwerveModulePosition(),new SwerveModulePosition()},Pose2d.kZero);
        previousPositions = new SwerveModulePosition[modules.length];
        deltas = new SwerveModulePosition[modules.length];
        for (int i=0; i<modules.length; i++) {
            previousPositions[i] = modules[i].getPosition();
        }
    }

    @Override
    public void periodic() {
        getData();
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
        for (int i=0; i<positions.length; i++) {
            positions[i] = modules[i].getPosition();
            deltas[i] = new SwerveModulePosition(positions[i].distanceMeters - previousPositions[i].distanceMeters, positions[i].angle);
            previousPositions[i] = positions[i];
        }
        internalEstimatedHeading = internalEstimatedHeading.plus(Rotation2d.fromRadians(kinematics.toTwist2d(deltas).dtheta));
        poseEstimator.update(config.gyroType != null ? gyro.getHeading() : internalEstimatedHeading, positions);
    }

    public SwerveDrive(MotorController driveMotor, MotorController steerMotor, MotorConfig driveConfig, MotorConfig steerConfig, SwerveDriveConfig swerveConfig) {}
    public void driveSpeeds(ChassisSpeeds speeds, boolean robotRelative) {
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, robotRelative ? Rotation2d.kZero : getHeading()));
        for (int i=0; i<states.length; i++) {
            states[i].optimize(modules[i].getModuleHeading());
            states[i].cosineScale(modules[i].getModuleHeading());
            modules[i].runState(states[i]);
        }
        intendedStates = states;
    }

    public void xPattern() {
        for (int i=0; i< modules.length; i++) {
            modules[i].runState(new SwerveModuleState(0.0, Rotation2d.fromDegrees(45).plus(Rotation2d.kCW_90deg.times(i))));
        }
    }

    public Command drive(Supplier<ChassisSpeeds> speeds, BooleanSupplier robotRelative) {
        return this.run(() -> {
            driveSpeeds(speeds.get(), robotRelative.getAsBoolean());
        });
    }

    public Command drive(Supplier<ChassisSpeeds> speeds, BooleanSupplier robotRelative, double stopThreshold) {
        return this.run(() -> {
            ChassisSpeeds chassisSpeeds = speeds.get();
            if (Math.hypot(chassisSpeeds.vxMetersPerSecond,chassisSpeeds.vyMetersPerSecond) <= stopThreshold && chassisSpeeds.omegaRadiansPerSecond <= stopThreshold) {
                xPattern();
            } else {
                driveSpeeds(chassisSpeeds, robotRelative.getAsBoolean());
            }
        });
    }

    public SwerveModuleState[] getStates() {
        SwerveModuleState[] states = new SwerveModuleState[modules.length];
        for (int i=0; i<modules.length; i++) {
            states[i] = modules[i].getState();
        }
        return states;
    }

    public SwerveModuleState[] getIntendedStates() {
        return intendedStates;
    }

    public Rotation2d getHeading() {
        if (config.gyroType != null) {
            return gyro.getHeading();
        }
        return internalEstimatedHeading;
    }

    public Pose2d getPose() {
        return poseEstimator.getEstimatedPosition();
    }

    /** Gets the 2d pose with a 3d gyro position. Will hopefully update this to use an actual 3d pose estimator soon. */
    public Pose3d getPose3d() {
        if (config.gyroType != null) {
            return new Pose3d(new Pose3d(poseEstimator.getEstimatedPosition()).getTranslation(),gyro.getOrientation());
        }
        return new Pose3d(poseEstimator.getEstimatedPosition());
    }

    public SwerveDriveData getData() {
        for (int i=0; i<modules.length; i++) {
            modules[i].updateData(data.modules[i]);
        }
        data.gyro = gyro.getData();
        data.pose.update(getPose());
        data.measuredStates.update(getStates());
        data.targetStates.update(getIntendedStates());
        data.positions.update(previousPositions);
        return data;
    }
}
