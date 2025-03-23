import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class intakearm extends Subsystem {

    public static final intakearm INSTANCE = new intakearm();
    private intakearm(){ }

    public MotorEx armleft;
    public MotorEx armright;
    public MotorGroup armmotors;

    public PIDFController ArmPID = new PIDFController(0.005,0.0,0.0,new StaticFeedforward(0.0));
    public String name1 = "leftarm";
    public String name2 = "rightarm";

    public Command armextend(){
        return new RunToPosition(armmotors,
                0.0,
                ArmPID,
                this);
    }
    public Command armretract(){
        return new RunToPosition(armmotors,
                88.0,
                ArmPID,
                this);
    }

    @Override
    public void initialize(){
        armleft = new MotorEx(name1);
        armright = new MotorEx(name2).reverse();
        armmotors = new MotorGroup(armleft,armright);
    }

}
