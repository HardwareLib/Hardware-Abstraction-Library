// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.utility;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.signals.ExternalFeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.OutputConfig.NeutralMode;
import simple.lib.motor.util.MotorConfig.OutputConfig.OutputDirection;

/** Add your docs here. */
public class Phoenix6ConfigUtility {
    public static TalonFXConfiguration getTalonFXConfig(MotorConfig config) {
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        
        configuration.ClosedLoopGeneral.ContinuousWrap = config.PID_Config.continousWrap;

        configuration.MotorOutput.NeutralMode = config.outputConfig.neutralMode == NeutralMode.Brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        configuration.MotorOutput.Inverted = config.outputConfig.outputDirection == OutputDirection.ClockWisePositive ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        configuration.Feedback.SensorToMechanismRatio = config.outputConfig.sensorToMechanismRatio;

        configuration.CurrentLimits.StatorCurrentLimit = config.currentLimits.maxStator;
        configuration.CurrentLimits.StatorCurrentLimitEnable = true;
        configuration.CurrentLimits.SupplyCurrentLimit = config.currentLimits.maxSupply;
        configuration.CurrentLimits.SupplyCurrentLimitEnable = true;

        configuration.Slot0.withKP(config.PID_Config.slot0.kP).withKI(config.PID_Config.slot0.kI).withKD(config.PID_Config.slot0.kD).withKS(config.PID_Config.slot0.kS).withKV(config.PID_Config.slot0.kV).withKA(config.PID_Config.slot0.kA).withKG(config.PID_Config.slot0.kG);
        configuration.Slot1.withKP(config.PID_Config.slot1.kP).withKI(config.PID_Config.slot1.kI).withKD(config.PID_Config.slot1.kD).withKS(config.PID_Config.slot1.kS).withKV(config.PID_Config.slot1.kV).withKA(config.PID_Config.slot1.kA).withKG(config.PID_Config.slot1.kG);
        configuration.Slot2.withKP(config.PID_Config.slot2.kP).withKI(config.PID_Config.slot2.kI).withKD(config.PID_Config.slot2.kD).withKS(config.PID_Config.slot2.kS).withKV(config.PID_Config.slot2.kV).withKA(config.PID_Config.slot2.kA).withKG(config.PID_Config.slot2.kG);
        switch (config.PID_Config.gravityCompensationType) {
            case ARM_COSINE:
                configuration.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
                configuration.Slot1.GravityType = GravityTypeValue.Arm_Cosine;
                configuration.Slot2.GravityType = GravityTypeValue.Arm_Cosine;
                break;
            case ELEVATOR_STATIC:
                configuration.Slot0.GravityType = GravityTypeValue.Elevator_Static;
                configuration.Slot1.GravityType = GravityTypeValue.Elevator_Static;
                configuration.Slot2.GravityType = GravityTypeValue.Elevator_Static;
                break;
            default:
                // CTRE doesn't support Arm sine compensation yet.
                break;
        }
        return configuration;
    }

    public static TalonFXSConfiguration getTalonFXSConfig(MotorConfig config) {
        TalonFXSConfiguration configuration = new TalonFXSConfiguration();
        
        configuration.ClosedLoopGeneral.ContinuousWrap = config.PID_Config.continousWrap;

        configuration.MotorOutput.NeutralMode = config.outputConfig.neutralMode == NeutralMode.Brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        configuration.MotorOutput.Inverted = config.outputConfig.outputDirection == OutputDirection.ClockWisePositive ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        configuration.ExternalFeedback.SensorToMechanismRatio = config.outputConfig.sensorToMechanismRatio;
        configuration.CurrentLimits.StatorCurrentLimit = config.currentLimits.maxStator;
        configuration.CurrentLimits.StatorCurrentLimitEnable = true;
        configuration.CurrentLimits.SupplyCurrentLimit = config.currentLimits.maxSupply;
        configuration.CurrentLimits.SupplyCurrentLimitEnable = true;

        configuration.ExternalFeedback.ExternalFeedbackSensorSource = ExternalFeedbackSensorSourceValue.Commutation;

        configuration.Slot0.withKP(config.PID_Config.slot0.kP).withKI(config.PID_Config.slot0.kI).withKD(config.PID_Config.slot0.kD).withKS(config.PID_Config.slot0.kS).withKV(config.PID_Config.slot0.kV).withKA(config.PID_Config.slot0.kA).withKG(config.PID_Config.slot0.kG);
        configuration.Slot1.withKP(config.PID_Config.slot1.kP).withKI(config.PID_Config.slot1.kI).withKD(config.PID_Config.slot1.kD).withKS(config.PID_Config.slot1.kS).withKV(config.PID_Config.slot1.kV).withKA(config.PID_Config.slot1.kA).withKG(config.PID_Config.slot1.kG);
        configuration.Slot2.withKP(config.PID_Config.slot2.kP).withKI(config.PID_Config.slot2.kI).withKD(config.PID_Config.slot2.kD).withKS(config.PID_Config.slot2.kS).withKV(config.PID_Config.slot2.kV).withKA(config.PID_Config.slot2.kA).withKG(config.PID_Config.slot2.kG);
        return configuration;
    }
}
