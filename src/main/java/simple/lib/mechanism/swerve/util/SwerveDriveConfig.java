package simple.lib.mechanism.swerve.util;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import simple.lib.encoder.Encoder.EncoderType;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.gyro.Gyro.GyroType;
import simple.lib.gyro.util.GyroConfig;
import simple.lib.motor.Motor.MotorController;
import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.FeedbackConfig.FeedbackSource;
import simple.lib.motor.util.MotorConfig.OutputConfig.OutputDirection;

import static edu.wpi.first.units.Units.*;

public class SwerveDriveConfig {
    public static class ModuleConfig {
        public Translation2d moduleTranslation = Translation2d.kZero;

        /** The can id of the drive motor. */
        public int driveId = 0;
        /** The can id of the steer/azimuth motor. */
        public int steerId = 0;
        /** The can/pwm id of the encoder. leave this as -1 if no encoder is attached */
        public int encoderId = -1;

        /** The Encoder Source of the azimuth motor, this can be changed to work with a fusion of a PWM encoder or CanCoder if you are using a TalonFX or FXS for your azimuth motor. This can be left as is if not using an external encoder */
        public FeedbackSource encoderSource = FeedbackSource.InternalEncoder;

        /** The motor controller of the drive motor. This doesn't have to be set because this value is an override of the global variable*/
        public MotorController driveController = null;
        /** The motor controller of the steer/azimuth motor. This doesn't have to be set because this value is an override of the global variable*/
        public MotorController steerController = null;
        /** The type of encoder used on the module. This doesn't have to be set because this value is an override of the global variable, and you may not use an encoder*/
        public EncoderType encoderType = null;
        /**
         * This is an override for steer gearing. DO NOT USE THIS UNLESS YOU HAVE A DIFFERENT MODULE OR GEAR RATIO USED FROM THE OTHER MODULES */
        public double steerGearing = -1.0; // Override for Steer Gearing
        /**
         * This is an override for steer gearing. DO NOT USE THIS UNLESS YOU HAVE A DIFFERENT MODULE OR GEAR RATIO USED FROM THE OTHER MODULES */
        public double driveGearing = -1.0; // Override for Drive Gearing

        /**
         * This is an override for the default wheel diameter setting. DO NOT USE THIS UNLESS YOU HAVE A DIFFERENT MODULE OR WHEEL DIAMETER FROM THE OTHER MODULES */
        public Distance wheelDiameter = Inches.of(4);

        /** The offset of the encoder from zero. You do not have to use this if you aren't use an encoder*/
        public Angle encoderOffset = Radians.zero();

        public OutputDirection steerDirection = OutputDirection.ClockWisePositive;
        public OutputDirection driveDirection = OutputDirection.ClockWisePositive;
        public boolean encoderInverted = false;
    }

    /** The gear ratio of the steer/azimuth motor of the module to the sensor */ 
    public double steerGearing = 1.0; // Global Steer Gearing
    /** The gear ratio of the drive motor of the module */
    public double driveGearing = 1.0; // Global Drive Gearing
    /** The Wheel Radius of the Swerve Module*/
    public Distance wheelDiameter = Inches.of(4);

    /** The CAN ID of the Gyro if it is connected with CAN*/
    public int gyroId = -1;

    /** The motor controller of the drive motor. This can be overridden in the individual module configs. This must be set to reflect the hardware used on the robot*/
    public MotorController driveController = null;
    /** The motor controller of the steer/azimuth motor. This can be overridden in the individual module configs. This must be set to reflect the hardware used on the robot*/
    public MotorController steerController = null;
    /** The type of encoder used for all the modules on the drivetrain. This can be overridden in the individual module configs. This doesn't have to be set if you don't use an encoder or absolute encoder however if you do not have an absolute encoder, you must always straighten your wheels before running*/
    public EncoderType encoderType = null;
    /** The type of Gyro in the swerve drive. This doesn't have to be set if you don't use a gyro, but it is highly recommend you use a gyro */
    public GyroType gyroType = null;

    /** The configuration of the gyro for the drivetrain. This doesn't have to be changed if you don't use a gyro.*/
    public GyroConfig gyroConfig = new GyroConfig();
    /** The configuration of the drive motors for all the modules*/
    public MotorConfig driveConfig = new MotorConfig();
    /** The configuration of the steer motors for all the modules*/
    public MotorConfig steerConfig = new MotorConfig();
    /** The configuration of the encoders for all the modules. This doesn't have to be changed if you don't use encoders.*/
    public EncoderConfig encoderConfig = new EncoderConfig();

    public Distance trackWidth = Inches.of(19);
    public Distance trackLength = Inches.of(19);

    /** The configuration of the front left module */
    public ModuleConfig frontLeftConfig = new ModuleConfig();
    /** The configuration of the front right module */
    public ModuleConfig frontRightConfig = new ModuleConfig();
    /** The configuration of the back left module */
    public ModuleConfig backLeftConfig = new ModuleConfig();
    /** The configuration of the back right module */
    public ModuleConfig backRightConfig = new ModuleConfig();

