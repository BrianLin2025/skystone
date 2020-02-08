package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="ParkToRight", group="HGT")
public class ParkToRight extends AutoBot {
    @Override
    public void runOpMode() {

        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        sleep(20000);

        move(robot, 1, 40, 30, RIGHT);
    }
}
