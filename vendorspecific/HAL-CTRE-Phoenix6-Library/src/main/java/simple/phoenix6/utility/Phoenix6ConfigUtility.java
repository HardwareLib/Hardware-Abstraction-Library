// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.utility;

import com.ctre.phoenix6.configs.ParentConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.signals.ExternalFeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.FeedbackConfig.FeedbackSource;
import simple.lib.motor.util.MotorConfig.OutputConfig.NeutralMode;
import simple.lib.motor.util.MotorConfig.OutputConfig.OutputDirection;

/** Add your docs here. */
public class Phoenix6ConfigUtility {
    public static TalonFXConfiguration getTalonFXConfig(MotorConfig config) {
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        
        configuration.ClosedLoopGeneral.ContinuousWrap = config.feedback.continousWrap;

        configuration.MotorOutput.NeutralMode = config.outputConfig.neutralMode == NeutralMode.Brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        configuration.MotorOutput.Inverted = config.outputConfig.outputDirection == OutputDirection.ClockWisePositive ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        configuration.Feedback.SensorToMechanismRatio = config.feedback.sensorToMechanismRatio;

        switch (config.feedback.feedbackSource) {
            case CanEncoder:
                configuration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
                configuration.Feedback.FeedbackRemoteSensorID = config.feedback.encoderId;
                // This is assuming that motor to mechanism ratio and sensor to mechanism ratio isn't 1
                configuration.Feedback.RotorToSensorRatio = config.feedback.motorToMechanismRatio/config.feedback.sensorToMechanismRatio;
                break;
            default:
                // TALONFX doesn't support External Encoders connected to motor controller and we can use Internal encoders for both the internal encoder config and fused encoder config;
                configuration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
                break;
        }

        configuration.CurrentLimits.StatorCurrentLimit = config.currentLimits.maxStator;
        configuration.CurrentLimits.StatorCurrentLimitEnable = true;
        configuration.CurrentLimits.SupplyCurrentLimit = config.currentLimits.maxSupply;
        configuration.CurrentLimits.SupplyCurrentLimitEnable = true;

        configuration.Voltage.PeakForwardVoltage = config.voltageLimits.maxVoltage;
        configuration.Voltage.PeakReverseVoltage = config.voltageLimits.minVoltage;

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
        
        configuration.ClosedLoopGeneral.ContinuousWrap = config.feedback.continousWrap;

        configuration.MotorOutput.NeutralMode = config.outputConfig.neutralMode == NeutralMode.Brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        configuration.MotorOutput.Inverted = config.outputConfig.outputDirection == OutputDirection.ClockWisePositive ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        configuration.ExternalFeedback.SensorToMechanismRatio = config.feedback.sensorToMechanismRatio;
        configuration.CurrentLimits.StatorCurrentLimit = config.currentLimits.maxStator;
        configuration.CurrentLimits.StatorCurrentLimitEnable = true;
        configuration.CurrentLimits.SupplyCurrentLimit = config.currentLimits.maxSupply;
        configuration.CurrentLimits.SupplyCurrentLimitEnable = true;

        switch (config.feedback.feedbackSource) {
            case ExternalAbsoluteEncoder:
                configuration.ExternalFeedback.ExternalFeedbackSensorSource = ExternalFeedbackSensorSourceValue.PulseWidth;
                configuration.ExternalFeedback.RotorToSensorRatio = config.feedback.motorToMechanismRatio/config.feedback.sensorToMechanismRatio;
                break;
            case ExternalRelativeEncoder:
                configuration.ExternalFeedback.ExternalFeedbackSensorSource = ExternalFeedbackSensorSourceValue.Quadrature;
                configuration.ExternalFeedback.RotorToSensorRatio = config.feedback.motorToMechanismRatio/config.feedback.sensorToMechanismRatio;
                break;
            case CanEncoder:
                configuration.ExternalFeedback.ExternalFeedbackSensorSource = ExternalFeedbackSensorSourceValue.RemoteCANcoder;
                configuration.ExternalFeedback.FeedbackRemoteSensorID = config.feedback.encoderId;
                // This is assuming that motor to mechanism ratio and sensor to mechanism ratio isn't 1
                configuration.ExternalFeedback.RotorToSensorRatio = config.feedback.motorToMechanismRatio/config.feedback.sensorToMechanismRatio;
                break;
            default:
                // TALONFX doesn't support External Encoders connected to motor controller and we can use Internal encoders for both the internal encoder config and fused encoder config;
                configuration.ExternalFeedback.ExternalFeedbackSensorSource = ExternalFeedbackSensorSourceValue.Commutation;
                break;
        }

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

    public static MotorConfig getConfig(ParentConfiguration config) {
        MotorConfig outputConfig = new MotorConfig();
        if (config instanceof TalonFXConfiguration) {
            outputConfig.feedback.continousWrap = ((TalonFXConfiguration)config).ClosedLoopGeneral.ContinuousWrap;
            outputConfig.feedback.encoderId = ((TalonFXConfiguration)config).Feedback.FeedbackRemoteSensorID;
            outputConfig.feedback.feedbackSource = switch(((TalonFXConfiguration)config).Feedback.FeedbackSensorSource) {
                case FusedCANcoder -> FeedbackSource.CanEncoder;
                case RemoteCANcoder -> FeedbackSource.CanEncoder;
                case RotorSensor -> FeedbackSource.InternalEncoder;
                case SyncCANcoder -> FeedbackSource.CanEncoder;
                default -> FeedbackSource.InternalEncoder;};
            outputConfig.feedback.sensorToMechanismRatio = ((TalonFXConfiguration)config).Feedback.SensorToMechanismRatio;
            outputConfig.feedback.motorToMechanismRatio = ((TalonFXConfiguration)config).Feedback.RotorToSensorRatio * outputConfig.feedback.sensorToMechanismRatio;

            outputConfig.PID_Config.slot0.kP = ((TalonFXConfiguration) config).Slot0.kP;
            outputConfig.PID_Config.slot0.kI = ((TalonFXConfiguration) config).Slot0.kI;
            outputConfig.PID_Config.slot0.kD = ((TalonFXConfiguration) config).Slot0.kD;
            outputConfig.PID_Config.slot0.kS = ((TalonFXConfiguration) config).Slot0.kS;
            outputConfig.PID_Config.slot0.kG = ((TalonFXConfiguration) config).Slot0.kG;
            outputConfig.PID_Config.slot0.kV = ((TalonFXConfiguration) config).Slot0.kV;
            outputConfig.PID_Config.slot0.kA = ((TalonFXConfiguration) config).Slot0.kA;
            
            outputConfig.PID_Config.slot1.kP = ((TalonFXConfiguration) config).Slot1.kP;
            outputConfig.PID_Config.slot1.kI = ((TalonFXConfiguration) config).Slot1.kI;
            outputConfig.PID_Config.slot1.kD = ((TalonFXConfiguration) config).Slot1.kD;
            outputConfig.PID_Config.slot1.kS = ((TalonFXConfiguration) config).Slot1.kS;
            outputConfig.PID_Config.slot1.kG = ((TalonFXConfiguration) config).Slot1.kG;
            outputConfig.PID_Config.slot1.kV = ((TalonFXConfiguration) config).Slot1.kV;
            outputConfig.PID_Config.slot1.kA = ((TalonFXConfiguration) config).Slot1.kA;

            outputConfig.PID_Config.slot2.kP = ((TalonFXConfiguration) config).Slot2.kP;
            outputConfig.PID_Config.slot2.kI = ((TalonFXConfiguration) config).Slot2.kI;
            outputConfig.PID_Config.slot2.kD = ((TalonFXConfiguration) config).Slot2.kD;
            outputConfig.PID_Config.slot2.kS = ((TalonFXConfiguration) config).Slot2.kS;
            outputConfig.PID_Config.slot2.kG = ((TalonFXConfiguration) config).Slot2.kG;
            outputConfig.PID_Config.slot2.kV = ((TalonFXConfiguration) config).Slot2.kV;
            outputConfig.PID_Config.slot2.kA = ((TalonFXConfiguration) config).Slot2.kA;


            outputConfig.outputConfig.neutralMode = ((TalonFXConfiguration)config).MotorOutput.NeutralMode == NeutralModeValue.Brake ? NeutralMode.Brake : NeutralMode.Coast;
            outputConfig.outputConfig.outputDirection = getDirectionFromPhoenix(((TalonFXConfiguration)config).MotorOutput.Inverted);
            
            outputConfig.currentLimits.maxStator = (int) ((TalonFXConfiguration)config).CurrentLimits.StatorCurrentLimit;
            outputConfig.currentLimits.maxSupply = (int) ((TalonFXConfiguration)config).CurrentLimits.SupplyCurrentLimit;
            outputConfig.voltageLimits.maxVoltage = ((TalonFXConfiguration)config).Voltage.PeakForwardVoltage;
            outputConfig.voltageLimits.minVoltage = ((TalonFXConfiguration)config).Voltage.PeakReverseVoltage;
        } else if (config instanceof TalonFXSConfiguration) {
            outputConfig.feedback.continousWrap = ((TalonFXSConfiguration)config).ClosedLoopGeneral.ContinuousWrap;
            outputConfig.feedback.encoderId = ((TalonFXSConfiguration)config).ExternalFeedback.FeedbackRemoteSensorID;
            outputConfig.feedback.feedbackSource = switch(((TalonFXSConfiguration)config).ExternalFeedback.ExternalFeedbackSensorSource) {
                case FusedCANcoder -> FeedbackSource.CanEncoder;
                case RemoteCANcoder -> FeedbackSource.CanEncoder;
                case Commutation -> FeedbackSource.InternalEncoder;
                case SyncCANcoder -> FeedbackSource.CanEncoder;
                default -> FeedbackSource.InternalEncoder;};
            outputConfig.feedback.sensorToMechanismRatio = ((TalonFXSConfiguration)config).ExternalFeedback.SensorToMechanismRatio;
            outputConfig.feedback.motorToMechanismRatio = ((TalonFXSConfiguration)config).ExternalFeedback.RotorToSensorRatio * outputConfig.feedback.sensorToMechanismRatio;

            outputConfig.PID_Config.slot0.kP = ((TalonFXSConfiguration) config).Slot0.kP;
            outputConfig.PID_Config.slot0.kI = ((TalonFXSConfiguration) config).Slot0.kI;
            outputConfig.PID_Config.slot0.kD = ((TalonFXSConfiguration) config).Slot0.kD;
            outputConfig.PID_Config.slot0.kS = ((TalonFXSConfiguration) config).Slot0.kS;
            outputConfig.PID_Config.slot0.kG = ((TalonFXSConfiguration) config).Slot0.kG;
            outputConfig.PID_Config.slot0.kV = ((TalonFXSConfiguration) config).Slot0.kV;
            outputConfig.PID_Config.slot0.kA = ((TalonFXSConfiguration) config).Slot0.kA;
            
            outputConfig.PID_Config.slot1.kP = ((TalonFXSConfiguration) config).Slot1.kP;
            outputConfig.PID_Config.slot1.kI = ((TalonFXSConfiguration) config).Slot1.kI;
            outputConfig.PID_Config.slot1.kD = ((TalonFXSConfiguration) config).Slot1.kD;
            outputConfig.PID_Config.slot1.kS = ((TalonFXSConfiguration) config).Slot1.kS;
            outputConfig.PID_Config.slot1.kG = ((TalonFXSConfiguration) config).Slot1.kG;
            outputConfig.PID_Config.slot1.kV = ((TalonFXSConfiguration) config).Slot1.kV;
            outputConfig.PID_Config.slot1.kA = ((TalonFXSConfiguration) config).Slot1.kA;

            outputConfig.PID_Config.slot2.kP = ((TalonFXSConfiguration) config).Slot2.kP;
            outputConfig.PID_Config.slot2.kI = ((TalonFXSConfiguration) config).Slot2.kI;
            outputConfig.PID_Config.slot2.kD = ((TalonFXSConfiguration) config).Slot2.kD;
            outputConfig.PID_Config.slot2.kS = ((TalonFXSConfiguration) config).Slot2.kS;
            outputConfig.PID_Config.slot2.kG = ((TalonFXSConfiguration) config).Slot2.kG;
            outputConfig.PID_Config.slot2.kV = ((TalonFXSConfiguration) config).Slot2.kV;
            outputConfig.PID_Config.slot2.kA = ((TalonFXSConfiguration) config).Slot2.kA;


            outputConfig.outputConfig.neutralMode = ((TalonFXSConfiguration)config).MotorOutput.NeutralMode == NeutralModeValue.Brake ? NeutralMode.Brake : NeutralMode.Coast;
            outputConfig.outputConfig.outputDirection = getDirectionFromPhoenix(((TalonFXSConfiguration)config).MotorOutput.Inverted);
            
            outputConfig.currentLimits.maxStator = (int) ((TalonFXSConfiguration)config).CurrentLimits.StatorCurrentLimit;
            outputConfig.currentLimits.maxSupply = (int) ((TalonFXSConfiguration)config).CurrentLimits.SupplyCurrentLimit;
            outputConfig.voltageLimits.maxVoltage = ((TalonFXSConfiguration)config).Voltage.PeakForwardVoltage;
            outputConfig.voltageLimits.minVoltage = ((TalonFXSConfiguration)config).Voltage.PeakReverseVoltage;
        }
        return outputConfig;
    }

    public static OutputDirection getDirectionFromPhoenix(InvertedValue value) {
        switch (value) {
            case Clockwise_Positive:
                return OutputDirection.ClockWisePositive;
            case CounterClockwise_Positive:
                return OutputDirection.CounterClockWisePositive;
            default:
                return OutputDirection.ClockWisePositive;
        }
    }
}
