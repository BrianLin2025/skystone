package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="CornerCampBlue", group="HGT")
public class CornerCampRed extends CornerCampBlue {
    public CornerCampRed() {
        isBlueSide = false;
    }

}
