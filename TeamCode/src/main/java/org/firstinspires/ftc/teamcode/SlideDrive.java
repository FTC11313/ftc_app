package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gyroscope;
import java.lang.Math;
import java.lang.String;
/**
 * Created by David on 10/6/2017.
 */
//@Disabled
@TeleOp (name="Slide Drive 1.0.0")
public class SlideDrive extends OpMode {
    private ElapsedTime time = new ElapsedTime();
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor backMotor = null;
    private DcMotor frontMotor = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;

    private Servo leftGrab = null;
    private Servo rightGrab = null;

    int dPadDirection = 1;
    boolean single = true;
    int leftOverOne;
    int rightOverOne;
    int frontOverOne;
    int backOverOne;
    double rotRatio = .733333;
    int direction = 0;
    int over;

    /*private DcMotor relicLeftMotor = null;
    private DcMotor relicRightMotor = null;
    */
    /*private Servo jewelServo = null;
    private Servo servoGlyphPush = null;
    private CRServo servoLeftUpTakeTop = null;
    private CRServo servoLefttUpTakeMid = null;
    private CRServo servoLeftUpTakeBot = null;
    private CRServo servoRightUpTake = null;
    private CRServo servoLeftInTake = null;
    private CRServo servoRightInTake = null;*/
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        leftMotor = hardwareMap.get(DcMotor.class, "m1");
        rightMotor = hardwareMap.get(DcMotor.class, "m2");
        backMotor = hardwareMap.get(DcMotor.class, "m3");
        frontMotor = hardwareMap.get(DcMotor.class, "m4");
        leftLift = hardwareMap.dcMotor.get("m5");
        rightLift = hardwareMap.dcMotor.get("m6");

        leftGrab = hardwareMap.servo.get("s1");
        rightGrab = hardwareMap.servo.get("s2");

        rightLift.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.setAutoClear(false);


        //jewelServo = hardwareMap.get(Servo.class, "s1");

