// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.hardware;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFXS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import simple.lib.controls.Control;
import simple.lib.logging.data.MotorData;
import simple.lib.motor.util.MotorConfig;
import simple.phoenix6.utility.Phoenix6ConfigUtility;
import simple.phoenix6.utility.PhoenixUtil;
import simple.lib.motor.util.MotorInterface;

/** Add your docs here. */
public class TalonFXSInterface implements MotorInterface {
    private final TalonFXS talon;

    private final StatusSignal<Voltage> appliedVoltage;

    private final StatusSignal<Angle> position;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<AngularAcceleration> acceleration;

    private final StatusSignal<Current> supply;
    private final StatusSignal<Current> stator;
    private final StatusSignal<Current> torque;

    private final StatusSignal<Temperature> temperature;

    // Control Output
    private final DutyCycleOut controlledOutput = new DutyCycleOut(0.0);
    private final VoltageOut voltageOutput = new VoltageOut(Volts.zero());
    private final PositionVoltage positionControl = new PositionVoltage(Radians.zero());
    private final MotionMagicVoltage profiledPosition = new MotionMagicVoltage(Radians.zero());
    private final VelocityVoltage velocityControl = new VelocityVoltage(RadiansPerSecond.zero());
    private final MotionMagicVelocityVoltage profiledVelocity = new MotionMagicVelocityVoltage(RadiansPerSecond.zero());

    public TalonFXSInterface(int id, MotorConfig config) {
        talon = new TalonFXS(id, PhoenixUtil.getCAN(config.canbus));
        TalonFXSConfiguration motorConfig = Phoenix6ConfigUtility.getTalonFXSConfig(config);
        PhoenixUtil.tryUntilOkay(() -> talon.getConfigurator().apply(motorConfig), 5);

        position = talon.getPosition();
        velocity = talon.getVelocity();
        acceleration = talon.getAcceleration();

        appliedVoltage = talon.getMotorVoltage();

        supply = talon.getSupplyCurrent();
        stator = talon.getStatorCurrent();
        torque = talon.getTorqueCurrent();

        temperature = talon.getDeviceTemp();

        PhoenixUtil.tryUntilOkay(() -> StatusSignal.setUpdateFrequencyForAll(50, supply,stator,torque,appliedVoltage, temperature), 5);
        PhoenixUtil.tryUntilOkay(() -> StatusSignal.setUpdateFrequencyForAll(50, position,velocity,acceleration), 5);
        PhoenixUtil.tryUntilOkay(() -> talon.optimizeBusUtilization(), 5);
    }

    @Override
    public void getData(MotorData data) {
        StatusSignal.refreshAll(supply,stator,torque,appliedVoltage,temperature,position,velocity,acceleration);

        data.connected.update(talon.isConnected());
        data.alive.update(StatusSignal.isAllGood(supply,stator,torque,appliedVoltage,temperature,position,velocity,acceleration));
        data.output.update(talon.get());
        data.position.update(position.getValue());
        data.velocity.update(velocity.getValue());
        data.acceleration.update(acceleration.getValue());
        data.voltage.update(appliedVoltage.getValue());
        data.statorCurrent.update(stator.getValue());
        data.supplyCurrent.update(supply.getValue());
        data.torqueCurrent.update(torque.getValue());
        data.temperature.update(temperature.getValue());
    }

    @Override
    public void configure(MotorConfig config) {
        TalonFXSConfiguration motorConfig = Phoenix6ConfigUtility.getTalonFXSConfig(config);
        PhoenixUtil.tryUntilOkay(() -> talon.getConfigurator().apply(motorConfig), 5);
    }

    @Override
    public void setControl(Control control) {
        switch (control.type) {
            case DutyCycle:
                talon.setControl(controlledOutput.withOutput(MathUtil.clamp(control.output, -1.0, 1.0)));
                break;
            case Voltage:
                talon.setControl(voltageOutput.withOutput(control.output));
                break;
            case Position:
                talon.setControl(positionControl.withPosition(control.position));
                break;
            case PositionProfiled:
                talon.setControl(profiledPosition.withPosition(control.position));
                break;
            case Velocity:
                talon.setControl(velocityControl.withVelocity(control.velocity));
                break;
            case VelocityProfiled:
                talon.setControl(profiledVelocity.withVelocity(control.velocity));
                break;
            default:
                break;
        }
    }

    @Override
    public void setPosition(Angle newPosition) {
        talon.setPosition(newPosition);
    }

    @Override
    public void stop() {
        talon.stopMotor();
    }
}
