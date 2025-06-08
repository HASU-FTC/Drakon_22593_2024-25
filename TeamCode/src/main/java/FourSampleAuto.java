import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@Autonomous(name = "FourSampleAuto")
public class FourSampleAuto extends PedroOpMode{
    public FourSampleAuto(){
        super(intakeclaw.INSTANCE, pivot.INSTANCE, clawdiffy.INSTANCE, lift.INSTANCE);
    }

    //robot positions (field co-ordinates)

    private final Pose startpose = new Pose(9.6,84.24827586206897,Math.toRadians(0));
    private final Pose preloadscorepose = new Pose(17.213793103448275,126.28965517241379,Math.toRadians(-45));


    //Sample 2
    private final Pose samplepick2pose = new Pose(24.49655172413793,120.99310344827586,Math.toRadians(0));
    private final Pose samplescore2pose = new Pose(17.71034482758621,126.28965517241379,Math.toRadians(-45));

    //Sample 3
    private final Pose samplepick3pose = new Pose(24.66206896551724,131.0896551724138,Math.toRadians(0));
    private final Pose samplescore3pose = new Pose(17.544827586206896,126.28965517241379,Math.toRadians(-45));

    //Sample 4
    private final Pose samplepick4pose = new Pose(45.186206896551724,123.14482758620689,Math.toRadians(90));
    private final Pose samplescore4pose = new Pose(17.379310344827587,126.62068965517241,Math.toRadians(-45));

    //Park
    private final Pose parkpose = new Pose(59.08965517241379, 96.16551724137932, Math.toRadians(-90));


    //Paths initialisation
    private Path preload, park;
    private PathChain samplepick2, samplescore2;
    private PathChain samplepick3, samplescore3;
    private PathChain samplepick4, samplescore4;


    //Path definitions using co-ordinates + headings

    public void buildPaths(){

        //scoring preload (sample 1)
        preload = new Path(new BezierLine(new Point(startpose), new Point(preloadscorepose)));
        preload.setLinearHeadingInterpolation(startpose.getHeading(), preloadscorepose.getHeading());


        //scoring sample 2
        samplepick2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(preloadscorepose), new Point(samplepick2pose)))
                .setLinearHeadingInterpolation(preloadscorepose.getHeading(), samplepick2pose.getHeading())
                .build();
        samplescore2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplepick2pose), new Point(samplescore2pose)))
                .setLinearHeadingInterpolation(samplepick2pose.getHeading(), samplescore2pose.getHeading())
                .build();

        //scoring sample 3
        samplepick3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplescore2pose), new Point(samplepick3pose)))
                .setLinearHeadingInterpolation(samplescore2pose.getHeading(), samplepick3pose.getHeading())
                .build();
        samplescore2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplepick3pose), new Point(samplescore3pose)))
                .setLinearHeadingInterpolation(samplepick3pose.getHeading(), samplescore3pose.getHeading())
                .build();

        //scoring sample 4
        samplepick2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplescore3pose), new Point(samplepick4pose)))
                .setLinearHeadingInterpolation(samplescore3pose.getHeading(), samplepick4pose.getHeading())
                .build();
        samplescore2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(samplepick4pose), new Point(samplescore4pose)))
                .setLinearHeadingInterpolation(samplepick4pose.getHeading(), samplescore4pose.getHeading())
                .build();

        park = new Path(new BezierLine(new Point(samplescore4pose), new Point(parkpose)));
        park.setLinearHeadingInterpolation(samplescore4pose.getHeading(), parkpose.getHeading());
    }

    //Order of autonomous routine
    public Command sampleroutine(){
        return new SequentialGroup(

                //scoring preload
                new ParallelGroup(
                        new FollowPath(preload),
                        lift.INSTANCE.lifthigh(),
                        clawdiffy.INSTANCE.diffyup()
                ),
                new SequentialGroup(
                        new Delay(0.5),
                        intakeclaw.INSTANCE.intakeclawopen(),
                        new Delay(0.1),
                        new ParallelGroup(
                                lift.INSTANCE.liftlow(),
                                pivot.INSTANCE.pivotdown()
                        )
                ),


                //SCORING SAMPLE 2
                new SequentialGroup(
                        new ParallelGroup(
                                new FollowPath(samplepick2),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffydown(),
                                intakeclaw.INSTANCE.intakeclawopen()
                        ),
                        new Delay(0.5),

                        intakeclaw.INSTANCE.intakeclawclose(),

                        new ParallelGroup(
                                pivot.INSTANCE.pivotup(),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffyup()
                        ),

                        new Delay(0.1),

                        intakeclaw.INSTANCE.intakeclawopen(),

                        new SequentialGroup(
                                new Delay(0.5),
                                intakeclaw.INSTANCE.intakeclawopen(),
                                new Delay(0.1),
                                new ParallelGroup(
                                        lift.INSTANCE.liftlow(),
                                        pivot.INSTANCE.pivotdown()
                                )
                        )

                ),

                //SCORING SAMPLE 3
                new SequentialGroup(
                        new ParallelGroup(
                                new FollowPath(samplepick3),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffydown(),
                                intakeclaw.INSTANCE.intakeclawopen()
                        ),
                        new Delay(0.5),

                        intakeclaw.INSTANCE.intakeclawclose(),

                        new ParallelGroup(
                                pivot.INSTANCE.pivotup(),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffyup()
                        ),

                        new Delay(0.1),

                        intakeclaw.INSTANCE.intakeclawopen(),

                        new SequentialGroup(
                                new Delay(0.5),
                                intakeclaw.INSTANCE.intakeclawopen(),
                                new Delay(0.1),
                                new ParallelGroup(
                                        lift.INSTANCE.liftlow(),
                                        pivot.INSTANCE.pivotdown()
                                )
                        )

                ),

                //SCORING SAMPLE 4
                new SequentialGroup(
                        new ParallelGroup(
                                new FollowPath(samplepick3),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffydown(),
                                intakeclaw.INSTANCE.intakeclawopen()
                        ),
                        new Delay(0.5),

                        intakeclaw.INSTANCE.intakeclawclose(),

                        new ParallelGroup(
                                pivot.INSTANCE.pivotup(),
                                lift.INSTANCE.lifthigh(),
                                clawdiffy.INSTANCE.diffyup()
                        ),

                        new Delay(0.1),

                        intakeclaw.INSTANCE.intakeclawopen(),

                        new SequentialGroup(
                                new Delay(0.5),
                                intakeclaw.INSTANCE.intakeclawopen(),
                                new Delay(0.1),
                                new ParallelGroup(
                                        lift.INSTANCE.liftlow(),
                                        pivot.INSTANCE.pivotdown()
                                )
                        )
                ),

        //PARKING ROBOT

                new ParallelGroup(
                        new FollowPath(park),
                        pivot.INSTANCE.pivotmiddle(),
                        lift.INSTANCE.lifthigh()
                )
        );
    }

    @Override
    public void onInit(){
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startpose);
        buildPaths();
    }

    @Override
    public void onStartButtonPressed() {
        sampleroutine().invoke();
    }
}