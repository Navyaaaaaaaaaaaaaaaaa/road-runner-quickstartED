package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.List;

@TeleOp
public class CustomBotTeleop extends OpMode {

    private DcMotor leftBack, leftFront, rightBack, rightFront;
    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;

    private Limelight3A limelight;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack  = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront= hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        shooterLeft  = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake       = hardwareMap.get(DcMotor.class, "intake");

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection (DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection (DcMotorSimple.Direction.REVERSE);

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(4);
        limelight.start();

        telemetry.addLine("Init done");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Drive inputs
        double x = -gamepad2.right_stick_x * 0.6;
        double y = gamepad2.left_stick_y  * 1.1;
        double rx= -gamepad2.left_stick_x;  // note: left stick x used for pivot when in pivot mode

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double frontLeftPower  = (y + x + rx) / denominator;
        double backLeftPower   = (y + x - rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower  = (y - x + rx) / denominator;

        boolean tagDetected = false;
        double tx = 0.0;

        if (gamepad2.left_trigger>0.1) {
            // pivot-mode
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                List<FiducialResult> frs = result.getFiducialResults();
                for (FiducialResult fr : frs) {
                    if (fr.getFiducialId() == 20) {
                        tagDetected = true;
                        tx = fr.getTargetXDegrees();
                        break;
                    }
                }
            }

            if (tagDetected) {
                // Tag seen: lock pivot (do **not** allow left stick to pivot further)
                leftFront .setPower(0);
                leftBack  .setPower(0);
                rightFront.setPower(0);
                rightBack .setPower(0);
                telemetry.addLine("Tag20 detected — pivot locked");
                telemetry.addData("tx", "%.2f", tx);
            } else {
                // Tag not seen: allow pivot with left stick x
                double pivotPower = -gamepad2.right_stick_x * 0.8;
                leftFront .setPower(pivotPower);
                leftBack  .setPower(pivotPower);
                rightFront.setPower(-pivotPower);
                rightBack .setPower(-pivotPower);
                telemetry.addLine("Pivot mode: searching for Tag20");
                telemetry.addData("leftStickX", "%.2f", gamepad2.left_stick_x);
            }

        } else {
            // Normal full driving mode
            leftFront .setPower(frontLeftPower);
            leftBack  .setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack .setPower(backRightPower);
        }

        // Shooter control
//        double shooterPower = gamepad1.left_trigger * -0.06;
//        shooterLeft.setPower(shooterPower);
//        shooterRight.setPower(shooterPower);

        // Intake control
        if (gamepad1.b) {
            intake.setPower(0.7);
        } else if (gamepad1.right_trigger > 0.1) {
            intake.setPower(-0.8);
        } else if (gamepad1.a) {
            intake.setPower(0.5);
        } else if (gamepad1.dpad_up) {
            intake.setPower(1.0);
        } else if(gamepad1.left_trigger>0.1){
            shooterLeft.setPower(-0.5);
            shooterRight.setPower(-0.5);
        } else if(gamepad1.left_bumper){
            shooterLeft.setPower(-1);
            shooterRight.setPower(-1);
        } else {
            intake.setPower(0);
            shooterLeft.setPower(0);
            shooterRight.setPower((0));
        }

        telemetry.update();
    }
}
