package simple.lib.motor;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import simple.lib.HardwareInterface;
import simple.lib.HardwareRunner;
import simple.lib.LibraryRegistry;
import simple.lib.LibraryRegistry.LibraryType;
import simple.lib.controls.Control;
import simple.lib.logging.data.MotorData;
import simple.lib.motor.util.MotorConfig;
import simple.lib.motor.util.MotorConfig.FeedbackConfig.FeedbackSource;
import simple.lib.motor.util.MotorInterface;
/**
 * A Simple Base class for motor abstraction.
 *
 *
 * @version 1.0
 */
public class Motor implements HardwareInterface {
    MotorInterface motorInterface;
    private boolean following = false;
    private boolean invertMaster = false;
    private Motor master;
    private MotorConfig config;
    private Control activeControl = new Control(0.0, Control.ControlType.Voltage);
    private int id = 0;
    public boolean periodicDataCollection = false;
    private final MotorData data = new MotorData();

    public enum MotorController {
        TalonFX,
        TalonFXS,
        TalonSRX,
        SparkMax,
        SparkFlex,
        Nitrate,
        Nova,
        Sim
    }

    /**
     * The basic class for all motor abstraction used by this library.
     * This version of the constructor is not recommended for SIM use because it limits what motors you can simulate and defaults to a Kraken X60FOC if you specify the Simulation as your motor controller
     * @param id the CAN id of the motor you are trying to create.
     * @param config the Configuration of the Motor you are trying to use with all the constants such as PID values, Current Limits, Voltage Limits, etc.
     * @param controller the Type of Motor Controller that has the CAN ID of the controller make sure to fill this field out correctly or the Library will not work or will not produce desirable results.
     * */
    public Motor(int id, MotorConfig config, MotorController controller) {
        this(id,config,controller, switch (controller) {
            case TalonFX -> DCMotor.getKrakenX60(1);
            case TalonFXS -> DCMotor.getMinion(1);
            case TalonSRX -> DCMotor.getCIM(1);
            case SparkMax -> DCMotor.getNEO(1);
            case SparkFlex -> DCMotor.getNeoVortex(1);
            case Nitrate -> new DCMotor((double) 12.0F,7.1,450.0,2.4, Units.rotationsPerMinuteToRadiansPerSecond(7140.0),1); // Temporary until they add Cu60 to DCMotor class
            case Nova -> new DCMotor((double) 12.0F,3.1,189.0,2.7, Units.rotationsPerMinuteToRadiansPerSecond(7500.0),1); // Temporary until Pulsar 775 gets added to DCMotor class
            case Sim -> DCMotor.getKrakenX60Foc(1); // Temporary Sim Motor DO NOT
        });
    }

    /**
     * The basic class for all motor abstraction used by this library.
     * @param id the CAN id of the motor you are trying to create.
     * @param config the Configuration of the Motor you are trying to use with all the constants such as PID values, Current Limits, Voltage Limits, etc.
     * @param controller the Type of Motor Controller that has the CAN ID of the controller make sure to fill this field out correctly or the Library will not work or will not produce desirable results.
     * @param motor the type of Motor you are trying to simulate if you are running in Sim.
     * */
    @SuppressWarnings("unchecked")
    public Motor(int id, MotorConfig config, MotorController controller, DCMotor motor) {
        this.config = config;
        this.id = id;
        Class<MotorInterface> interfaceClass;
        try {
            interfaceClass = (Class<MotorInterface>) ClassLoader.getSystemClassLoader().loadClass(LibraryRegistry.getOverrideOrDefault(LibraryType.Motor, controller.toString()));
            if (interfaceClass != null) {
                if (controller == MotorController.Sim) {
                    motorInterface = interfaceClass.getConstructor(int.class, MotorConfig.class, DCMotor.class).newInstance(id,config,motor);
                } else {
                    motorInterface = interfaceClass.getConstructor(int.class, MotorConfig.class).newInstance(id,config);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            switch (controller) {
                case TalonFX, TalonFXS:
                    throw new Error("Fatal Error: You must install both the Phoenix 6 Library, and the Unofficial Phoenix 6 Abstraction Library");
                case TalonSRX:
                    throw new Error("Fatal Error: You must install both the Phoenix 5 Library, and the Unofficial Phoenix 5 Abstraction Library");
                case SparkMax, SparkFlex:
                    throw new Error("Fatal Error: You must install both REVLib, and the Unofficial Rev Robotics Abstraction Library");
                case Nitrate:
                    throw new Error("Fatal Error: You must install both ReduxLib, and the Unofficial Redux Robotics Abstraction Library");
                case Nova:
                    throw new Error("Fatal Error: You must install both ThriftyLib, and the Unofficial Thrifty Robotics Abstraction Library");
                case Sim:
                    throw new Error("Fatal Error: Something has gone wrong please contact the owner of the library to help fix this.");
            }
        }
        configure(this.config);
        HardwareRunner.getInstance().registerInterface(this);
    }

    /**
     * Gets the Data from the motor controller in the form of a motor data object that you can use for logging
     * @return The Data from the Motor Controller */
    public MotorData getData() {
        if (!periodicDataCollection) {
            motorInterface.getData(data);
        }
        return data;
    }

    public void configureSlot(MotorConfig.PID.SlotConfig config, int slot) {
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

    public void configure(MotorConfig config) {
        this.config = config;
        if (motorInterface != null) {
            motorInterface.configure(config);
        } else {
           throw new RuntimeException("Failed to Configure Motor with id of "+this.id+" check if you are instantiating it correctly.");
        }
   }

   @Override
   public void periodic() {
        motorInterface.periodic();
       if (following) {
        Control control = master.getActiveControl();
        Control follow = (invertMaster ? control.copy().invert() : control);
        motorInterface.setControl(follow);
        this.activeControl = follow;
       }
       if (periodicDataCollection) {
            motorInterface.getData(data);
       }
       if (config.feedback.feedbackSource == FeedbackSource.FusedEncoder && config.feedback.fusedEncoder != null) {
           motorInterface.setPosition(config.feedback.fusedEncoder.getPosition());
       }
   }

   public MotorConfig getConfig() {
       return config;
   }

    //Following
    public void follow(Motor master, boolean invertMaster) {
        following = true;
        this.invertMaster = invertMaster;
        this.master = master;
    }

    public Control getActiveControl() {
        return activeControl;
    }

    public void setControl(Control control) {
        setControl(control,control.slot,true);
    }

   public void setControl(Control control, boolean overrideFollow) {
       setControl(control,control.slot,overrideFollow);
   }

   public void setControl(Control control, int slot) {
        setControl(control,slot,true);
   }

    public void setControl(Control control, int slot, boolean overrideFollow) {
        following = !overrideFollow;
        motorInterface.setControl(control.output,control.type, slot);
        control.slot = slot;
        this.activeControl = control;
    }

    /**
     * Sets the position of the internal or external encoder
     * @param position the new position
     */
    public void setPosition(Angle position) {
        motorInterface.setPosition(position);
    }

    public void stop() {
        motorInterface.stop();
    }
}