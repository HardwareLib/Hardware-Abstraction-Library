// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.lib.mechanism.differential_drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.motor.Motor.MotorController;

public class AbstractDifferentialDrive extends SubsystemBase {
  /** Creates a new DifferentialDrive. */
  public AbstractDifferentialDrive(MotorController controller) {}

  public Command driveVoltage(DoubleSupplier drive, DoubleSupplier steer, double maxVolts) {
    return this.run(() -> {});
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
