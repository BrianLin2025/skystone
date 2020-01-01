package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="HyperBotDriver", group="HGT")
public class HyperBotDriver extends LinearOpMode {
    //has motor and servo info
    HyperBot robot = new HyperBot();

    private static final double CLAW_SPEED = 0.1;  // sets rate to move servo

    double sidewayRightX         = 0;
    double forwardY         = 0;
    double turn             = 0;

    double frontLeftPower   = 0;
    double frontRightPower  = 0;
    double backLeftPower    = 0;
    double backRightPower   = 0;

    double clawOffset = 1;

    @Override
    public void runOpMode() {
        //robot config
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double linearPower = gamepad2.left_stick_y * -1;
            robot.linearServo.setPower(linearPower);

            //driving robot
            sidewayRightX = gamepad1.left_stick_x; // left stick right(1)/left(-1)
            forwardY = gamepad1.left_stick_y * -1; // left stick forward(-1)/back(1)
            turn  =  gamepad1.right_stick_x;

            frontLeftPower   = Range.clip(forwardY + sidewayRightX + turn, -1.0, 1.0) ;
            frontRightPower  = Range.clip(forwardY - sidewayRightX - turn, -1.0, 1.0) ;
            backLeftPower    = Range.clip(forwardY - sidewayRightX + turn, -1.0, 1.0) ;
            backRightPower   = Range.clip(forwardY + sidewayRightX - turn, -1.0, 1.0) ;

            robot.frontLeft.setPower(frontLeftPower);
            robot.frontRight.setPower(frontRightPower);
            robot.backLeft.setPower(backLeftPower);
            robot.backRight.setPower(backRightPower);

            // the position 1 is when the claw is closed and 0 is open so to open the claw it subtracts
            if (gamepad2.dpad_up) {
                clawOffset -= CLAW_SPEED;
            } else if (gamepad2.dpad_down) {
                clawOffset += CLAW_SPEED;
            }
            clawOffset = Range.clip(clawOffset, 0, 1);
            robot.clawServo.setPosition(clawOffset);

            //debugging
            telemetry.addData("Motors", "FL(%.2f), FR(%.2f), BL:(%.2f), BR:(%.2f)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.addData("Servos", "Claw(%.2f), Linear:(%.2f)",
                    clawOffset, linearPower);

            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());

            telemetry.update();
        }
    }
}

