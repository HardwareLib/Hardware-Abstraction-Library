// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.lib.mechanism.differential_drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.mechanism.differential_drive.util.DifferentialDriveConfig;
import simple.lib.motor.Motor;
import simple.lib.motor.Motor.MotorController;

// TODO: Complete this part of the library
public class AbstractDifferentialDrive extends SubsystemBase {
  /** Creates a new DifferentialDrive. */
  private final Motor frontLeft;
  private final Motor backLeft;
  private final Motor frontRight;
  private final Motor backRight;

  public AbstractDifferentialDrive(MotorController controller, DifferentialDriveConfig config) {
    frontLeft = new Motor(config.frontLeftId, config.motorConfig, controller);
    backLeft = new Motor(config.backLeftId, config.motorConfig, controller);
    frontRight = new Motor(config.frontRightId, config.motorConfig, controller);
    backRight = new Motor( config.backRightId, config.motorConfig, controller);

    backLeft.follow(frontLeft, config.invertRearMotors);
    backRight.follow(frontRight, config.invertRearMotors);
  }

  public Command driveVoltage(DoubleSupplier drive, DoubleSupplier steer, double maxVolts) {
    return this.run(() -> {});
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
