package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    /* Public OpMode members. */
    //null = nothing, not 0
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    public DcMotor  linearDrive = null;
    public Servo    servo1      = null;
    public Servo    servo2      = null;
    public Servo    servo3      = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    /* Constructor */
    public Robot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        //hardwaremap = robot config

        // Define and Initialize Motors
        leftDrive  = hwMap.get(DcMotor.class, "leftDrive");
        rightDrive = hwMap.get(DcMotor.class, "rightDrive");
        linearDrive = hwMap.get(DcMotor.class, "linearDrive");

        // Set all motors to run without encoder by default
        //encoder = fll degrees
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set motor directions
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        linearDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        linearDrive.setPower(0);

        // Define and initialize servos
        servo1  = hwMap.get(Servo.class, "servo1");
        servo2  = hwMap.get(Servo.class, "servo2");
        servo3  = hwMap.get(Servo.class, "servo3");

        servo1.setPosition(0);
        servo2.setPosition(0);
        servo3.setPosition(0);
    }

 }

