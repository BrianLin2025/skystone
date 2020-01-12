package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="HyperBotAutoMode", group="HGT")
public class HyperBotAutoMode extends AutoBot{
    public int isBlueSide = 1;
    double leftX = 0;
    public int foundationSide  = -1;
    double leftY = 0;
    public int parkTop = 1;
    double rightX = 0;
    public int takeTwoBricks = -1;
    //double rightY = 0;
    public int bricksToScan = 3;
    //boolean bumper;
    //boolean bumper2;


    @Override
    public void runOpMode() {
        setup();
        leftX = gamepad1.left_stick_x;
        leftY = gamepad1.left_stick_y;
        rightX = gamepad1.right_stick_x;
        //rightY = gamepad1.right_stick_y;
        //bumper = gamepad1.left_bumper;
        //bumper2 = gamepad1.right_bumper;
        if(leftX != 0) {
            isBlueSide = isBlueSide * -1;
        }
        if(leftY != 0) {
            foundationSide = foundationSide * -1;
        }
        if(rightX != 0) {
            parkTop = isBlueSide * -1;
        }
        telemetry.addData("1 = true and -1 = false","","");
        telemetry.addData("is blue side : " + isBlueSide ,"","");
        telemetry.addData("foundation site side : " + foundationSide,"","");
        telemetry.addData("park at the top : " + parkTop,"","");
        //telemetry.addData("take two bricks : true","","");
        //telemetry.addData("how many bricks to scan : 1","","");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        if(foundationSide == 1) {
            //open claw
            openClaw(robot);
            sleep(500);

            //move to foundation
            int direction = 0;
            if (isBlueSide == 1) {
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
            if (isBlueSide == 1) {
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
            move(robot, 0.75, 52.5, 6, direction);
            sleep(250);

            //0pen the claw
            openClaw(robot);
            sleep(500);

            //drive to stones using distance sensor
            moveWithDistanceSensor(robot, 0.2, 20, 8, FORWARD, 1.65);
            sleep(200);

            double inchesToMoveBack = 45;

            for (int i = 0; i < 5 && bricksToScan != i; i++ ) {
                telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                        robot.distanceSensor.getDistance(DistanceUnit.INCH),
                        robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
                telemetry.update();

                if (!isBlackStone(robot)) {
                    move(robot, 0.65, 8.5, 6, direction);
                    sleep(200);
                    inchesToMoveBack += 8.75; // every time we move away from bridge, we need to move more back
                }
            }

            //align
            if (isBlueSide == 1) {
                direction = LEFT;
            } else {
                direction = RIGHT;
            }
            move(robot, 0.7, 4, 2, direction);

            //grab stone
            closeClaw(robot);
            sleep(500);

            if(parkTop == 1) {
                //move back
                move(robot, 1, 7, 3, BACK);
                sleep(250);
            } else {
                //move back
                move(robot, 1, 34, 3, BACK);
                sleep(250);
            }

            //move to deliver stone
            move(robot, 1, inchesToMoveBack, 4, direction);
            sleep(250);

            //deliver stone
            openClaw(robot);
            sleep(400);

            //park
            if (isBlueSide == 1) {
                direction = RIGHT;
            } else {
                direction = LEFT;
            }
            move(robot, 1, 17.5, 2, direction);







        } else {
            //get into corner
            int direction = 0;
            if (isBlueSide == 1) {
                direction = RIGHT;
            } else {
                direction = LEFT;
            }
            move(robot, 1, 27, 4, direction);
            sleep(100);

            //get closer to the bricks
            move(robot, 0.8, 22, 5, FORWARD);
            sleep(200);

            if (isBlueSide == 1) {
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
            for (int i = 0; i < 5; i++) {
                telemetry.addData("Sensors", "Distance(%.2f in), Red(%d), Green(%d), Blue(%d)",
                        robot.distanceSensor.getDistance(DistanceUnit.INCH),
                        robot.colorSensor.red(), robot.colorSensor.green(), robot.colorSensor.blue());
                telemetry.update();

                if (!isBlackStone(robot)  && bricksToScan != i) {
                    move(robot, 1, 8.5, 6, direction);
                    sleep(200);
                    inchesToMoveBack += 8.5; // every time we move away from the corner, we need to move more back
                    ii++;
                }
            }

            //grab stone
            closeClaw(robot);
            sleep(800);

            if (takeTwoBricks == -1) {
                //back up
                move(robot, 1, 48, 10, BACK);
                //get in to the corner
                if (isBlueSide == 1) {
                    direction = RIGHT;
                } else {
                    direction = LEFT;
                }
                move(robot, 1, inchesToMoveBack, 10, direction);
                sleep(10000);
                if (parkTop == 1) {
                    move(robot, 1, 33, 10, FORWARD);
                }
                //cross the bridge
                if (isBlueSide == 1) {
                    direction = LEFT;
                } else {
                    direction = RIGHT;
                }
                move(robot, 1, 78, 10, direction);
                //drop the brick
                openClaw(robot);
                sleep(800);

                if (isBlueSide == 1) {
                    direction = RIGHT;
                } else {
                    direction = LEFT;
                }
                move(robot, 1, 10, 10, direction);
            } else {
                int i;
                if (parkTop == 1) {
                    i = 7;
                } else {
                    i = 48;
                }
                //back up
                move(robot, 1, i, 10, BACK);
                move(robot, 1, 51, 10, direction);
                openClaw(robot);
                if (isBlueSide == 1) {
                    direction = RIGHT;
                } else {
                    direction = LEFT;
                }
                move(robot, 1, 51, 10, direction);
                move(robot, 1, i, 10, FORWARD);
                closeClaw(robot);
                //back up
                move(robot, 1, i, 10, BACK);
                move(robot, 1, 51, 10, direction);
                openClaw(robot);
                if (isBlueSide == 1) {
                    direction = RIGHT;
                } else {
                    direction = LEFT;
                }
                move(robot, 1, 8, 10, direction);
            }
        }
    }
}