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

    // --- Motors ---
    private DcMotor leftBack, leftFront, rightBack, rightFront;
    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;

    // --- Limelight ---
    private Limelight3A limelight;

    @Override
    public void init() {
        // Map motors
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        // Directions
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        // Brake mode for precise control
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(4);
        limelight.start();

        telemetry.addLine("TeleOp with Vision Auto-Stop Ready");
        telemetry.update();
    }

    @Override
    public void loop() {

        // =======================
        // ===== DRIVE LOGIC =====
        // =======================
        double x = gamepad2.right_stick_x * 0.6;
        double y = gamepad2.left_stick_y * 1.1;
        double rx = -gamepad2.left_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y + x - rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y - x + rx) / denominator;

        boolean tagDetected = false;

        // ==============================
        // ===== LIMELIGHT CHECK ========
        // ==============================
        if (gamepad2.x) {
            // Only check tag when holding X
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                List<FiducialResult> fiducials = result.getFiducialResults();
                for (FiducialResult fr : fiducials) {
                    if (fr.getFiducialId() == 20) {
                        tagDetected = true;
                        break;
                    }
                }
            }

            if (tagDetected) {
                // Stop all movement when tag is detected
                leftFront.setPower(0);
                leftBack.setPower(0);
                rightFront.setPower(0);
                rightBack.setPower(0);
                telemetry.addLine("AprilTag 20 Detected — Robot Locked");
            } else {
                // Allow driver to spin manually with joystick
                double manualTurn = -gamepad2.left_stick_x * 0.6;
                leftFront.setPower(manualTurn);
                leftBack.setPower(manualTurn);
                rightFront.setPower(-manualTurn);
                rightBack.setPower(-manualTurn);
                telemetry.addLine("Searching... use left stick to spin");
            }

        } else {
            // Normal full driving (when X not held)
            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);
        }

        // ===========================
        // ===== SHOOTER & INTAKE ====
        // ===========================
        double shooterPower = gamepad1.left_trigger * -0.67;
        shooterLeft.setPower(shooterPower);
        shooterRight.setPower(shooterPower);

        if (gamepad1.b) {
            intake.setPower(1);
        } else if (gamepad1.right_trigger > 0.1) {
            intake.setPower(-1);
        } else if (gamepad1.a) {
            intake.setPower(0.5);
        } else if (gamepad1.dpad_up) {
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }

        telemetry.update();
    }
}
