package simple.lib.controls;

import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.Volts;

public class VoltageControl extends Control {
    public VoltageControl(Voltage output) {
        super(output.in(Volts),ControlType.Voltage);
    }
}
