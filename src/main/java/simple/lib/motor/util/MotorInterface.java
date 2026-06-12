package simple.lib.motor.util;

import edu.wpi.first.units.measure.Angle;
import simple.lib.controls.Control;
import simple.lib.logging.data.MotorData;

public interface MotorInterface {
    public default void periodic() {}

    public abstract void getData(MotorData data);
    public abstract void configure(MotorConfig config);
    public default void setPosition(Angle position) {}
    public default void setControl(double output, Control.ControlType outputType) {
        setControl(new Control(output,outputType));
    }

    public default void setControl(double output, Control.ControlType outputType, int slot) {
        setControl(new Control(output,outputType,slot));
    }

    public abstract void setControl(Control control);

    public abstract void stop();
}
