package simple.lib.motor.util;

import simple.lib.encoder.Encoder;

public class MotorConfig {
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

        public enum FeedbackSource {
            InternalEncoder,
            ExternalEncoder, // Externally Connected Controller
            FusedController
        }

        public enum GravityCompensationType {
            /** Static constant kG that is frequently used in Elevator mechanisms */
            ELEVATOR_STATIC,
            /** kG that is calculated by taking the cosine of the current Angle, it is frequently used in Arm mechanisms */
            ARM_COSINE,
            /** kG that is calculated by taking the sine of the current Angle, it is frequently used in Arm mechanisms */
            ARM_SINE
        }

        public FeedbackSource feedbackSource = FeedbackSource.InternalEncoder;
        public Encoder externalEncoder = null;

        public GravityCompensationType gravityCompensationType = GravityCompensationType.ELEVATOR_STATIC;
        public MotionProfilingConfig motionProfile = new MotionProfilingConfig();
        public SlotConfig slot0 = new SlotConfig();
        public SlotConfig slot1 = new SlotConfig();
        public SlotConfig slot2 = new SlotConfig();
    }

    public PID PID_Config = new PID();

    public static class OutputConfig {
        public double sensorToMechanismRatio = 1.0;
        public enum OutputDirection {
            CounterClockWisePositive,
            ClockWisePositive
        }
        public OutputDirection outputDirection = OutputDirection.ClockWisePositive;
    }

    public OutputConfig outputConfig = new OutputConfig();
}
