package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by David on 11/18/2017.
 */
@Disabled
@Autonomous (name = "BlueAuto1", group = "BlueAuto")
public class BlueAuto1 extends LinearOpMode {

    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor frontMotor = null;
    private DcMotor backMotor = null;
    private Servo leftKnocker = null;
    private ColorSensor color1 = null;
    private ColorSensor color2 = null;
    private GyroSensor gyro = null;

    @Override
    public void runOpMode() {

        telemetry.setAutoClear(false);

        color1 = hardwareMap.colorSensor.get("c1");
        color1 = hardwareMap.colorSensor.get("c2");

        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");
        backMotor = hardwareMap.dcMotor.get("m3");
        frontMotor = hardwareMap.dcMotor.get("m4");

        leftKnocker = hardwareMap.servo.get("s1");

        color1.enableLed(true);
        color2.enableLed(true);

        leftKnocker.setPosition(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        gyro.calibrate();

        if (gyro.isCalibrating()) {
            telemetry.addData("Calibration: ", "True");
            telemetry.update();
        }
        else {
            telemetry.clear();
            telemetry.addData("Calibration: ", "False");
            telemetry.update();
        }


        waitForStart();
        telemetry.clear();
        while (opModeIsActive()){
            leftKnocker.setPosition(1);
            if(color1.blue() > color1.red()) {
                spin(.3, 280);
                sleep(1000);
                spin(-.3, 280);
                leftKnocker.setPosition(0);
            }
            else if (color1.blue() < color1.red()) {
                spin(-.3, 280);
                sleep(1000);
                spin(.3, 280);
                leftKnocker.setPosition(0);
            }
            else {
                telemetry.addData("Error","");
                telemetry.update();
                leftKnocker.setPosition(0);
            }

            driveForwards(.3, 3360);

            requestOpModeStop();
        }

    }
    public void driveForwards(double power, int distance) {
        leftMotor.setTargetPosition(distance);
        rightMotor.setTargetPosition(distance);

        leftMotor.setPower(power);
        rightMotor.setPower(-power);

    }
    public void spin(double Power, int distance) {
        double ratio = 11/15;

        leftMotor.setTargetPosition(distance);
        rightMotor.setTargetPosition(distance);
        frontMotor.setTargetPosition(distance);
        backMotor.setTargetPosition(distance);

        leftMotor.setPower(Power);
        rightMotor.setPower(Power);
        frontMotor.setPower(Power * ratio);
        backMotor.setPower(Power * ratio);

        while (leftMotor.isBusy() && rightMotor.isBusy() && frontMotor.isBusy() && backMotor.isBusy()){

        }
    }
}
