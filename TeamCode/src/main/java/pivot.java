import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class pivot extends Subsystem {

    public static final pivot INSTANCE = new pivot();
    private pivot(){ }

    public MotorEx pivotleft;
    public MotorEx pivotright;
    public MotorGroup pivotmotors;

    public PIDFController ArmPID = new PIDFController(0.005,0.0,0.0,new StaticFeedforward(0.0));
    public String name1 = "leftarm";
    public String name2 = "rightarm";

    public Command pivotup(){
        return new RunToPosition(pivotmotors,
                697,
                ArmPID,
                this);
    }
    public Command pivotdown(){
        return new RunToPosition(pivotmotors,
                0,
                ArmPID,
                this);
    }

    public Command pivotmiddle(){
        return new RunToPosition(pivotmotors,
                0,
                ArmPID,
                this);
    }

    @Override
    public void initialize(){
        pivotleft = new MotorEx(name1);
        pivotright = new MotorEx(name2).reverse();
        pivotmotors = new MotorGroup(pivotleft, pivotright);
    }

}
