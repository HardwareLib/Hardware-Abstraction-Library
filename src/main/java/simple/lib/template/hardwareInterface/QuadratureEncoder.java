package simple.lib.template.hardwareInterface;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Encoder;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.logging.data.EncoderData;

import static edu.wpi.first.units.Units.Seconds;

public class QuadratureEncoder implements EncoderInterface {
    Encoder quadrature;
    private EncoderConfig config;
    private AngleUnit baseUnit;
    private Angle offset;
    public QuadratureEncoder(int apin, EncoderConfig config) {
        quadrature = new Encoder(apin, config.bPin);
        this.config = config;
        baseUnit = config.defaultUnit;
        offset = config.offset;
    }

    /**
     * @param config
     */
    @Override
    public void configure(EncoderConfig config) {
        this.config = config;
        baseUnit = config.defaultUnit;
        offset = config.offset;
    }

    /**
     * @param data
     */
    @Override
    public void getData(EncoderData data) {
        data.connected.update(true);
        data.alive.update(true);
    }

    /**
     * @return Position recorded by the encoder
     */
    @Override
    public Angle getPosition() {
        return baseUnit.of(quadrature.getDistance()).minus(offset);
    }

    @Override
    public AngularVelocity getVelocity() {
        return baseUnit.per(Seconds).of(quadrature.getRate());
    }
}
