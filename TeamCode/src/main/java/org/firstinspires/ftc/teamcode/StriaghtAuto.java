package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by David on 11/5/2017.
 */
@Autonomous (name= "Forwards" ,group = "Move Stright")
public class StriaghtAuto extends LinearOpMode {

    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public Servo leftGrab = null;
    public Servo rightGrab = null;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");
        leftGrab = hardwareMap.servo.get("s1");
        rightGrab = hardwareMap.servo.get("s2");

        leftGrab.setPosition(.9);
        rightGrab.setPosition(.1);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()){

            sleep(5000);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftMotor.setTargetPosition(3360);
            rightMotor.setTargetPosition(3360);

            leftMotor.setPower(.3);
            rightMotor.setPower(.3);

            while (leftMotor.isBusy() && rightMotor.isBusy() && opModeIsActive()) {

            }
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            requestOpModeStop();
        }
        requestOpModeStop();
    }
}
