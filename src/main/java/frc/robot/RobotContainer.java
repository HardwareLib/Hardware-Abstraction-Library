// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.littletonrobotics.junction.AutoLogOutput;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ExampleSubsystem;
import simple.lib.mechanism.swerve.SwerveDrive;
import simple.lib.mechanism.swerve.util.SwerveDriveConfig;
import simple.lib.motor.Motor.MotorController;

public class RobotContainer {
  ExampleSubsystem subsystem;
  SwerveDrive drive;
  CommandXboxController controller = new CommandXboxController(0);
  public RobotContainer() {
    subsystem = new ExampleSubsystem();
    SwerveDriveConfig config = new SwerveDriveConfig();
    config.driveGearing = 4.94;
    config.steerGearing = 25.9;
    config.driveController = MotorController.Sim;
    config.steerController = MotorController.Sim;
    config.driveConfig.PID_Config.slot0.kP = 5.0;
    config.steerConfig.PID_Config.slot0.kP = 8.0;
    config.trackWidth = Inches.of(24);
    config.trackLength = Inches.of(24);
    config.setModulePositions();
    drive = new SwerveDrive(config);
    configureBindings();
  }

  @AutoLogOutput(key = "Drive/Pose")
  public Pose2d getRobotPose() {
    return drive.getPose();
  }

  @AutoLogOutput(key = "Drive/Actual States")
  public SwerveModuleState[] getSwerveStates() {
    return drive.getStates();
  }

  @AutoLogOutput(key = "Drive/Intended States")
  public SwerveModuleState[] getIntendedStates() {
    return drive.getIntendedStates();
  }
 
  private void configureBindings() {
    drive.setDefaultCommand(drive.drive(() -> {
      return new ChassisSpeeds(5.0 * controller.getLeftX(), 5.0 * controller.getLeftY(), 5.0 * controller.getRightX());
    }, controller.x()));
    controller.leftTrigger().whileTrue(subsystem.setVelocity(RadiansPerSecond.of(-5)));
    controller.rightTrigger().whileTrue(subsystem.setVelocity(RadiansPerSecond.of(5)));
    controller.a().onTrue(subsystem.setPosition(Radians.of(-5)));
    controller.b().onTrue(subsystem.setPosition(Radians.of(5)));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}