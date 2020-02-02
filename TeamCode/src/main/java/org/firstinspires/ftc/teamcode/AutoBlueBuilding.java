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
//        sleep(500);

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
//        sleep(250);

        //grab foundation
        closeClaw(robot);
        closeFClaw(robot);
        sleep(1000);

//        move(robot, 1, 2, 3, FORWARD);
//        sleep(50);

        // pull foundation back
        move(robot, 1, 42, 9, BACK);
//        sleep(50);

        //release foundation
        openClaw(robot);
        openFClaw(robot);
        sleep(300);

        //Go out from behind the foundation
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 0.8, 30.5, 6, direction);
//        sleep(250);

        //close the claw
        closeClaw(robot);
        closeFClaw(robot);

        //move forward so we won't bump into the other robot
        move(robot, 0.9, 20, 6, FORWARD);
//        sleep(250);

        //go under the bridge
        move(robot, 0.9, 52.5, 6, direction);
//        sleep(250);

        //0pen the claw
        openClaw(robot);
        sleep(400);

        //drive to stones using distance sensor
        move(robot, 1, 8,5,FORWARD);
        moveWithDistanceSensor(robot, 0.1, 16, 8, FORWARD, 2.3);
        telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                robot.distanceSensor.getDistance(DistanceUnit.INCH),
                robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
        telemetry.update();

//        sleep(200);

        double inchesToMoveBack = 45;

        for(int i = 0; i < 4; i++) {
            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
            telemetry.update();

            if(!isBlackStone(robot)) {
                move(robot, 0.65 , 8.5, 6, direction);
//                sleep(200);
                inchesToMoveBack += 8.75; // every time we move away from bridge, we need to move more back
            } else {
                break;
            }
        }

        //align
        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 0.7,4,2,direction);

        //grab stone
        closeClaw(robot);
        sleep(400);

        //move back
        move(robot, 1, 11, 3, BACK);
//        sleep(250);

        //move to deliver stone
        move(robot, 1, inchesToMoveBack, 4, direction);
//        sleep(250);

        //deliver stone
        openClaw(robot);
        sleep(100);

        //park
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, 17.5, 2, direction);
    }

}