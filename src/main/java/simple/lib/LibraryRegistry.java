package simple.lib;

import java.util.HashMap;
import java.util.Map;

public class LibraryRegistry {
    public enum LibraryType {
        Motor,
        Encoder,
        Gyro,
        Logger
    }

    private static Map<LibraryType, Map<String, String>> libraryMap = Map.of(
            LibraryType.Motor, Map.of(),
            LibraryType.Encoder, Map.of(),
            LibraryType.Gyro, Map.of(),
            LibraryType.Logger, Map.of());

    public static String getOverride(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, "noOverride");
    }
}
