package simple.lib.template.hardwareInterface;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.Timer;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.logging.data.EncoderData;

import static edu.wpi.first.units.Units.Seconds;

public class PWMEncoder implements EncoderInterface {
    private AnalogEncoder encoder;
    private AngleUnit defaultUnit;
    private Angle offset;
    double prevTime = Timer.getFPGATimestamp();
    double prevPosition = 0.0;
    public PWMEncoder(int id, EncoderConfig config) {
        encoder = new AnalogEncoder(id);
        defaultUnit = config.defaultUnit;
        offset = config.offset;
    }

    /**
     * @param config
     */
    @Override
    public void configure(EncoderConfig config) {
        defaultUnit = config.defaultUnit;
        offset = config.offset;
    }

    /**
     *
     */
    @Override
    public void getData(EncoderData data) {
        data.position.update(getPosition());
        data.alive.update(true); // I don't know how to check if an encoder is connected so temporary solution for now
        data.connected.update(true);
    }

    /**
     * @return The reported position of the encoder. It may be absolute or relative
     */
    @Override
    public Angle getPosition() {
        return defaultUnit.of(encoder.get()).minus(offset);
    }

    @Override
    public AngularVelocity getVelocity() {
        double currentTime = Timer.getFPGATimestamp();
        double currentPosition = encoder.get();
        double rate = (currentPosition-prevPosition)/(currentTime-prevTime);
        prevTime = currentTime;
        prevPosition = currentPosition;
        return defaultUnit.per(Seconds).of(rate);
    }
}
