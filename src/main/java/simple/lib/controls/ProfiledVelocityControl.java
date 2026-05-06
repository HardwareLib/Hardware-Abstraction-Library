package simple.lib.controls;

import edu.wpi.first.units.measure.AngularVelocity;

import static edu.wpi.first.units.Units.RadiansPerSecond;

public class ProfiledVelocityControl extends Control {
    public ProfiledVelocityControl(AngularVelocity velocity) {
        this(velocity,0);
    }
    public ProfiledVelocityControl(AngularVelocity velocity, int slot) {
        super(velocity.in(RadiansPerSecond), ControlType.VelocityProfiled,slot);
    }
}

