package simple.lib.encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import simple.lib.LibraryRegistry;
import simple.lib.LibraryRegistry.LibraryType;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.logging.data.EncoderData;

import java.util.function.Supplier;

import static edu.wpi.first.units.Units.Radians;

public class Encoder extends SubsystemBase {
    public enum EncoderType {
        CANCoder,
        CANAndMag,
        Helium,
        PWM,
        Quadrature
    }

    private EncoderInterface encoderInterface;
    private EncoderData data = new EncoderData();
    public boolean periodicDataCollection = false;
    @SuppressWarnings("unchecked")
    public Encoder(int id, EncoderType type, EncoderConfig config) {
        Class<EncoderInterface> interfaceClass;
        try {
            interfaceClass = (Class<EncoderInterface>) ClassLoader.getSystemClassLoader().loadClass(LibraryRegistry.getOverrideOrDefault(LibraryType.Encoder,type.toString()));
            if (interfaceClass != null) {
                encoderInterface = interfaceClass.getConstructor(int.class, EncoderConfig.class).newInstance(id,config);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            switch (type) {
                case CANCoder:
                    throw new Error("Fatal Error: You must install both the Phoenix 6 Library, and the Unofficial Phoenix 6 Abstraction Library");
                case Helium, CANAndMag:
                    throw new Error("Fatal Error: You must install both ReduxLib, and the Unofficial Redux Robotics Abstraction Library");
                case PWM,Quadrature:
                    throw new Error("Fatal Error: Something has gone wrong please contact the owner of the library to help fix this.");
            }
        }
    }

    @Override
    public void periodic() {
        if (periodicDataCollection) {
            encoderInterface.getData(data);
        }
    }

    public EncoderData getData() {
        if (!periodicDataCollection) {
            encoderInterface.getData(data);
        }
        return data;
    }

    public void configure(EncoderConfig config) {
        encoderInterface.configure(config);
    }

    public Angle getPosition() {return encoderInterface.getPosition();}
    public AngularVelocity getVelocity() {return encoderInterface.getVelocity();}
    public Rotation2d getRotation2d() {return Rotation2d.fromRadians(encoderInterface.getPosition().in(Radians));}

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
