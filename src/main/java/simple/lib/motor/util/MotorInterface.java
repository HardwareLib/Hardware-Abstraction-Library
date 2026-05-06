package simple.lib.motor.util;

import edu.wpi.first.units.measure.Angle;
import simple.lib.controls.Control;
import simple.lib.logging.data.MotorData;

public interface MotorInterface {
    public default void configPID(double kP,double kI, double kD, double kV, double kA, double kS, double kG) {
        configPID(kP,kI,kD,kV,kA,kS,kG,0);
    }

    public default void periodic() {}

    public abstract void getData(MotorData data);

    /**
     * @param kP the kP of the controller
     * @param kI
     * @param kD
     * @param kV
     * @param kA
     * @param kS
     * @param kG
     * @param slot
     */
    public default void configPID(double kP,double kI, double kD, double kV, double kA, double kS, double kG, int slot) {
        MotorConfig.PID.SlotConfig config = new MotorConfig.PID.SlotConfig();
        config.kP = kP;
        config.kI = kI;
        config.kD = kD;
        config.kV = kV;
        config.kA = kA;
        config.kS = kS;
        config.kG = kG;
        configPID(config,slot);
    }

    public abstract void configure(MotorConfig config);
    public default void configPID(MotorConfig.PID.SlotConfig config) {
        configPID(config,0);
    }
    public default void setPosition(Angle position) {}
    public abstract void configPID(MotorConfig.PID.SlotConfig config, int slot);
    public default void setControl(double output, Control.ControlType outputType) {
        setControl(new Control(output,outputType));
    }

    public default void setControl(double output, Control.ControlType outputType, int slot) {
        setControl(new Control(output,outputType,slot));
    }

    public abstract void setControl(Control control);

    public abstract void stop();
}
