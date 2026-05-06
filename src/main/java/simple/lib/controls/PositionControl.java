package simple.lib.controls;

import edu.wpi.first.units.measure.Angle;

import static edu.wpi.first.units.Units.Radians;

public class PositionControl extends Control {
    public PositionControl(Angle position) {
        this(position,0);
    }
    public PositionControl(Angle position, int slot) {
        super(position.in(Radians), ControlType.Position,slot);
    }
}
