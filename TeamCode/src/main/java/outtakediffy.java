import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;

import java.util.List;
import java.util.Map;

public class outtakediffy extends Subsystem {
    public static final outtakediffy INSTANCE = new outtakediffy();
    private outtakediffy(){ }
    public Servo OuttakeDiffyLeft;
    public Servo OuttakeDiffyRight;
    public String name1 = "LeftOuttakeDiffy";
    public String name2 = "RightOuttakeDiffy";


    public Command outtakediffyup(){
        return new MultipleServosToPosition(
                List.of(
                        OuttakeDiffyLeft,
                        OuttakeDiffyRight
                ),
                1.0,
                this
        );
    }
    public Command outtakediffydown(){
        return new MultipleServosToPosition(
                List.of(
                        OuttakeDiffyLeft,
                        OuttakeDiffyRight
                ),
                0.0,
                this
        );
    }
    public Command outtakediffymiddle(){
        return new MultipleServosToPosition(
                List.of(
                        OuttakeDiffyLeft,
                        OuttakeDiffyRight
                ),
                0.5,
                this
        );
    }

    public Command outtakediffyleft(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        OuttakeDiffyLeft, 0.99,
                        OuttakeDiffyRight, 0.01
                ),
                this
        );
    }
    public Command outtakediffyright(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        OuttakeDiffyLeft, 0.01,
                        OuttakeDiffyRight, 0.99
                ),
                this
        );
    }
    public Command outtakediffycentre(){
        return new MultipleServosToSeperatePositions(
                Map.of(
                        OuttakeDiffyLeft, 0.5,
                        OuttakeDiffyRight, 0.5
                ),
                this
        );
    }




}
