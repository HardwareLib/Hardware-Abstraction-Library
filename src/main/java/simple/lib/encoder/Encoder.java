package simple.lib.encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.HardwareInterface;
import simple.lib.HardwareRunner;
import simple.lib.LibraryRegistry;
import simple.lib.LibraryRegistry.LibraryType;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.logging.data.EncoderData;

import static edu.wpi.first.units.Units.Radians;

public class Encoder implements HardwareInterface {
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
        HardwareRunner.getInstance().registerInterface(this);
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
}
