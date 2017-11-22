package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by David on 11/5/2017.
 */
@Autonomous (name= "Side" ,group = "Move Stright")
public class SideAuto extends LinearOpMode {
    DcMotor frontMotor = null;
    DcMotor backMotor = null;

    public void runOpMode(){

        frontMotor = hardwareMap.dcMotor.get("m3");
        backMotor =hardwareMap.dcMotor.get("m4");

        backMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()){
            sleep(5000);
            frontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontMotor.setTargetPosition(5600);
            backMotor.setTargetPosition(5600);

            frontMotor.setPower(.3);
            backMotor.setPower(.3);

            while (frontMotor.isBusy() && backMotor.isBusy() && opModeIsActive()) {

            }
            requestOpModeStop();
        }
        requestOpModeStop();
    }
}
