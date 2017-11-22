package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

/**
 * Created by David on 10/18/2017.
 */
@Disabled
@Autonomous(name = "Move Foward", group = "Move")
public class TeamTest extends LinearOpMode {

    IntegratingGyroscope gyro;
    ModernRoboticsI2cGyro modernRoboticsI2cGyro;

    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    DcMotor frontMotor = null;
    DcMotor backMotor = null;

    @Override
    public void runOpMode() throws InterruptedException{

        double leftPower;
        double rightPower;

        double error = .005;
        double correction = 0;

        modernRoboticsI2cGyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");

        double gyroIntZ = modernRoboticsI2cGyro.getIntegratedZValue();
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()){
            sleep (5000);
            modernRoboticsI2cGyro.calibrate();
            while (modernRoboticsI2cGyro.isCalibrating() == true){
                telemetry.addData("Calibrating", "Gyro");
                telemetry.update();
            }
            int leftStart = leftMotor.getCurrentPosition();
            int rightStart = rightMotor.getCurrentPosition();

            int targetLeft = leftStart + 5600;
            int targetRight = rightStart +5600;

            leftMotor.setTargetPosition(targetLeft);

            leftMotor.setPower(.5);
            rightMotor.setPower(.5);

            while (leftMotor.isBusy() && rightMotor.isBusy()){
                correction = modernRoboticsI2cGyro.getIntegratedZValue() * .005 ;
                leftMotor.setPower(.5 + correction);
                rightMotor.setPower(.5 - correction);
            }
            requestOpModeStop();
        }
    }
}
