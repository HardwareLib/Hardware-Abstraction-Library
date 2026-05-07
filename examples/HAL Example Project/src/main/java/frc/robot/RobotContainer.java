// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ExampleSubsystem;

public class RobotContainer {
  ExampleSubsystem subsystem;
  CommandXboxController controller = new CommandXboxController(0);
  public RobotContainer() {
    subsystem = new ExampleSubsystem();
    configureBindings();
  }

  private void configureBindings() {
    controller.leftTrigger().whileTrue(subsystem.setVelocity(RadiansPerSecond.of(-5)));
    controller.rightTrigger().whileTrue(subsystem.setVelocity(RadiansPerSecond.of(5)));
    controller.a().onTrue(subsystem.setPosition(Radians.of(5)));
    controller.b().onTrue(subsystem.setPosition(Radians.of(5)));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
