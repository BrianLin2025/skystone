package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        move(robot, 0.4, 60, 6, BACK);
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
        move(robot, 1, 75, 6, direction);
        sleep(500);

        //drive to stones
        move(robot, 1, 32, 3, FORWARD);
        sleep(500);

        //grab stone
        closeClaw(robot);
        sleep(500);

        //move back
        move(robot, 1, 31, 3, BACK);
        sleep(500);

        //move to deliver stone
        if (isBlueSide) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
        move(robot, 1, 40, 4, direction);
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
        move(robot, 1, 15, 2, direction);
    }

}
