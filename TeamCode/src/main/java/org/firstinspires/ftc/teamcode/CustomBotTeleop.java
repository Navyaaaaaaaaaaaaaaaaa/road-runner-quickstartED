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


        double x  = -gamepad2.right_stick_x * 1.1;
        double y  =  gamepad2.left_stick_y * 1.1;
        double rx = -gamepad2.left_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double frontLeftPower  = (y + x + rx) / denominator;
        double backLeftPower   = (y + x - rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower  = (y - x + rx) / denominator;

        boolean tagDetected = false;
        double tx = 0.0;

        if (gamepad2.left_trigger > 0.1) {

            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                for (FiducialResult fr : result.getFiducialResults()) {
                    if (fr.getFiducialId() == 20) {
                        tagDetected = true;
                        tx = fr.getTargetXDegrees();
                        break;
                    }
                }
            }

            if (tagDetected) {

                setSafePower(leftFront,  0);
                setSafePower(leftBack,   0);
                setSafePower(rightFront, 0);
                setSafePower(rightBack,  0);
                telemetry.addLine("Tag20 detected — pivot locked");
                telemetry.addData("tx", "%.2f", tx);

            } else {

                double pivotPower = -gamepad2.right_stick_x * 0.8;
                setSafePower(leftFront,  pivotPower);
                setSafePower(leftBack,   pivotPower);
                setSafePower(rightFront, -pivotPower);
                setSafePower(rightBack,  -pivotPower);

                telemetry.addLine("Pivot mode: searching for Tag20");
                telemetry.addData("leftStickX", "%.2f", gamepad2.left_stick_x);
            }

        } else {

            setSafePower(leftFront,  frontLeftPower);
            setSafePower(leftBack,   backLeftPower);
            setSafePower(rightFront, frontRightPower);
            setSafePower(rightBack,  backRightPower);
        }


        if (gamepad1.right_trigger > 0.1) {
            setSafePower(intake, -0.8);
            setSafePower(shooterLeft, 0.7);
            setSafePower(shooterRight, 0.7);
        } else if (gamepad1.a) {
            setSafePower(intake, -1);
        } else if (gamepad1.dpad_up) {
            setSafePower(intake, 1.0);
        } else if (gamepad1.left_trigger > 0.1) {
            setSafePower(shooterLeft, -0.55);
            setSafePower(shooterRight, -0.55);
            setSafePower(intake, 0);
        } else if (gamepad1.left_bumper) {
            setSafePower(shooterLeft, -1);
            setSafePower(shooterRight, -1);
            setSafePower(intake, 0);
        } else {
            setSafePower(intake, 0);
            setSafePower(shooterLeft, 0);
            setSafePower(shooterRight, 0);
        }

        telemetry.update();
    }

    void setSafePower(DcMotor motor, double targetPower) {
        final double SLEW_RATE = 0.2;
        double currentPower = motor.getPower();
        double desired = targetPower - currentPower;
        double limited = Math.max(-SLEW_RATE, Math.min(desired, SLEW_RATE));
        motor.setPower(currentPower + limited);
    }
}
