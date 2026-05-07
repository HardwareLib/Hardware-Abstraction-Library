// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.utility;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.OutputConfig.OutputDirection;

/** Add your docs here. */
public class Phoenix6ConfigUtility {
    public static TalonFXConfiguration getTalonConfig(MotorConfig config) {
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        // configuration.ClosedLoopGeneral.ContinuousWrap = config.PID_Config.
        configuration.Feedback.SensorToMechanismRatio = config.outputConfig.sensorToMechanismRatio;
        configuration.MotorOutput.Inverted = config.outputConfig.outputDirection == OutputDirection.ClockWisePositive ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        configuration.Slot0.withKP(config.PID_Config.slot0.kP).withKI(config.PID_Config.slot0.kI).withKD(config.PID_Config.slot0.kD).withKS(config.PID_Config.slot0.kS).withKV(config.PID_Config.slot0.kV).withKA(config.PID_Config.slot0.kA).withKG(config.PID_Config.slot0.kG);
        configuration.Slot1.withKP(config.PID_Config.slot1.kP).withKI(config.PID_Config.slot1.kI).withKD(config.PID_Config.slot1.kD).withKS(config.PID_Config.slot1.kS).withKV(config.PID_Config.slot1.kV).withKA(config.PID_Config.slot1.kA).withKG(config.PID_Config.slot1.kG);
        configuration.Slot2.withKP(config.PID_Config.slot2.kP).withKI(config.PID_Config.slot2.kI).withKD(config.PID_Config.slot2.kD).withKS(config.PID_Config.slot2.kS).withKV(config.PID_Config.slot2.kV).withKA(config.PID_Config.slot2.kA).withKG(config.PID_Config.slot2.kG);
        return configuration;
    }
}