        //jewelServo.setPosition(180);
    }

    public void init_loop() {
        telemetry.clear();
        telemetry.addData("Status", "Init Loop");

    }

    public void start() {
        telemetry.clear();
        telemetry.addData("Status", "Start");
    }

    public void loop() {
        telemetry.clear();
        telemetry.addData("Status", "Loop");

        if (gamepad1.dpad_up == true) {
            dPadDirection = 1;
            telemetry.addData("Direction: ", "Forwrads");
        } else if (gamepad1.dpad_left == true) {
            dPadDirection = 2;
            telemetry.addData("Direction: ", "Left");
        } else if (gamepad1.dpad_down == true) {
            dPadDirection = 3;
            telemetry.addData("Direction: ", "Backwards");
        } else if (gamepad1.dpad_right == true) {
            dPadDirection = 4;
            telemetry.addData("Direction: ", "Right");
        }


        double leftStickY = gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
        double leftStickX = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rightStickX = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);

        double linLeftPower = leftStickY;
        double linRightPower = -leftStickY;
        double linFrontPower = -leftStickX;
        double linBackPower = leftStickX;

        double rotLeftPower = -rightStickX;
        double rotRightPower = -rightStickX;
        double rotBackPower = -rightStickX * rotRatio;
        double rotFrontPower = -rightStickX * rotRatio;

        double testLeftPower = linLeftPower + rotLeftPower;
        double testRightPower = linRightPower + rotRightPower;
        double testBackPower = linBackPower + rotBackPower;
        double testFrontPower = linFrontPower + rotFrontPower;

        double finLeftPower = 0;
        double finRightPower = 0;
        double finFrontPower = 0;
        double finBackPower = 0;

        telemetry.addData("Left X Joy Raw: ", gamepad1.left_stick_x);
        telemetry.addData("Left Y Joy Raw: ", gamepad1.left_stick_y);
        telemetry.addData("Right X Joy Raw: ", gamepad1.right_stick_x);

        if (Math.abs(testLeftPower) > 1) //Tests if each Math.abs(testDirectionPower) is over 1 or not if it is, set respective bool to true
        {
            leftOverOne = 1;
            direction = 1;
        } else {
            leftOverOne = 0;
        }
        if (Math.abs(testRightPower) > 1) {
            rightOverOne = 1;
            direction = 2;
        } else {
            rightOverOne = 0;
        }
        if (Math.abs(testFrontPower) > 1) {
            frontOverOne = 1;
            direction = 3;
        } else {
            frontOverOne = 0;
        }
        if (Math.abs(testBackPower) > 1) {
            backOverOne = 1;
            direction = 4;
        } else {
            backOverOne = 0;
        }

        if ((leftOverOne + rightOverOne + frontOverOne + backOverOne) > 1) //Test if more than one testDirectionPower is over 1 i true, set single to false
        {
            single = false;
            over = 1;
        } else if ((leftOverOne + rightOverOne + frontOverOne + backOverOne) == 1) {
            single = true;
            over = 1;
        } else {
            over = 0;
        }

        switch (over) {

            case 0:
                finLeftPower = testLeftPower;
                finRightPower = testRightPower;
                finFrontPower = testFrontPower;
                finBackPower = testBackPower;
                break;
            case 1:
                if (single == true) {

                    switch (direction) {

                        case 1:
                            finLeftPower = testLeftPower / Math.abs(testLeftPower);
                            finRightPower = testRightPower / Math.abs(testLeftPower);
                            finFrontPower = testFrontPower / Math.abs(testLeftPower);
                            finBackPower = testBackPower / Math.abs(testLeftPower);
                            break;
                        case 2:
                            finLeftPower = testLeftPower / Math.abs(testRightPower);
                            finRightPower = testRightPower / Math.abs(testRightPower);
                            finFrontPower = testFrontPower / Math.abs(testRightPower);
                            finBackPower = testBackPower / Math.abs(testRightPower);
                            break;
                        case 3:
                            finLeftPower = testLeftPower / Math.abs(testFrontPower);
                            finRightPower = testRightPower / Math.abs(testFrontPower);
                            finFrontPower = testFrontPower / Math.abs(testFrontPower);
                            finBackPower = testBackPower / Math.abs(testFrontPower);
                            break;

                        case 4:
                            finLeftPower = testLeftPower / Math.abs(testBackPower);
                            finRightPower = testRightPower / Math.abs(testBackPower);
                            finFrontPower = testFrontPower / Math.abs(testBackPower);
                            finBackPower = testBackPower / Math.abs(testBackPower);
                            break;

                        default:
                            telemetry.addData("Return", "Less than 1");
                            break;
                    }
                } else {
                    double maxPower = Math.max(Math.max(Math.abs(testLeftPower), Math.abs(testRightPower)), Math.max(Math.abs(testFrontPower), Math.abs(testBackPower)));
                    finLeftPower = testLeftPower / maxPower;
                    finRightPower = testRightPower / maxPower;
                    finFrontPower = testFrontPower / maxPower;
                    finBackPower = testBackPower / maxPower;
                }
                break;

            default:
                telemetry.addData("Something", "Messed up");
                break;
        }

        //following code is used to switch which side is front
        if (dPadDirection == 1) {
            leftMotor.setPower(finLeftPower);
            rightMotor.setPower(finRightPower);
            frontMotor.setPower(finFrontPower);
            backMotor.setPower(finBackPower);
        } else if (dPadDirection == 4) {
            leftMotor.setPower(finFrontPower);
            rightMotor.setPower(finBackPower);
            frontMotor.setPower(finRightPower);
            backMotor.setPower(finLeftPower);
        } else if (dPadDirection == 3) {
            leftMotor.setPower(finRightPower);
            rightMotor.setPower(finLeftPower);
            frontMotor.setPower(finBackPower);
            backMotor.setPower(finFrontPower);
        } else if (dPadDirection == 2) {
            leftMotor.setPower(finBackPower);
            rightMotor.setPower(finFrontPower);
            frontMotor.setPower(finLeftPower);
            backMotor.setPower(finRightPower);
        }
        else {

        }
        telemetry.addData("Left Motor Final: ", finLeftPower);
        telemetry.addData("Right Motor Final: ", finRightPower);
        telemetry.addData("Front Motor Final: ", finFrontPower);
        telemetry.addData("Back Motor Final: ", finBackPower);


        if (gamepad1.y) {
            leftLift.setPower(.2);
            rightLift.setPower(.2);
        }
        else if (gamepad1.a) {
            leftLift.setPower(-.2);
            rightLift.setPower(-.2);
        }
        else {
            leftLift.setPower(0);
            rightLift.setPower(0);
        }

        if (gamepad1.right_bumper) {
            leftGrab.setPosition(0);
            rightGrab.setPosition(1);
        }
        else if (gamepad1.left_bumper) {
            leftGrab.setPosition(.9);
            rightGrab.setPosition(.1);
        }
        else if (gamepad1.left_trigger > .5) {
            leftGrab.setPosition(.6);
            rightGrab.setPosition(.4);
        }
        else if (gamepad1.right_trigger > .5) {
            leftGrab.setPosition(.4);
            rightGrab.setPosition(.6);
        }
    }
}

//servoRightInTake.setDirection(CRServo.Direction.REVERSE);
//servoLeftInTake.setDirection(CRServo.Direction.REVERSE);

        /*if (gamepad1.left_bumper=true){
            servoLeftInTake.setPower(-.5);
        }
        if (gamepad1.right_bumper=true){
            servoRightInTake.setPower(-.5);
        }
        else {
            servoLeftInTake.setPower(gamepad1.left_trigger);
            servoRightInTake.setPower(gamepad1.right_trigger);
        }


        if (gamepad1.x=true){
            /*move lift down
            drive both left and right uptake
            move lift up
            slow uptake speeds
        /*
        }

        if (gamepad1.dpad_up=true){
            servoGlyphPush.setPosition(60);
        }
        */