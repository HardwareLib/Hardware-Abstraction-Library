package simple.lib.logging;

import edu.wpi.first.units.Unit;
import simple.lib.logging.data.BaseData;

import java.util.Map;

public interface Logger {
    public default void logData(BaseData data) {}
}
