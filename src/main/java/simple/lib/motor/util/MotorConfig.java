package simple.lib.motor.util;

import simple.lib.encoder.Encoder;

public class MotorConfig {

    public String canbus = ""; // Multiple CAN BUS support only for devices which support it, and 2027 season onwards
    public double updateFrequency = 50.0; // Update Frequency for data from the motor. Only for devices which support it
    public static class PID {
        public static class SlotConfig {
            public double kP = 0.0;
            public double kI = 0.0;
            public double kD = 0.0;
            public double kV = 0.0;
            public double kA = 0.0;
            public double kS = 0.0;
            public double kG = 0.0;
        }

        public static class MotionProfilingConfig {
            public double maxVelocity = 0.0;
            public double maxAcceleration = 0.0;
        }


        public enum GravityCompensationType {
            /** Static constant kG that is frequently used in Elevator mechanisms */
            ELEVATOR_STATIC,
            /** kG that is calculated by taking the cosine of the current Angle, it is frequently used in Arm mechanisms */
            ARM_COSINE,
            /** kG that is calculated by taking the sine of the current Angle, it is frequently used in Arm mechanisms */
            ARM_SINE
        }

        public GravityCompensationType gravityCompensationType = GravityCompensationType.ELEVATOR_STATIC;
        public MotionProfilingConfig motionProfile = new MotionProfilingConfig();
        public SlotConfig slot0 = new SlotConfig();
        public SlotConfig slot1 = new SlotConfig();
        public SlotConfig slot2 = new SlotConfig();
    }

    public PID PID_Config = new PID();

    public static class OutputConfig {
        public enum OutputDirection {
            CounterClockWisePositive,
            ClockWisePositive
        }
        public OutputDirection outputDirection = OutputDirection.ClockWisePositive;

        public enum NeutralMode {
            Brake,
            Coast
        }
        public NeutralMode neutralMode = NeutralMode.Coast;
    }

    public OutputConfig outputConfig = new OutputConfig();

    public static class CurrentLimits {
        public int maxStator = 60;
        public int maxSupply = 40;
    }

    public CurrentLimits currentLimits = new CurrentLimits();

    public static class VoltageLimits {
        public double minVoltage = -12;
        public double maxVoltage = 12;
    }

    public VoltageLimits voltageLimits = new VoltageLimits();

    public static class FeedbackConfig {
        public enum FeedbackSource {
            InternalEncoder,
            ExternalEncoder, // Externally Connected Controller
            FusedEncoder,
            /**USED for having TalonFX and FXS read from a CANCODER Only, and maybe Nitrate */
            CanEncoder
        }
        public int encoderId = 0;
        public Encoder fusedEncoder = null;

        public void setFusedFeedbackSource(Encoder encoder) {
            this.fusedEncoder = encoder;
            this.feedbackSource = FeedbackSource.FusedEncoder;
        }

        public void setExternalCanSource(int encoderId) {
            this.feedbackSource = FeedbackSource.CanEncoder;
            this.encoderId = encoderId;
        }

        public void resetEncoderSettings() {
            this.fusedEncoder = null;
            this.feedbackSource = FeedbackSource.InternalEncoder;
            this.encoderId = 0;
        }

        public FeedbackSource feedbackSource = FeedbackSource.InternalEncoder;
        public boolean continousWrap = false;

        /** The Gear ratio of sensor input to Mechanism output.
         * For most swerve drives this is 1 for other mechanisms please check your cad or the manufacture's cad for that mechanism.
         */
        public double sensorToMechanismRatio = 1.0;
        
        /** The Gear ratio of motor to Mechanism output
         * You must use CAD for this. 
         */
        public double motorToMechanismRation =  1.0; // If used on an internal encoder this does effectively the same as sensorToMechanism Ration
    }
    public FeedbackConfig feedback = new FeedbackConfig();
    public double simMoi = 0.0035;
}
