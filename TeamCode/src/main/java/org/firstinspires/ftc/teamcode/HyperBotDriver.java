package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="HyperBotDriver", group="HGT")
public class HyperBotDriver extends LinearOpMode {
    //has motor and servo info
    HyperBot robot = new HyperBot();

    private static final double CLAW_SPEED = 0.25;  // sets rate to move servo

    double sidewayRightX      = 0;
    double forwardY           = 0;
    double turn               = 0;

    double frontLeftPower     = 0;
    double frontRightPower    = 0;
    double backLeftPower      = 0;
    double backRightPower     = 0;

    int linearExtensionHeight = 0;
    int layer                 = 0;
    int x                     = 0;
    int MAX_LINEAR_POSITION   = 1000;

    double leftServo          = 0;
    /*boolean leftTop   = false;
    boolean rightTop    = false;
    boolean leftBottom  = false;
    boolean rightBottom = false;
    double layer = 1;*/

    double clawOffset = 1;

    @Override
    public void runOpMode() {
        //robot config
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
//        x = robot.linearDrive.getCurrentPosition();
        //robot.linearDrive.setTargetPosition(x);
        int layer1 = 104 + x;
        int layer2 = 295 + x;
        int layer3 = 440 + x;
        int layer4 = 600 + x;
        int layer5 = 750 + x;
        int layer6 = 910 + x;
        int layer7 = 1100 + x;
        int targetPosition;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //linear extension
            linearExtensionHeight = robot.linearDrive.getCurrentPosition();
//            if (linearExtensionHeight <= x + 500 && linearExtensionHeight >= x + 0) { // 1040
                    robot.linearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    double linearPower = gamepad2.right_stick_y * -0.5;

                    if (linearPower > 0) {
                        if ( linearExtensionHeight < MAX_LINEAR_POSITION) {
                            robot.linearDrive.setPower(linearPower);
                        }

                    } else if (linearPower <0) {
                        if ( linearExtensionHeight > 0) {
                            robot.linearDrive.setPower(linearPower) ;
                        }
                    } else {
                        robot.linearDrive.setPower(0);
                    }
//                    if (0 < linearExtensionHeight || linearExtensionHeight <  500) {
//                        if (linearPower > 0 && linearExtensionHeight < 500) {
//                            robot.linearDrive.setPower(linearPower);
//                        } else if (linearPower > 0 && linearExtensionHeight > 500) {
//                            robot.linearDrive.setPower(0);
//                        }
//                        if (linearPower < 0 && linearExtensionHeight > 0) {
//                            robot.linearDrive.setPower(linearPower);
//                        } else if (linearPower < 0 && linearExtensionHeight < 0) {
//                            robot.linearDrive.setPower(0);
//                        }
//                    } else {
//                        if (linearPower == 0 || linearExtensionHeight > 500 || linearExtensionHeight < x) {
//                            robot.linearDrive.setPower(0);
//                        }
//                    }
            /*if(gamepad}                                                                                  2.right_bumper) {
                targetPosition = robot.linearDrive.getTargetPosition();
                if (targetPosition == x){
                    robot.linearDrive.setTargetPosition(layer1);
                    if (robot.linearDrive.getCurrentPosition() < layer1) {
                        robot.linearDrive.setPower(0.5);
                    }
                }
                if (targetPosition == layer1){
                    robot.linearDrive.setTargetPosition(layer2);
                    if (robot.linearDrive.getCurrentPosition() < layer2) {
                        robot.linearDrive.setPower(0.5);
                    }
                }
            }


            if(gamepad2.left_bumper) {
                robot.linearDrive.setTargetPosition(x);
                if (robot.linearDrive.getCurrentPosition() > 0) {
                    robot.linearDrive.setPower(-0.5);

                }
            }*/

            //driving robot
            sidewayRightX = gamepad1.left_stick_x; // left stick right(1)/left(-1)
            forwardY = gamepad1.left_stick_y * -1; // left stick forward(-1)/back(1)
            turn  =  gamepad1.right_stick_x;

            frontLeftPower   = Range.clip(forwardY + sidewayRightX + turn, -1.0, 1.0) ;
            frontRightPower  = Range.clip(forwardY - sidewayRightX - turn, -1.0, 1.0) ;
            backLeftPower    = Range.clip(forwardY - sidewayRightX + turn, -1.0, 1.0) ;
            backRightPower   = Range.clip(forwardY + sidewayRightX - turn, -1.0, 1.0) ;

            robot.frontLeft.setPower(frontLeftPower * Math.abs(frontLeftPower) * Math.abs(frontLeftPower));
            robot.frontRight.setPower(frontRightPower * Math.abs(frontRightPower) * Math.abs(frontRightPower));
            robot.backLeft.setPower(backLeftPower * Math.abs(backLeftPower) * Math.abs(backLeftPower));
            robot.backRight.setPower(backRightPower * Math.abs(backRightPower) * Math.abs(backRightPower));

            // the position 1 is when the claw is closed and 0 is open so to open the claw it subtracts
            if (gamepad2.dpad_up) {
                clawOffset -= CLAW_SPEED;
            } else if (gamepad2.dpad_down) {
                clawOffset += CLAW_SPEED;
            }
            clawOffset = Range.clip(clawOffset, 0, 1);
            robot.clawServo.setPosition(clawOffset);

            //foundation claw
            if (gamepad2.left_stick_y < -0.5) {
                robot.leftServo.setPosition(1);
            } else if (gamepad2.left_stick_y > 0.5) {
                robot.leftServo.setPosition(0);
            }
            if (gamepad2.left_stick_y < -0.5) {
                robot.rightServo.setPosition(0);
            } else if (gamepad2.left_stick_y > 0.5) {
                robot.rightServo.setPosition(1);
            }


//            if (leftTop) {
//                robot.linearDrive.setPower(0);
//            }

            telemetry.addData("X", "x:(%d)", x);
            telemetry.addData("Right stick", "stick_y:(%.2f)", gamepad2.right_stick_y);

//            //debugging
            telemetry.addData("Motors", "FL(%.2f), FR(%.2f), BL:(%.2f), BR:(%.2f), L:(%.2f), Linear:(%d)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower, linearPower, robot.linearDrive.getCurrentPosition());
            telemetry.addData("Servos", "Claw(%.2f)", clawOffset,
                    robot.clawServo.getPosition());

            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());

            telemetry.update();
        }
    }
}





































//if(int i
//
//

