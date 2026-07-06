package simple.lib.logging.data;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.AngleUnit;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.StructEntry;
import simple.lib.logging.entry.UnitEntry;

import static edu.wpi.first.units.Units.Rotations;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class SwerveModuleData extends BaseData {
    public MotorData drive = new MotorData();
    public MotorData steer = new MotorData();
    public EncoderData encoder = new EncoderData();

    public final StructEntry<SwerveModuleState> state = new StructEntry<SwerveModuleState>(new SwerveModuleState());
    public final StructEntry<SwerveModulePosition> position = new StructEntry<SwerveModulePosition>(new SwerveModulePosition());

    public final UnitEntry<AngleUnit> steerAbsolutePosition = new UnitEntry<>(Rotations.zero());

    public Map<String, Entry> getData(String prefix) {
        steerAbsolutePosition.update(encoder.position.getValue());
        Map<String,Entry> data = new HashMap<>(Map.of("State", state,"position",position,"Steer Absolute Position",steerAbsolutePosition));
        data.putAll(drive.getData(prefix+"drive/"));
        data.putAll(steer.getData(prefix+"steer/"));
        data.putAll(encoder.getData(prefix+"encoder/"));
        return data;
    }

    public double[] timestampQueue;
    public double[] drivePositionQueue;
    public Rotation2d[] steerPositionQueue;
}
