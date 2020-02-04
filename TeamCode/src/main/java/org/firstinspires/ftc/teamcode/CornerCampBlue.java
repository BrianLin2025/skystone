package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="CornerCampBlue", group="HGT")
public class CornerCampBlue extends AutoBot{
    public boolean isBlueSide = true;

    @Override
    public void runOpMode() {
        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //get into corner
        int direction = 0;
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        clawsUp(robot);
        move(robot, 0.8, 27, 4, direction);
//        sleep(100);

        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 0.7, 3, 5, direction);
//        sleep(200);

        //open claw
        openClaw(robot);
        sleep(800);

        move(robot, 1, 24, 6, FORWARD);
        //drive to stones using distance sensor
        moveWithDistanceSensor(robot, 0.1, 9, 7, FORWARD, 2);
        sleep(200);

        //scan for skystone
        double inchesToMoveBack = 2;
        for(int i = 0; i < 3; i++) {
            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
            telemetry.update();

            if(!isBlackStone(robot)) {
                move(robot, 1, 8.5, 6, direction);
                sleep(200);
                inchesToMoveBack += 8.5; // every time we move away from the corner, we need to move more back
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
        sleep(800);

        move(robot, 0.5, 2, 1, FORWARD);

        //back up
        move(robot, 1, 48, 10, BACK);

        //get in to the corner
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, inchesToMoveBack, 10, direction);

        sleep(2000);

        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }

        move(robot, 1, 80, 10, direction);

        openClaw(robot);
        sleep(800);

        move(robot, 1, 1, 10, FORWARD);

        move(robot, 1, 1, 10, BACK);

        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, 10, 10, direction);
    }
}
