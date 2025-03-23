import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class lift extends Subsystem{

    public static final lift INSTANCE = new lift();
    private lift() { }

    public MotorEx motorleft;
    public MotorEx motorright;
    public MotorGroup motors;

    public PIDFController LiftPID = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));
    public String name1 = "leftlift";
    public String name2 = "rightlift";

    public Command liftlow(){
        return new RunToPosition(motors,
                0.0,
                LiftPID,
                this);
    }
    public Command liftmiddle(){
        return new RunToPosition(motors,
                500.0,
                LiftPID,
                this);
    }
    public Command lifthigh(){
        return new RunToPosition(motors,
                1000.0,
                LiftPID,
                this);
    }
    @Override
    public void initialize(){
        motorleft = new MotorEx(name1);
        motorright = new MotorEx(name2);
        motors = new MotorGroup(motorleft,motorright);
    }
}
