package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="ParkToRight_Wall", group="HGT")
public class ParkToRight_Wall extends AutoBot {
    @Override
    public void runOpMode() {

        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        sleep(20000);

        move(robot, 1, 40, 30, RIGHT);
    }
}
