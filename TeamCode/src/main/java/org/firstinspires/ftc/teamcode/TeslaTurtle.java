package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeslaTurtle", group="Linear Opmode")
//@Disabled
public class TeslaTurtle extends LinearOpMode {
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    DcMotor linearDrive = null;
    Servo servo1 = null;
    Servo servo2 = null;
    Servo servo3 = null;

    double          clawOffset1      = 0;                       // Servo mid position
    double          clawOffset2      = 0;
    double          clawOffset3      = 0;
    final double    CLAW_SPEED      = 0.01;                    // sets rate to move servo

    @Override
    public void runOpMode() {
        double drive;
        double turn;

        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        linearDrive = hardwareMap.get(DcMotor.class, "linearDrive");

        servo1  = hardwareMap.get(Servo.class, "servo1");
        servo2  = hardwareMap.get(Servo.class, "servo2");
        servo3  = hardwareMap.get(Servo.class, "servo3");

        servo1.setPosition(0);
        servo2.setPosition(0);
        servo3.setPosition(0);

        double leftPower;
        double rightPower;
        double linearPower;

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        linearDrive.setDirection(DcMotor.Direction.FORWARD);

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
            linearDrive.setPower(linearPower);

            //driving robot
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive - turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive + turn, -1.0, 1.0) ;
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            // Use gamepad left & right Bumpers to open and close the claw

            //servo1 = brick claw
            if (gamepad2.right_bumper) {
                clawOffset1 += CLAW_SPEED;
            } else if (gamepad2.left_bumper) {
                clawOffset1 -= CLAW_SPEED;
            }
            clawOffset1 = Range.clip(clawOffset1, 0, 1);
            servo1.setPosition(clawOffset1);

            //servo2 = foundation claw
            if (gamepad2.left_stick_x == 1) {
                clawOffset2 += CLAW_SPEED;
            } else if (gamepad2.left_stick_x == -1) {
                clawOffset2 -= CLAW_SPEED;
            }

            clawOffset2 = Range.clip(clawOffset2, 0, 1);
            servo2.setPosition(clawOffset2);

            //servo3 = servo that rotates whole claw
            if (gamepad2.right_stick_x == 1) {
                clawOffset3 += CLAW_SPEED;
            } else if (gamepad2.right_stick_x == -1) {
                clawOffset3 -= CLAW_SPEED;
            }

            clawOffset3 = Range.clip(clawOffset3, 0, 1);
            servo3.setPosition(clawOffset3);

            telemetry.addData("Servo", "%5.2f, %5.2f, %5.2f", servo1.getPosition(), servo2.getPosition(), servo3.getPosition());
            telemetry.addData("Motors", "left (%.2f), right (%.2f), linear: (%.2f)", leftPower, rightPower, linearPower);
            telemetry.update();
        }
    }
}

