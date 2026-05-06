package simple.lib.controls;

import edu.wpi.first.units.measure.Angle;

import static edu.wpi.first.units.Units.Radians;

public class ProfiledPositionControl extends Control {
    public ProfiledPositionControl(Angle position) {
        this(position,0);
    }
    public ProfiledPositionControl(Angle position, int slot) {
        super(position.in(Radians), ControlType.PositionProfiled,slot);
    }
}
