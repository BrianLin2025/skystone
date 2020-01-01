package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutoBlueBuilding", group="HGT")
public class AutoBlueBuilding extends AutoBot {

    public boolean isBlueSide = true;

    @Override
    public void runOpMode() {
        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //open claw
        openClaw(robot);
        sleep(500);

        //move to foundation
        move(robot, 1, 31.5, 3, FORWARD);
        sleep(500);

        //grab foundation
        closeClaw(robot);
        sleep(1500);

        move(robot, 1, 1.5, 3, FORWARD);
        sleep(300);

        // pull foundation back
        move(robot, 0.4, 57.5, 5, BACK);
        sleep(800);

        //release foundation
        openClaw(robot);
        sleep(500);

        //move under bridge to stones
        int direction = 0;
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }

        //Go out from behind the foundation
        move(robot, 1, 30, 6, direction);
        sleep(500);

        //move forward so we won't bump into the other robot
        move(robot, 1, 24, 6, FORWARD);
        sleep(500);

        //go under the bridge
        move(robot, 1, 47, 6, direction);
        sleep(500);

        //drive to stones using distance sensor
        moveWithDistanceSensor(robot, 0.25, 8, 3, FORWARD, 1.5);
        sleep(200);

        double inchesToMoveBack = 36;

        for(int i = 0; i < 5; i++) {
            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
            telemetry.update();

            if(!isBlackStone(robot)) {
                move(robot, 1, 8, 6, direction);
                sleep(200);
                inchesToMoveBack += 8; // every time we move away from bridge, we need to move more back
            }
        }

        //grab stone
        closeClaw(robot);
        sleep(500);

        //move back
        move(robot, 1, 6, 3, BACK);
        sleep(500);

        //move to deliver stone
        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 1, inchesToMoveBack, 4, direction);
        sleep(500);

        //deliver stone
        openClaw(robot);
        sleep(500);

        //park
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, 12, 2, direction);
    }

}
