package simple.lib.logging.data;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import simple.lib.logging.entry.BooleanEntry;
import simple.lib.logging.entry.Entry;
import simple.lib.logging.entry.StructEntry;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class GyroData extends BaseData {
    public final BooleanEntry connected = new BooleanEntry(false);
    public final BooleanEntry alive = new BooleanEntry(false);
    public final StructEntry<Rotation2d> heading = new StructEntry<Rotation2d>(Rotation2d.kZero);
    public final StructEntry<Rotation2d> headingChange = new StructEntry<Rotation2d>(Rotation2d.kZero);
    public final StructEntry<Rotation3d> orientation = new StructEntry<Rotation3d>(Rotation3d.kZero);
    public final StructEntry<Rotation3d> angularVelocity = new StructEntry<Rotation3d>(Rotation3d.kZero);
    public double[] timestampQueue; // DO NOT USE THIS IN LOGGING
    public Rotation2d[] headingQueue;

    @Override
    public Map<String, Entry> getData(String prefix) {
        return Map.of(
                prefix+"Connected", connected,
                prefix+"Alive", alive,
                prefix+"Heading", heading,
                prefix+"Angular Velocity 2d", headingChange,
                prefix+"Orientation", orientation,
                prefix+"Angular Velocity", angularVelocity
        );
    }
}
