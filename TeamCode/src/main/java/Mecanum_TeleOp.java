import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.DriverControlled;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

@TeleOp(name = "Mecanum_Teleop")
class Mecanum_TeleOp extends PedroOpMode {

    public Mecanum_TeleOp() {
        super(lift.INSTANCE,intakearm.INSTANCE,intakeclaw.INSTANCE,outtakeclaw.INSTANCE,intakediffy.INSTANCE,outtakediffy.INSTANCE);
    }

    public String leftFrontName = "leftFront";
    public String rightFrontName = "rightFront";
    public String leftRearName = "leftRear";
    public String rightRearName = "rightRear";

    public MotorEx leftFrontMotor;
    public MotorEx rightFrontMotor;
    public MotorEx leftRearMotor;
    public MotorEx rightRearMotor;

    public MotorEx[] motors;

    public Command driverControlled;

    @Override
    public void onInit(){
        leftFrontMotor = new MotorEx(leftFrontName);
        rightFrontMotor = new MotorEx(rightFrontName);
        leftRearMotor = new MotorEx(leftRearName);
        rightRearMotor = new MotorEx(rightRearName);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[] {leftFrontMotor,rightFrontMotor,leftRearMotor,rightRearMotor};
    }

    @Override
    public void onStartButtonPressed(){
        driverControlled = new MecanumDriverControlled(motors,gamepadManager.getGamepad1());
        CommandManager.INSTANCE.scheduleCommand(new DriverControlled(gamepadManager.getGamepad1(), true));
        driverControlled.invoke();

        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value-> new ParallelGroup(
                        intakearm.INSTANCE.armextend(),
                        intakediffy.INSTANCE.intakediffydown(),
                        intakeclaw.INSTANCE.intakeclawopen()
                )
        );
        gamepadManager.getGamepad2().getRightTrigger().setReleasedCommand(
                value-> new SequentialGroup(
                        intakeclaw.INSTANCE.intakeclawclose(),
                        new Delay(0.25),
                        new ParallelGroup(
                                intakearm.INSTANCE.armretract(),
                                intakediffy.INSTANCE.intakediffyup())
                        )
        );

        gamepadManager.getGamepad2().getLeftTrigger().setPressedCommand(
                value-> new SequentialGroup(
                        outtakeclaw.INSTANCE.outtakeclawclose(),
                        new Delay(0.2),
                        intakeclaw.INSTANCE.intakeclawopen(),
                        new ParallelGroup(
                                intakediffy.INSTANCE.intakediffymiddle(),
                                outtakediffy.INSTANCE.outtakediffydown()
                        )
                )
        );

        gamepadManager.getGamepad2().getLeftTrigger().setReleasedCommand(
                value-> new ParallelGroup(
                        lift.INSTANCE.lifthigh(),
                        intakearm.INSTANCE.armextend()
                )
        );
        gamepadManager.getGamepad2().getA().setPressedCommand(outtakeclaw.INSTANCE::outtakeclawopen);

        gamepadManager.getGamepad2().getDpadRight().setHeldCommand(intakediffy.INSTANCE::rotateright);

        gamepadManager.getGamepad2().getDpadLeft().setHeldCommand(intakediffy.INSTANCE::rotateleft);

        gamepadManager.getGamepad2().getY().setPressedCommand(outtakeclaw.INSTANCE::outtakeclawopen);

        gamepadManager.getGamepad2().getRightBumper().setPressedCommand(
                ()-> new SequentialGroup(
                        lift.INSTANCE.liftlow(),
                        new Delay(0.5),
                        outtakeclaw.INSTANCE.outtakeclawclose()
                )
        );
        gamepadManager.getGamepad2().getRightBumper().setReleasedCommand(lift.INSTANCE::lifthigh);

        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                ()-> new ParallelGroup(
                        lift.INSTANCE.liftmiddle(),
                        new SequentialGroup(
                                new Delay(0.2),
                                outtakeclaw.INSTANCE.outtakeclawopen()
                        )
                )
        );

        gamepadManager.getGamepad2().getLeftBumper().setReleasedCommand(outtakediffy.INSTANCE::outtakediffyup);
    }
}
