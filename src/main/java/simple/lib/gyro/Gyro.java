package simple.lib.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.LibraryRegistry;
import simple.lib.gyro.util.GyroConfig;
import simple.lib.gyro.util.GyroInterface;
import simple.lib.logging.data.GyroData;

import java.util.function.Supplier;

/**
 * Work In Progress
 * DO NOT USE
 */
public class Gyro extends SubsystemBase {
    public enum GyroType {
        Pidgeon2,
        CanAndGyro,
        Boron,
        NavX3,
        NavX_MXP
    }

    private GyroInterface gyroInterface;

    public boolean periodicDataCollection = false;
    private GyroData data = new GyroData();
    private int id;
    /**
     * Work In Progress
     * DO NOT USE
     */
    @SuppressWarnings("unchecked")
    public Gyro(int id, GyroType type, GyroConfig config) {
        this.id = id;
        Class<GyroInterface> interfaceClass;
        try {
            interfaceClass = (Class<GyroInterface>) ClassLoader.getSystemClassLoader().loadClass(LibraryRegistry.getOverrideOrDefault(LibraryRegistry.LibraryType.Gyro,type.toString()));
            if (interfaceClass != null) {
                gyroInterface = interfaceClass.getConstructor(int.class, GyroConfig.class).newInstance(id,config);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            switch (type) {
                case Pidgeon2:
                    throw new Error("Fatal Error: You must install both the Phoenix 6 Library, and the Unofficial Phoenix 6 Abstraction Library");
                case Boron, CanAndGyro:
                    throw new Error("Fatal Error: You must install both ReduxLib, and the Unofficial Redux Robotics Abstraction Library");
                case NavX3:
                    throw new Error("Fatal Error: You must install both StudicaLib, and the Unofficial Studica CAN Abstraction Library");
                case NavX_MXP:
                    throw new Error("Fatal Error: You must install both Studica, and the Unofficial Studica MXP Abstraction Library"); // The difference between Studica and StudicaLib is so stupid. Just integrate the MXP gyros into the main I beg of you.
            }
        }
    }

    public void configure(GyroConfig config) {
        if (gyroInterface != null) {
            gyroInterface.configure(config);
        } else {
            throw new RuntimeException("Failed to Configure Motor with id of "+this.id+" check if you are instantiating it correctly.");
        }
    }

    public GyroData getData() {
        if (!periodicDataCollection) {
            data.heading.update(gyroInterface.getHeading());
            data.headingChange.update(gyroInterface.getHeadingRate());
            data.orientation.update(gyroInterface.getOrientation());
            data.angularVelocity.update(gyroInterface.getAngularVelocity());
        }
        return data;
    }

    @Override
    public void periodic() {
        if (periodicDataCollection) {
            data.heading.update(gyroInterface.getHeading());
            data.headingChange.update(gyroInterface.getHeadingRate());
            data.orientation.update(gyroInterface.getOrientation());
            data.angularVelocity.update(gyroInterface.getAngularVelocity());
        }
    }

    public Rotation2d getHeading() {
        return gyroInterface.getHeading();
    }

    public Rotation2d getAngularVelocity2d() {
        return gyroInterface.getHeadingRate();
    }

    public Rotation3d getOrientation() {
        return gyroInterface.getOrientation();
    }

    public Rotation3d getAngularVelocity() {
        return gyroInterface.getAngularVelocity();
    }

    // Prevent users from treating this like it is a proper subsystem
    @Override
    public Command run(Runnable run) {
        return Commands.none();
    }

    @Override
    public Command startRun(Runnable start, Runnable run) {
        return Commands.none();
    }

    @Override
    public Command idle() {
        return Commands.none();
    }
    @Override
    public Command runOnce(Runnable action) {
        return Commands.none();
    }
    @Override
    public Command startEnd(Runnable start, Runnable end) {
        return Commands.none();
    }
    @Override
    public Command runEnd(Runnable run, Runnable end) {
        return Commands.none();
    }

    @Override
    public Command defer(Supplier<Command> supplier) {
        return Commands.none();
    }

    @Override
    public void setDefaultCommand(Command command) {

    }

    @Override
    public void removeDefaultCommand() {}
}
