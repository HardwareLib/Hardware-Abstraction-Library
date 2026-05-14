package simple.lib.logging.data;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.StructEntry;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class SwerveModuleData extends BaseData {
    public final MotorData drive = new MotorData();
    public final MotorData steer = new MotorData();
    public final EncoderData encoder = new EncoderData();

    public final StructEntry<SwerveModuleState> state = new StructEntry<SwerveModuleState>(new SwerveModuleState());
    public final StructEntry<SwerveModulePosition> position = new StructEntry<SwerveModulePosition>(new SwerveModulePosition());

    public Map<String, Entry> getData(String prefix) {
        Map<String,Entry> data = new HashMap<>(Map.of("State", state,"position",position));
        data.putAll(drive.getData(prefix+"drive/"));
        data.putAll(steer.getData(prefix+"steer/"));
        data.putAll(encoder.getData(prefix+"encoder/"));
        return data;
    }
}
