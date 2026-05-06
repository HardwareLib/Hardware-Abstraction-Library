package simple.lib;

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

    private static Map<LibraryType, Map<String, String>> defaultLibraries = Map.of(
            LibraryType.Motor, Map.of(
                "TalonFX", "simple.phoenix6.hardware.TalonFX",
                "TalonFXS", "simple.phoenix6.hardware.TalonFXS",
                "TalonSRX", "simple.phoenix5.hardware.TalonSRX",
                "SparkMax", "simple.revrobotics.hardware.SparkMax",
                "SparkFlex", "simple.revrobotics.hardware.SparkFlex",
                "Nitrate", "simple.reduxrobotics.hardware.Nitrate",
                "Nova", "simple.thrifty.hardware.Nova",
                "Sim", "simple.lib.template.hardwareInterface.SimMotor"
            ),
            LibraryType.Encoder, Map.of(
                "CANCoder", "simple.phoenix6.hardware.CANCoder",
                "Helium", "simple.reduxrobotics.hardware.CANAndMag",
                "CANAndMag", "simple.reduxrobotics.hardware.CANAndMag",
                "PWM", "simple.lib.template.hardwareInterface.PWMEncoder",
                "Quadrature", "simple.lib.template.hardwareInterface.QuadratureEncoder"
            ),
            LibraryType.Gyro, Map.of(
                "Pidgeon2", "",
                "CanAndGyro", "",
                "NavX3", "",
                "NavX2MXP", ""
            ),
            LibraryType.Logger, Map.of());

    public static String getOverride(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, "noOverride");
    }

    public static String getOverrideOrDefault(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, defaultLibraries.get(type).getOrDefault(interfaceName, "No Library Exists"));
    }
}
