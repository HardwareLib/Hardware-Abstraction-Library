package simple.lib.template.hardwareInterface;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import simple.lib.controls.Control;
import simple.lib.logging.data.MotorData;
import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorInterface;

import static edu.wpi.first.units.Units.*;

public class SimMotor implements MotorInterface {
    private final PIDController pid;
    private final SimpleMotorFeedforward ff;
    private final ProfiledPIDController profiledPid;
    private Control activeControl = null;

    private MotorConfig config;
    private MotorConfig.PID.SlotConfig currentSlot;

    private final DCMotorSim sim;

    public SimMotor(int id, MotorConfig config, DCMotor motor) {
        pid = new PIDController(config.PID_Config.slot0.kP,config.PID_Config.slot0.kI,config.PID_Config.slot0.kD, 0.02);
        ff = new SimpleMotorFeedforward(config.PID_Config.slot0.kS,config.PID_Config.slot0.kV,config.PID_Config.slot0.kA);
        profiledPid = new ProfiledPIDController(config.PID_Config.slot0.kP,config.PID_Config.slot0.kI,config.PID_Config.slot0.kD, new TrapezoidProfile.Constraints(config.PID_Config.motionProfile.maxVelocity,config.PID_Config.motionProfile.maxAcceleration),0.02);
        this.config = config;
        sim = new DCMotorSim(LinearSystemId.createDCMotorSystem(motor,0.035,config.outputConfig.sensorToMechanismRatio),motor);
    }

    @Override
    public void periodic() {
        double kG;
        if (this.activeControl != null) {
            switch (activeControl.type) {
                case Voltage:
                    sim.setInputVoltage(MathUtil.clamp(activeControl.output,-12.0,12.0));
                    break;
                case DutyCycle:
                    sim.setInputVoltage(MathUtil.clamp(activeControl.output,-1.0,1.0)*12.0);
                    break;
                case Position:
                    kG = currentSlot.kG;
                    switch (config.PID_Config.gravityCompensationType) {
                        case ARM_SINE:
                            kG *= Math.sin(sim.getAngularPositionRad());
                            break;
                        case ARM_COSINE:
                            kG *= Math.cos(sim.getAngularPositionRad());
                            break;
                        default:
                            break;
                    }
                    sim.setInputVoltage(pid.calculate(sim.getAngularPositionRad()) + ff.calculate(0.0) + kG);
                    break;
                case Velocity:
                    kG = currentSlot.kG;
                    switch (config.PID_Config.gravityCompensationType) {
                        case ARM_SINE:
                            kG *= Math.sin(sim.getAngularPositionRad());
                            break;
                        case ARM_COSINE:
                            kG *= Math.cos(sim.getAngularPositionRad());
                            break;
                        default:
                            break;
                    }
                    sim.setInputVoltage(pid.calculate(sim.getAngularVelocityRadPerSec()) + ff.calculate(activeControl.output) + kG);
                    break;
                case VelocityProfiled:
                    break;
                case PositionProfiled:
                    break;
            }
        }
        sim.update(0.020);
        SmartDashboard.putData(pid);
    }

    /**
     * @param data the data object you are updating values for
     */
    @Override
    public void getData(MotorData data) {
        data.alive.update(true);
        data.connected.update(true);
        data.temperature.update(Celsius.zero());
        data.acceleration.update(sim.getAngularAcceleration());
        data.velocity.update(sim.getAngularVelocity());
        data.position.update(sim.getAngularPosition());
        data.torqueCurrent.update(Amps.of(sim.getCurrentDrawAmps()));
        data.statorCurrent.update(Amps.of(sim.getCurrentDrawAmps()));
        data.supplyCurrent.update(Amps.of(sim.getCurrentDrawAmps()));
        data.output.update(sim.getOutput(0));
        data.voltage.update(Volts.of(sim.getInputVoltage()));
    }

    /**
     * @param config
     */
    @Override
    public void configure(MotorConfig config) {
        this.config = config;
    }

    /**
     * @param config
     * @param slot
     */
    @Override
    public void configPID(MotorConfig.PID.SlotConfig config, int slot) {
        switch (slot) {
            case 1:
                this.config.PID_Config.slot1 = config;
                break;
            case 2:
                this.config.PID_Config.slot2 = config;
                break;
            default:
                this.config.PID_Config.slot0 = config;
                break;
        }
        configure(this.config);
    }

    /**
     * @param control
     */
    @Override
    public void setControl(Control control) {
        this.activeControl = control;
        switch (control.type) {
            case Position, Velocity:
                pid.setSetpoint(control.output);
                break;
            case PositionProfiled:
                profiledPid.setGoal(control.output);
                break;
            case VelocityProfiled:
                profiledPid.setGoal(new TrapezoidProfile.State(0.0,control.output));
                break;
            default:
                break;
        }

        switch (control.slot) {
            case 0:
                currentSlot = config.PID_Config.slot0;
                break;
            case 1:
                currentSlot = config.PID_Config.slot1;
                break;
            case 2:
                currentSlot = config.PID_Config.slot2;
                break;
            default:
                break;
        }
        pid.setPID(currentSlot.kP,currentSlot.kI,currentSlot.kD);
        ff.setKv(currentSlot.kV);
        ff.setKa(currentSlot.kA);
        ff.setKs(currentSlot.kS);
        profiledPid.setPID(currentSlot.kP,currentSlot.kI,currentSlot.kD);
    }

    /**
     *
     */
    @Override
    public void stop() {
        this.activeControl = null;
    }
}
