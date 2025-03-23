import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class outtakeclaw extends Subsystem {
    public static final outtakeclaw INSTANCE = new outtakeclaw();
    private outtakeclaw(){ }
    public Servo OuttakeClawServo;
    public String name = "outtakeclaw";

    public Command outtakeclawopen(){
        return new ServoToPosition(OuttakeClawServo,
                0.9,
                this);
    }
    public Command outtakeclawclose(){
        return new ServoToPosition(OuttakeClawServo,
                0.2,
                this);
    }

    @Override
    public void initialize(){
        OuttakeClawServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,name);
    }
}
