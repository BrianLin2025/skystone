package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeslaTurtle", group="HGT")
public class TeslaTurtle extends LinearOpMode {
    Robot robot = new Robot();

    private static final double CLAW_SPEED = 0.01;  // sets rate to move servo

    double clawOffset1      = 0;    // Servo mid position
    double clawOffset2      = 0;
    double clawOffset3      = 0;
    double drive            = 0;
    double turn             = 0;
    double leftPower        = 0;
    double rightPower       = 0;
    double linearPower      = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // linear extension
            if (gamepad2.dpad_up) {
                linearPower = 0.5;
            } else if (gamepad2.dpad_down) {
                linearPower = -0.5;
            } else {
                linearPower = 0;
            }
            robot.linearDrive.setPower(linearPower);

            //driving robot
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive - turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive + turn, -1.0, 1.0) ;
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);

            // Use gamepad left & right Bumpers to open and close the claw

            //servo1 = brick claw
            if (gamepad2.right_bumper) {
                clawOffset1 += CLAW_SPEED;
            } else if (gamepad2.left_bumper) {
                clawOffset1 -= CLAW_SPEED;
            }
            clawOffset1 = Range.clip(clawOffset1, 0, 1);
            robot.servo1.setPosition(clawOffset1);

            //servo2 = foundation claw
            if (gamepad2.left_stick_x == 1) {
                clawOffset2 += CLAW_SPEED;
            } else if (gamepad2.left_stick_x == -1) {
                clawOffset2 -= CLAW_SPEED;
            }

            clawOffset2 = Range.clip(clawOffset2, 0, 1);
            robot.servo2.setPosition(clawOffset2);

            //servo3 = servo that rotates whole claw
            if (gamepad2.right_stick_x == 1) {
                clawOffset3 += CLAW_SPEED;
            } else if (gamepad2.right_stick_x == -1) {
                clawOffset3 -= CLAW_SPEED;
            }

            clawOffset3 = Range.clip(clawOffset3, 0, 1);
            robot.servo3.setPosition(clawOffset3);

            telemetry.addData("Servo", "%5.2f, %5.2f, %5.2f",
                    clawOffset1, clawOffset2, clawOffset3);
            telemetry.addData("Motors", "left (%.2f), right (%.2f), linear: (%.2f)",
                    leftPower, rightPower, linearPower);
            telemetry.update();
        }
    }
}

