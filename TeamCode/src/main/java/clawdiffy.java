import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;

import java.util.List;
import java.util.Map;

public class clawdiffy extends Subsystem {
    public static final clawdiffy INSTANCE = new clawdiffy();
    private clawdiffy(){ }
    public Servo IntakeDiffyLeft;
    public Servo IntakeDiffyRight;
    public String name1 = "leftdiffyservo";
    public String name2 = "rightdiffyservo";


    //Setting Diffy Position to Up, Down and Middle
    public Command diffyup(){
        return new MultipleServosToPosition(
                List.of(
                        IntakeDiffyLeft,
                        IntakeDiffyRight
                ),
                1.0,
                this
        );
    }
    public Command diffydown(){
        return new MultipleServosToPosition(
                List.of(
                        IntakeDiffyLeft,
                        IntakeDiffyRight
                ),
                0.0,
                this
        );
    }
    public Command diffymiddle(){
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
    public Command turndiffyclockwise(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.99,
                        IntakeDiffyRight, 0.01
                ),
                this
        );
    }

    public Command turndiffyanticlockwise(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.01,
                        IntakeDiffyRight, 0.99
                ),
                this
        );
    }
    public Command turndiffycentre(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft, 0.5,
                        IntakeDiffyRight, 0.5
                ),
                this
        );
    }
    public Command rotateclockwise() {
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft,IntakeDiffyLeft.getPosition()+0.03,
                        IntakeDiffyRight,IntakeDiffyRight.getPosition()-0.03

                ),
                this
        );
    }
    public Command rotateanticlockwise() {
        return new MultipleServosToSeperatePositions(
                Map.of(
                        IntakeDiffyLeft,IntakeDiffyLeft.getPosition()-0.03,
                        IntakeDiffyRight,IntakeDiffyRight.getPosition()+0.03

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
