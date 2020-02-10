package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="ParkToRight_Bridge", group="HGT")
public class ParkToRight_Bridge extends AutoBot {
    @Override
    public void runOpMode() {

        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        sleep(20000);

        move(robot, 1, 30, 30, FORWARD);
        move(robot, 1, 40, 30, RIGHT);
    }
}

