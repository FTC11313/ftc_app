package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


/**
 * Created by David on 10/23/2017.
 */
@Disabled
@Autonomous(name = "VuMarkAuto",group = "VuMark")
public class AutoTest extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AcgwJr//////AAAAGdmz4wUimUWkqHngf7t0scNajmmQk/pS2/Sx+FyEoxvTyExPyKZom8mXRAvcWA/q6IporKmcRAhsJEIGCSMQsjdvg/TeBRQ0NVW1m15T5CZBHmVlaPT0+KezTV8Ps3+7U5TVH3MzlD9/ieDNdBV3Uzd/Tlx2nc5FsVGP386jPUrtPAB+ydVOnPdHNU7kvKOusTH0GD6JvEfP09KEDH0oYZ5B4CLowXfZ6T2lnqzMM3Jhs/VuVC26Lil+JLwzXFFL09KmadIjcTOirFg/eYLlMXWq0hiNp/QNGrQbg7zl78PEjTUdn3wy+VQdubZiqYORFIoks1Q1hkLIkdpWyfUCxgcZnbVQoeMEJAXcRA1OSQ/m";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        boolean viewMarkDetected = false;

        while (opModeIsActive()) {
            while (viewMarkDetected == false){
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    telemetry.addData("VuMark","Left Detected");
                    telemetry.update();
                    viewMarkDetected = true;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    telemetry.addData("VuMark","Center Detected");
                    telemetry.update();
                    viewMarkDetected = true;
                }
                else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    telemetry.addData("VuMark","Right Detected");
                    telemetry.update();
                    viewMarkDetected = true;
                }
                else {
                    telemetry.addData("VuMark", "Not Visible");
                    telemetry.update();
                    //turn left and right to try to detect the image
                }
            }
        }
    }
}
