package simple.lib.logging.data;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.StructArrayEntry;
import simple.lib.logging.entry.StructEntry;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class SwerveData extends BaseData {
    public final SwerveModuleData[] modules = new SwerveModuleData[]{new SwerveModuleData(),new SwerveModuleData(),new SwerveModuleData(),new SwerveModuleData()};
    public final GyroData gyro = new GyroData();
    public final StructEntry<Pose2d> pose = new StructEntry<Pose2d>(Pose2d.kZero);
    public final StructArrayEntry<SwerveModuleState> measuredStates = new StructArrayEntry<>( new SwerveModuleState[]{
            new SwerveModuleState(),
            new SwerveModuleState(),
            new SwerveModuleState(),
            new SwerveModuleState()
    });

    public final StructArrayEntry<SwerveModuleState> targetStates = new StructArrayEntry<>( new SwerveModuleState[]{
            new SwerveModuleState(),
            new SwerveModuleState(),
            new SwerveModuleState(),
            new SwerveModuleState()
    });

    public final StructArrayEntry<SwerveModulePosition> positions = new StructArrayEntry<>( new SwerveModulePosition[]{
            new SwerveModulePosition(),
            new SwerveModulePosition(),
            new SwerveModulePosition(),
            new SwerveModulePosition()
    });

    @Override
    public Map<String, Entry> getData(String prefix) {
        Map<String,Entry> data = new HashMap<>(Map.of(
                prefix+"Pose", pose,
                prefix+"Measured States", measuredStates,
                prefix+"Target States", targetStates,
                prefix+"Positions", positions
        ));
        data.putAll(gyro.getData(prefix+"Gyro/"));
        data.putAll(modules[0].getData(prefix+"Modules/Front Left/"));
        data.putAll(modules[1].getData(prefix+"Modules/Front Right/"));
        data.putAll(modules[2].getData(prefix+"Modules/Back Left/"));
        data.putAll(modules[3].getData(prefix+"Modules/Back Right/"));
        return data;
    }
}
