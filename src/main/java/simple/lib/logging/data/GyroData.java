package simple.lib.logging.data;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import simple.lib.logging.entry.BooleanEntry;
import simple.lib.logging.entry.StructEntry;

public class GyroData {
    public BooleanEntry connected = new BooleanEntry(false);
    public BooleanEntry alive = new BooleanEntry(false);
    public StructEntry<Rotation2d> heading = new StructEntry<Rotation2d>(Rotation2d.kZero);
    public StructEntry<Rotation2d> headingChange = new StructEntry<Rotation2d>(Rotation2d.kZero);
    public StructEntry<Rotation3d> orientation = new StructEntry<Rotation3d>(Rotation3d.kZero);
    public StructEntry<Rotation3d> angularVelocity = new StructEntry<Rotation3d>(Rotation3d.kZero);
}
