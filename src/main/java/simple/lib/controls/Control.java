package simple.lib.controls;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.*;

public class Control {
    public enum ControlType {
        Position,
        PositionProfiled,
        Velocity,
        VelocityProfiled,
        DutyCycle,
        Voltage
    }

    public double output = 0.0;

    public boolean useCurrentControl = false;
    public Current desiredCurrent = Amps.zero();

    public Angle position = Radians.zero();
    public AngularVelocity velocity = RadiansPerSecond.zero();

    public ControlType type = ControlType.Voltage;

    public int slot = 0;

    public Control(double output, ControlType type) {
        this.output = output;
        this.type = type;
    }

    public Control(double output, ControlType type, int slot) {
        this.output = output;
        this.type = type;
        this.slot = slot;
    }

    public final Control copy() {
        return new Control(this.output,this.type, this.slot);
    }

    public final Control invert() {
        this.output *= -1;
        return this;
    }

    public boolean motionProfiled() {
        return this.type == ControlType.PositionProfiled || this.type == ControlType.VelocityProfiled;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Control otherControl) {
            return otherControl.type == this.type && otherControl.output == this.output && otherControl.slot == this.slot;
        } else {
            return false;
        }
    }

    public Control withVelocity(AngularVelocity velocity) {
        this.output = velocity.in(RadiansPerSecond);
        this.velocity = velocity;
        if (this.type == ControlType.VelocityProfiled || this.type == ControlType.PositionProfiled) {
            this.type = ControlType.VelocityProfiled;
        } else {
            this.type = ControlType.Velocity;
        }
        return this;
    }

    public Control withPosition(Angle position) {
        this.output = position.in(Radians);
        this.position = position;
        if (this.type == ControlType.VelocityProfiled || this.type == ControlType.PositionProfiled) {
            this.type = ControlType.PositionProfiled;
        } else {
            this.type = ControlType.Position;
        }
        return this;
    }

    public Control withMotionProfiling(Boolean profiled) {
        switch (this.type) {
            case VelocityProfiled, Velocity:
                this.type = profiled ? ControlType.VelocityProfiled : ControlType.Velocity;
                break;
            case PositionProfiled, Position:
                this.type = profiled ? ControlType.PositionProfiled : ControlType.Position;
                break;
            default:
                break;
        }
        return this;
    }

    public Control withOutput(double output) {
        this.output = MathUtil.clamp(output,-1.0,1.0);
        this.type = ControlType.DutyCycle;
        return this;
    }

    public Control withVoltage(Voltage output) {
        this.output = output.in(Volts);
        this.type = ControlType.Voltage;
        this.useCurrentControl = false;
        return this;
    }

    public Control withSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public Control withCurrent(Current current) {
        this.desiredCurrent = current;
        this.useCurrentControl = true;
        return this;
    } 

    public Control withCurrentControl(boolean enabled) {
        this.useCurrentControl = enabled;
        return this;
    }
}
