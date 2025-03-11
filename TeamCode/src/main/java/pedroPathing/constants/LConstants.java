package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        DriveEncoderConstants.forwardTicksToInches = 0.004733068;
            DriveEncoderConstants.strafeTicksToInches = 0.005395883964840191;
        DriveEncoderConstants.turnTicksToInches = 0.009963971141996257;

        DriveEncoderConstants.robot_Width = 13.5;
        DriveEncoderConstants.robot_Length = 12.75;


        DriveEncoderConstants.leftFrontEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightFrontEncoderDirection = Encoder.FORWARD;
        DriveEncoderConstants.leftRearEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightRearEncoderDirection = Encoder.FORWARD;
    }
}




