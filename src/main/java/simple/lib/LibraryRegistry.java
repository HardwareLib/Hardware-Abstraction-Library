package simple.lib;

import java.util.Map;

public class LibraryRegistry {
    public enum LibraryType {
        Motor,
        Encoder,
        Gyro
    }

    private static Map<LibraryType, Map<String, String>> libraryMap = Map.of(
            LibraryType.Motor, Map.of(),
            LibraryType.Encoder, Map.of(),
            LibraryType.Gyro, Map.of());

    private static Map<LibraryType, Map<String, String>> defaultLibraries = Map.of(
            LibraryType.Motor, Map.of(
                "TalonFX", "simple.phoenix6.hardware.TalonFXInterface",
                "TalonFXS", "simple.phoenix6.hardware.TalonFXSInterface",
                "TalonSRX", "simple.phoenix5.hardware.TalonSRXInterface", // TODO: Make this
                "SparkMax", "simple.revrobotics.hardware.SparkMaxInterface", // TODO: Make this
                "SparkFlex", "simple.revrobotics.hardware.SparkFlexInterface", // TODO: Make this
                "Nitrate", "simple.reduxrobotics.hardware.NitrateInterface", // TODO: Make this
                "Nova", "simple.thrifty.hardware.NovaInterface", // TODO: Make this
                "Sim", "simple.lib.template.hardwareInterface.SimMotor"
            ),
            LibraryType.Encoder, Map.of(
                "CANCoder", "simple.phoenix6.hardware.CANCoder",
                "Helium", "simple.reduxrobotics.hardware.CANAndMag", // TODO: Make this
                "CANAndMag", "simple.reduxrobotics.hardware.CANAndMag", // Kept as a nickname for CANAndMag
                "PWM", "simple.lib.template.hardwareInterface.PWMEncoder",
                "Quadrature", "simple.lib.template.hardwareInterface.QuadratureEncoder"
            ),
            LibraryType.Gyro, Map.of(
                    "Pigeon2", "simple.phoenix6.hardware.Pigeon2Interface", // TODO: Make this
                    "CanAndGyro", "simple.reduxrobotics.hardware.CanAndGyro", // TODO: Make this
                    "Boron", "simple.reduxrobotics.hardware.CanAndGyro",  // Kept as a nickname for CANAndGyro
                    "NavX3", "simple.studica_can.hardware.navx3", // TODO: Make this
                    "NavX_MXP",  "simple.studica_mxp.hardware.navx_mxp" // TODO: Make this
            ));

    public static String getOverride(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, "noOverride");
    }

    public static String getOverrideOrDefault(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, defaultLibraries.get(type).getOrDefault(interfaceName, "No Library Exists"));
    }
}
