import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;

import java.util.List;
import java.util.Map;

public class intakediffy extends Subsystem {
    public static final intakediffy INSTANCE = new intakediffy();
    private intakediffy(){ }
    public Servo IntakeDiffyLeft;
    public Servo IntakeDiffyRight;
    public String name1 = "LeftIntakeDiffy";
    public String name2 = "RightIntakeDiffy";


    //Setting Diffy Position to Up, Down and Middle
    public Command intakediffyup(){
        return new MultipleServosToPosition(
                List.of(
                        IntakeDiffyLeft,
                        IntakeDiffyRight
                ),
                1.0,
                this
        );
    }
    public Command intakediffydown(){
        return new MultipleServosToPosition(
                List.of(
                        IntakeDiffyLeft,
                        IntakeDiffyRight
                ),
                0.0,
                this
        );
    }
    public Command intakediffymiddle(){
        return new MultipleServosToPosition(
                List.of(
                        IntakeDiffyLeft,
                        IntakeDiffyRight
                ),
                0.5,
                this
        );
    }
    //Rotating Diffy Position to Right, Left, and Centred
    public Command intakediffyleft(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.99,
                        IntakeDiffyRight, 0.01
                ),
                this
        );
    }

    public Command intakediffyright(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.01,
                        IntakeDiffyRight, 0.99
                ),
                this
        );
    }
    public Command intakediffycentre(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.5,
                        IntakeDiffyRight, 0.5
                ),
                this
        );
    }

    //Initializing diffy servos
    @Override
    public void initialize(){
        IntakeDiffyLeft = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name1);
        IntakeDiffyRight = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name2);
        IntakeDiffyRight.setDirection(Servo.Direction.REVERSE);
    }
}
