package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="ParkToLeft", group="HGT")
public class ParkToLeft extends AutoBot {
    @Override
    public void runOpMode() {

        setup();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        sleep(20000);

        move(robot, 1, 40, 30, LEFT);
    }
}
