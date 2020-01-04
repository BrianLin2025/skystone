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
        int direction = 0;
        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 1, 12, 3, direction);


        //move to foundation
        move(robot, 1, 34.5, 3, FORWARD);
        sleep(250);

        //grab foundation
        closeClaw(robot);
        sleep(1000);

        move(robot, 1, 2, 3, FORWARD);
        sleep(250);

        // pull foundation back
        move(robot, 0.245, 68, 9, BACK);
        sleep(250);

        //release foundation
        openClaw(robot);
        sleep(300);

        //Go out from behind the foundation
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 0.8, 30.5, 6, direction);
        sleep(250);

        //close the claw
        closeClaw(robot);

        //move forward so we won't bump into the other robot
        move(robot, 0.9, 18.5, 6, FORWARD);
        sleep(250);

        //go under the bridge
        move(robot, 0.75, 52, 6, direction);
        sleep(250);

        //0pen the claw
        openClaw(robot);
        sleep(500);

        //drive to stones using distance sensor
        moveWithDistanceSensor(robot, 0.25, 20, 8, FORWARD, 1.5);
        sleep(200);

        double inchesToMoveBack = 40;

        for(int i = 0; i < 5; i++) {
            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
            telemetry.update();

            if(!isBlackStone(robot)) {
                move(robot, 0.65 , 8.5, 6, direction);
                sleep(200);
                inchesToMoveBack += 8.75; // every time we move away from bridge, we need to move more back
            }
        }

        //grab stone
        closeClaw(robot);
        sleep(500);

        //move back
        move(robot, 1, 7, 3, BACK);
        sleep(250);

        //move to deliver stone
        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 1, inchesToMoveBack, 4, direction);
        sleep(250);

        //deliver stone
        openClaw(robot);
        sleep(500);

        //park
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, 12.5, 2, direction);
    }

}