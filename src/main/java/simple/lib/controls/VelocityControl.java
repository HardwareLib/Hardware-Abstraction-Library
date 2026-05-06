package simple.lib.controls;

import edu.wpi.first.units.measure.AngularVelocity;

import static edu.wpi.first.units.Units.RadiansPerSecond;

public class VelocityControl extends Control {
    public VelocityControl(AngularVelocity velocity) {
        this(velocity,0);
    }
    public VelocityControl(AngularVelocity velocity, int slot) {
        super(velocity.in(RadiansPerSecond), ControlType.Velocity,slot);
    }
}
