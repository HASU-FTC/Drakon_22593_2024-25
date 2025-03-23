import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class intakeclaw extends Subsystem {
    public static final intakeclaw INSTANCE = new intakeclaw();
    private intakeclaw(){ }
    public Servo IntakeClawServo;
    public String name = "intakeclaw";

    public Command intakeclawopen(){
        return new ServoToPosition(IntakeClawServo,
                0.9,
                this);
    }
    public Command intakeclawclose(){
        return new ServoToPosition(IntakeClawServo,
                0.1,
                this);
    }

    @Override
    public void initialize(){
        IntakeClawServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
    }
}
