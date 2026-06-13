// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.mechanism;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;

import edu.wpi.first.math.geometry.Translation2d;
import simple.lib.mechanism.swerve.SwerveDrive;
import simple.lib.mechanism.swerve.util.SwerveDriveConfig;
import simple.lib.motor.Motor.MotorController;
import simple.lib.motor.util.MotorConfig.FeedbackConfig.FeedbackSource;
import simple.lib.motor.util.MotorConfig.OutputConfig.OutputDirection;

public class TunerSwerve extends SwerveDrive {
  /** Creates a new TunerSwerve. */
  @SuppressWarnings("rawtypes")
  public TunerSwerve(SwerveModuleConstants frontLeft,
  SwerveModuleConstants frontRight,
  SwerveModuleConstants backLeft,
  SwerveModuleConstants backRight,
  SwerveDrivetrainConstants swerveConstants) {
    super(getSwerveConfig(frontLeft, frontRight, backLeft, backRight, swerveConstants));
  }

  @SuppressWarnings("rawtypes")
  public static final SwerveDriveConfig getSwerveConfig(SwerveModuleConstants frontLeft,
  SwerveModuleConstants frontRight,
  SwerveModuleConstants backLeft,
  SwerveModuleConstants backRight,
  SwerveDrivetrainConstants swerveConstants) {
    SwerveDriveConfig config = new SwerveDriveConfig();
    config.driveController = MotorController.TalonFX;
    config.steerController = MotorController.TalonFX;

    // Motor Controller Determination
    config.frontLeftConfig.driveController = switch(frontLeft.DriveMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.frontLeftConfig.steerController = switch(frontLeft.SteerMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.frontRightConfig.driveController = switch(frontRight.DriveMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.frontRightConfig.steerController = switch(frontRight.SteerMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.backLeftConfig.driveController = switch(backLeft.DriveMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.backLeftConfig.steerController = switch(backLeft.SteerMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.backRightConfig.driveController = switch(backRight.DriveMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };
    config.backRightConfig.steerController = switch(backRight.SteerMotorType) {
      case TalonFX_Integrated -> MotorController.TalonFX;
      default -> MotorController.TalonFXS;
    };

    config.frontLeftConfig.driveId = frontLeft.DriveMotorId;
    config.frontLeftConfig.encoderId = frontLeft.EncoderId;
    config.frontLeftConfig.steerId = frontLeft.SteerMotorId;

    config.frontRightConfig.driveId = frontRight.DriveMotorId;
    config.frontRightConfig.encoderId = frontRight.EncoderId;
    config.frontRightConfig.steerId = frontRight.SteerMotorId;
    
    config.backLeftConfig.driveId = backLeft.DriveMotorId;
    config.backLeftConfig.encoderId = backLeft.EncoderId;
    config.backLeftConfig.steerId = backLeft.SteerMotorId;
    
    config.backRightConfig.driveId = backRight.DriveMotorId;
    config.backRightConfig.encoderId = backRight.EncoderId;
    config.backRightConfig.steerId = backRight.SteerMotorId;

    config.frontLeftConfig.driveGearing = frontLeft.DriveMotorGearRatio;
    config.frontLeftConfig.driveDirection = frontLeft.DriveMotorInverted ? OutputDirection.ClockWisePositive : OutputDirection.CounterClockWisePositive;
    config.frontLeftConfig.steerGearing = frontLeft.SteerMotorGearRatio;

    config.frontRightConfig.driveGearing = frontRight.DriveMotorGearRatio;
    config.frontRightConfig.driveDirection = frontRight.DriveMotorInverted ? OutputDirection.ClockWisePositive : OutputDirection.CounterClockWisePositive;
    config.frontRightConfig.steerGearing = frontRight.SteerMotorGearRatio;

    config.backLeftConfig.driveGearing = frontLeft.DriveMotorGearRatio;
    config.backLeftConfig.driveDirection = frontLeft.DriveMotorInverted ? OutputDirection.ClockWisePositive : OutputDirection.CounterClockWisePositive;
    config.backLeftConfig.steerGearing = frontLeft.SteerMotorGearRatio;

    config.backRightConfig.driveGearing = frontRight.DriveMotorGearRatio;
    config.backRightConfig.driveDirection = frontRight.DriveMotorInverted ? OutputDirection.ClockWisePositive : OutputDirection.CounterClockWisePositive;
    config.backRightConfig.steerGearing = frontRight.SteerMotorGearRatio;

    // I haven't included support for CANDi as that isn't a priority because it has multiple inputs and isn't traditionally used by most teams/
    config.frontLeftConfig.encoderSource = FeedbackSource.CanEncoder;
    config.frontRightConfig.encoderSource = FeedbackSource.CanEncoder;
    config.backLeftConfig.encoderSource = FeedbackSource.CanEncoder;
    config.backRightConfig.encoderSource = FeedbackSource.CanEncoder;

    config.frontLeftConfig.encoderOffset = Rotations.of(frontLeft.EncoderOffset);
    config.frontRightConfig.encoderOffset = Rotations.of(frontRight.EncoderOffset);
    config.backLeftConfig.encoderOffset = Rotations.of(backLeft.EncoderOffset);
    config.backRightConfig.encoderOffset = Rotations.of(backRight.EncoderOffset);

    config.frontLeftConfig.moduleTranslation = new Translation2d(frontLeft.LocationX, frontRight.LocationY);
    config.frontRightConfig.moduleTranslation = new Translation2d(frontRight.LocationX, backRight.LocationY);
    config.backLeftConfig.moduleTranslation = new Translation2d(backLeft.LocationX, backLeft.LocationY);
    config.backRightConfig.moduleTranslation = new Translation2d(backRight.LocationX, backRight.LocationY);

    config.frontLeftConfig.wheelDiameter = Meters.of(frontLeft.WheelRadius*2);
    config.frontRightConfig.wheelDiameter = Meters.of(frontRight.WheelRadius*2);
    config.backLeftConfig.wheelDiameter = Meters.of(backLeft.WheelRadius*2);
    config.backRightConfig.wheelDiameter = Meters.of(backRight.WheelRadius*2);

    TalonFXConfiguration driveMotorConfig = (TalonFXConfiguration) frontLeft.DriveMotorInitialConfigs;
    driveMotorConfig.withSlot0(frontLeft.DriveMotorGains);
    driveMotorConfig.CurrentLimits.StatorCurrentLimit = frontLeft.SlipCurrent;
    driveMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    return config;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
