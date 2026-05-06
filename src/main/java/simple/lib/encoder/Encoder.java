package simple.lib.encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.template.hardwareInterface.PWMEncoder;
import simple.lib.template.hardwareInterface.QuadratureEncoder;

import static edu.wpi.first.units.Units.Radians;

public class Encoder {
    public enum EncoderType {
        CANCoder,
        CANAndMag,
        PWM,
        Quadrature
    }

    private int id;
    private EncoderType type;
    private EncoderConfig config;
    private EncoderInterface encoderInterface;

    public Encoder(int id, EncoderType type, EncoderConfig config) {
        this.id  = id;
        this.type = type;
        this.config = config;

        switch (type) {
            case PWM:
                this.encoderInterface = new PWMEncoder(id,config);
                break;
            case CANCoder:
                // CTRE CANCoder
                break;
            case CANAndMag:
                // CANAndMag
                break;
            case Quadrature:
                // Quadrature Encoder
                this.encoderInterface = new QuadratureEncoder(id,config);
                break;
            default:
                break;
        }
    }

    public void configure(EncoderConfig config) {
        this.config = config;
        encoderInterface.configure(config);
    }

    public Angle getPosition() {return encoderInterface.getPosition();}
    public AngularVelocity getVelocity() {return encoderInterface.getVelocity();}
    public Rotation2d getRotation2d() {return Rotation2d.fromRadians(encoderInterface.getPosition().in(Radians));}
}
