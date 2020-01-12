package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutoBot", group="HGT")
@Disabled
public class AutoBot extends LinearOpMode {
    HyperBot           robot = new HyperBot();
    ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_ROTATION_HD_HEX_MOTOR = 1120;
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // 75mm
    static final double     COUNTS_PER_INCH = COUNTS_PER_ROTATION_HD_HEX_MOTOR / (WHEEL_DIAMETER_INCHES * 3.1415);

    static final int FORWARD = 1;
    static final int BACK = 2;
    static final int RIGHT = 3;
    static final int LEFT = 4;

    @Override
    public void runOpMode() {
        // do nothing here. All classes that extends AutoBot need to override this method
    }

    public void setup() {
        robot.init(hardwareMap);

        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void move(HyperBot robot, double speed, double inches, int timeoutS, int direction) {
        moveInternal(robot, speed, inches, timeoutS, direction, -1);
    }

    public void moveWithDistanceSensor(HyperBot robot, double speed, double inches, int timeoutS, int direction, double minDistance) {
        moveInternal(robot, speed, inches, timeoutS, direction, minDistance);
    }

    private void moveInternal(HyperBot robot, double speed, double inches, int timeoutS, int direction, double minDistance) {
        int frontLeftTarget = 0;
        int frontRightTarget = 0;
        int backLeftTarget = 0;
        int backRightTarget = 0;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            if (direction == FORWARD) {
                // Determine new target position, and pass to motor controller
                frontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
                frontRightTarget = robot.frontRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
                backLeftTarget = robot.backLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
                backRightTarget = robot.backRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            } else if (direction == BACK) {
                // Determine new target position, and pass to motor controller
                frontLeftTarget = robot.frontLeft.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                frontRightTarget = robot.frontRight.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                backLeftTarget = robot.backLeft.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                backRightTarget = robot.backRight.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
            } else if (direction == RIGHT) {
                // Determine new target position, and pass to motor controller
                frontLeftTarget = robot.frontLeft.getCurrentPosition() +(int) (inches * COUNTS_PER_INCH);
                frontRightTarget = robot.frontRight.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                backLeftTarget = robot.backLeft.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                backRightTarget = robot.backRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            } else if (direction == LEFT) {
                // Determine new target position, and pass to motor controller
                frontLeftTarget = robot.frontLeft.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
                frontRightTarget = robot.frontRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
                backLeftTarget = robot.backLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
                backRightTarget = robot.backRight.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
            }

            robot.frontLeft.setTargetPosition(frontLeftTarget);
            robot.frontRight.setTargetPosition(frontRightTarget);
            robot.backLeft.setTargetPosition(backLeftTarget);
            robot.backRight.setTargetPosition(backRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.frontRight.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            if (minDistance > 0) {
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        robot.distanceSensor.getDistance(DistanceUnit.INCH) > minDistance) {
                    sleep(50);
                }
            } else {
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {
                    sleep(50);
                }
            }

            // Stop all motion;
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void openClaw (HyperBot robot) {
        robot.clawServo.setPosition(0);
    }

    public void closeClaw (HyperBot robot) { robot.clawServo.setPosition(1); }

    public void closeFClaw (HyperBot robot) {
        robot.leftServo.setPosition(0);
        robot.rightServo.setPosition(0);
    }
    public void openFClaw (HyperBot robot) {
        robot.leftServo.setPosition(1);
        robot.rightServo.setPosition(1);
    }

    public boolean isBlackStone(HyperBot robot) {
        return robot.colorSensor.red() < 60;
    }

}
