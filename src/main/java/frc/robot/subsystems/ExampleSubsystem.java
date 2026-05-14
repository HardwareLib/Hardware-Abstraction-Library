// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.controls.PositionControl;
import simple.lib.motor.Motor;
import simple.lib.motor.Motor.MotorController;
import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.FeedbackConfig.FeedbackSource;
import simple.lib.motor.util.MotorConfig.PID.GravityCompensationType;

public class ExampleSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  Motor motor;
  PositionControl control = new PositionControl(Radians.zero());
  public ExampleSubsystem() {
    MotorConfig config = new MotorConfig();
    config.PID_Config.slot0.kP = 5.0;
    config.PID_Config.slot0.kD = 0.0;
    config.feedback.sensorToMechanismRatio = 5.0;
    config.feedback.feedbackSource = FeedbackSource.InternalEncoder;
    config.PID_Config.gravityCompensationType = GravityCompensationType.ELEVATOR_STATIC;
    motor = new Motor(0, config, MotorController.Sim, DCMotor.getKrakenX60Foc(1));
  }
  
  public Command setPosition(Angle position) {
    return this.runOnce(() -> {
      motor.setControl(control.withPosition(position));
    });
  }

  public Command setVelocity(AngularVelocity velocity) {
    return this.run(() -> {
      motor.setControl(control.withVelocity(velocity));
    }).finallyDo(() -> {
      motor.setControl(control.withVelocity(RadiansPerSecond.zero()));
    });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Example Position", motor.getData().position.getValue().in(Radians));
    SmartDashboard.putNumber("Example Velocity", motor.getData().velocity.getValue().in(RadiansPerSecond));
    SmartDashboard.putNumber("Target Position", motor.getActiveControl().position.in(Radians));
    SmartDashboard.putNumber("Target Velocity", motor.getActiveControl().velocity.in(RadiansPerSecond));
    SmartDashboard.putNumber("Example Voltage", motor.getData().voltage.getValue().in(Volts));
    SmartDashboard.putNumber("Motor kP", motor.getConfig().PID_Config.slot0.kP);
    SmartDashboard.putNumber("Motor kI", motor.getConfig().PID_Config.slot0.kI);
    SmartDashboard.putNumber("Motor kD", motor.getConfig().PID_Config.slot0.kD);
    SmartDashboard.putNumber("Motor kS", motor.getConfig().PID_Config.slot0.kS);
    SmartDashboard.putNumber("Motor kV", motor.getConfig().PID_Config.slot0.kV);
    SmartDashboard.putNumber("Motor kA", motor.getConfig().PID_Config.slot0.kA);
    SmartDashboard.putNumber("Motor kG", motor.getConfig().PID_Config.slot0.kG);
  }
}
