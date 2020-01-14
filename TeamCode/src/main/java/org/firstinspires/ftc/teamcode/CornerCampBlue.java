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
        move(robot, 1, 27, 4, direction);
        sleep(100);

        //get closer to the bricks
        move(robot, 0.8, 22, 5, FORWARD);
        sleep(200);

        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 0.8, 3, 5, direction);
        sleep(200);

        //open claw
        openClaw(robot);
        sleep(800);

        //drive to stones using distance sensor
        moveWithDistanceSensor(robot, 0.25, 9, 7, FORWARD, 1.5);
        sleep(200);

        //scan for skystone
        int ii = 3;
        double inchesToMoveBack = 2;
        for(int i = 0; i < 3; i++) {
            telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                    robot.distanceSensor.getDistance(DistanceUnit.INCH),
                    robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
            telemetry.update();

            if(!isBlackStone(robot) && ii < 1 ) {
                move(robot, 1, 8.5, 6, direction);
                sleep(200);
                inchesToMoveBack += 8.5; // every time we move away from the corner, we need to move more back
                ii++;
            }
        }

        //grab stone
        closeClaw(robot);
        sleep(800);

        //back up
        move(robot, 1, 48, 10, BACK);

        //get in to the corner
        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, inchesToMoveBack, 10, direction);

        sleep(10000);

        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }

        move(robot, 1, 78, 10, direction);

        openClaw(robot);
        sleep(800);

        if (isBlueSide) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        move(robot, 1, 10, 10, direction);
    }
}
