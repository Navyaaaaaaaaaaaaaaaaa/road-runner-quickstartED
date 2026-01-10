package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "TuningPIDF")
public class TuningPIDF extends OpMode {

    // Motors
    public DcMotorEx shooterLeft;
    public DcMotorEx shooterRight;

    // Velocity targets
    public double highVelocity = 1500;
    public double lowVelocity = 900;
    public double curTargetVelocity = highVelocity;

    // PIDF constants (shared)
    public double p = 0;
    public double f = 0;

    // Step size control
    public double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};
    public int stepIndex = 1;

    @Override
    public void init() {
        shooterLeft = hardwareMap.get(DcMotorEx.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotorEx.class, "shooterRight");

        shooterLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);

        applyPIDF();

        telemetry.addLine("Dual Shooter PIDF Tuner Ready");
    }

    @Override
    public void loop() {

        // Toggle velocity
        if (gamepad1.y) {
            curTargetVelocity =
                    (curTargetVelocity == highVelocity) ? lowVelocity : highVelocity;
        }

        // Cycle step size
        if (gamepad1.b) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        // Tune F
        if (gamepad1.dpad_left) {
            f -= stepSizes[stepIndex];
        } else if (gamepad1.dpad_right) {
            f += stepSizes[stepIndex];
        }

        // Tune P
        if (gamepad1.dpad_up) {
            p += stepSizes[stepIndex];
        } else if (gamepad1.dpad_down) {
            p -= stepSizes[stepIndex];
        }

        applyPIDF();

        shooterLeft.setVelocity(curTargetVelocity);
        shooterRight.setVelocity(curTargetVelocity);

        double leftVel = shooterLeft.getVelocity();
        double rightVel = shooterRight.getVelocity();

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Left Velocity", leftVel);
        telemetry.addData("Right Velocity", rightVel);
        telemetry.addData("Left Error", curTargetVelocity - leftVel);
        telemetry.addData("Right Error", curTargetVelocity - rightVel);
        telemetry.addData("P", p);
        telemetry.addData("F", f);
        telemetry.addData("Step Size", stepSizes[stepIndex]);
        telemetry.update();
    }

    private void applyPIDF() {
        PIDFCoefficients pidf = new PIDFCoefficients(p, 0, 0, f);
        shooterLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        shooterRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
    }
}
