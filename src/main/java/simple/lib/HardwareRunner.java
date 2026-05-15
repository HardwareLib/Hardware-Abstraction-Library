package simple.lib;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;

public class HardwareRunner extends SubsystemBase {
    private static HardwareRunner instance;
    private ArrayList<HardwareInterface> interfaces = new ArrayList<>();
    static {
        instance = getInstance();
    }

    public static HardwareRunner getInstance() {
        if (instance == null) {
            instance = new HardwareRunner();
        }
        return instance;
    }

    public void registerInterface(HardwareInterface hInterface) {
        interfaces.add(hInterface);
    }

    @Override
    public void periodic() {
        for (HardwareInterface hInterface : interfaces) {
            hInterface.periodic();
        }
    }
}
