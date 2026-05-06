package simple.lib.encoder.util;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.logging.data.EncoderData;

public interface EncoderInterface {
    public abstract void configure(EncoderConfig config);
    public abstract void getData(EncoderData data);
    public abstract Angle getPosition();
    public abstract AngularVelocity getVelocity();
}
