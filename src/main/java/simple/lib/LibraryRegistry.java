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
                "TalonSRX", "simple.phoenix5.hardware.TalonSRXInterface",
                "SparkMax", "simple.revrobotics.hardware.SparkMaxInterface",
                "SparkFlex", "simple.revrobotics.hardware.SparkFlexInterface",
                "Nitrate", "simple.reduxrobotics.hardware.NitrateInterface",
                "Nova", "simple.thrifty.hardware.NovaInterface",
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
                    "Pidgeon2", "simple.phoenix6.hardware.Pidgeon2",
                    "CanAndGyro", "simple.reduxrobotics.hardware.CanAndGyro",
                    "Boron", "simple.reduxrobotics.hardware.CanAndGyro",
                    "NavX3", "simple.studica_can.hardware.navx3",
                    "NavX_MXP",  "simple.studica_mxp.hardware.navx_mxp"
            ));

    public static String getOverride(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, "noOverride");
    }

    public static String getOverrideOrDefault(LibraryType type, String interfaceName) {
        return libraryMap.get(type).getOrDefault(interfaceName, defaultLibraries.get(type).getOrDefault(interfaceName, "No Library Exists"));
    }
}