    public void setModulePositions() {
        setModulePositions(this.trackWidth,this.trackLength);
    }

    public void setModulePositions(Distance trackWidth, Distance trackLength) {
        for (int i=0; i<4; i++) {
            switch (i) {
                case 0:
                    frontLeftConfig.moduleTranslation = new Translation2d(trackLength.in(Meters)/2,trackWidth.in(Meters)/2);
                case 1:
                    frontRightConfig.moduleTranslation = new Translation2d(trackLength.in(Meters)/2,-trackWidth.in(Meters)/2);
                case 2:
                    backLeftConfig.moduleTranslation = new Translation2d(-trackLength.in(Meters)/2,trackWidth.in(Meters)/2);
                case 3:
                    backRightConfig.moduleTranslation = new Translation2d(-trackLength.in(Meters)/2,-trackWidth.in(Meters)/2);
                default:
            }
        }
    }

    public Translation2d[] getModuleTranslations() {
        Translation2d[] translations = new Translation2d[4];
        for (int i=0; i<4; i++) {
            translations[i] = switch (i) {
                case 0 -> frontLeftConfig.moduleTranslation;
                case 1 -> frontRightConfig.moduleTranslation;
                case 2 -> backLeftConfig.moduleTranslation;
                case 3 -> backRightConfig.moduleTranslation;
                default -> Translation2d.kZero;
            };
        }
        return translations;
    }

    public enum COTSModule {
        /**An enum representation of the X2S family of modules */
        WCP_X2S(Inches.of(2)),
        /**An enum representation of the corner mount version of the X2 family of modules */
        WCP_X2_Corner_Mount(Inches.of(2.5)),
        /**An enum representation of the tube mount version of the X2 family of modules */
        WCP_X2_Tube_Mount(Inches.of(3.250000)),
        /**An enum representation of the Mk4 module */
        SDS_MK4(Inches.of(3)),
        /**An enum representation of the Mk4i module */
        SDS_MK4I(Inches.of(2.625)),
        /**An enum representation of the Mk4n module */
        SDS_MK4N(Inches.of(2.625)),
        /**An enum representation of the Mk4c module */
        SDS_MK4C(Inches.of(2.625)),
        /**An enum representation of the Mk5n module */
        SDS_MK5N(Inches.of(2.625)),
        /**An enum representation of the Mk5i module */
        SDS_MK5I(Inches.of(2.625)),
        /**An enum representation of the ThriftyBot Narrow Swerve Module */
        THRIFTY_SWERVE_NARROW(Inches.of(2.5)),
        /**An enum representation of the ThriftyBot Swerve Module */
        THRIFTY_SWERVE(Inches.of(2.5));

        public final Distance wheelOffset;

        private COTSModule(Distance edgeOfModuleToWheelCenter) {
            wheelOffset = edgeOfModuleToWheelCenter;
        }
    }
    /** */
    public static Distance convertFrameDimensionToTrack(Distance frameDimension, COTSModule module) {
        return frameDimension.minus(module.wheelOffset);
    }


    public enum WCPRatio {
        X1,
        X2,
        X3,
        X4
    }

    public static SwerveDriveConfig getWCPX2S(WCPRatio ratioSet, int pinionSize) {
        SwerveDriveConfig config = new SwerveDriveConfig();
        double driveRatio = 6;
        driveRatio = switch (ratioSet) {
            case X1 -> switch (pinionSize) {
                case 15 -> 6.0;
                case 16 -> 5.63;
                case 17 -> 5.29;
                default -> driveRatio;
            };
            case X2 -> switch (pinionSize) {
                case 17 -> 4.94;
                case 18 -> 4.67;
                case 19 -> 4.42;
                default -> driveRatio;
            };
            case X3 -> switch (pinionSize) {
                case 19 -> 4.11;
                case 20 -> 3.9;
                case 21 -> 3.71;
                default -> driveRatio;
            };
            default -> driveRatio;
        };
        config.steerGearing = 25.9;
        config.driveGearing = driveRatio;
        return config;
    }

    public static SwerveDriveConfig getWCPX2(WCPRatio ratioSet, int pinionSize) {
        SwerveDriveConfig config = new SwerveDriveConfig();
        double driveRatio = 6;
        driveRatio = switch (ratioSet) {
            case X1 -> switch (pinionSize) {
                case 10 -> 7.67;
                case 11 -> 6.98;
                case 12 -> 6.39;
                default -> driveRatio;
            };
            case X2 -> switch (pinionSize) {
                case 10 -> 6.82;
                case 11 -> 6.20;
                case 12 -> 5.68;
                default -> driveRatio;
            };
            case X3 -> switch (pinionSize) {
                case 10 -> 6.48;
                case 11 -> 5.89;
                case 12 -> 5.40;
                default -> driveRatio;
            };
            case X4 -> switch (pinionSize) {
                case 10 -> 5.67;
                case 11 -> 5.15;
                case 12 -> 5.45;
                default -> driveRatio;
            };
        };
        config.steerGearing = 25.9;
        config.driveGearing = driveRatio;
        return config;
    }
}
