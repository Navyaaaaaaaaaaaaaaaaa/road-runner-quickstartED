package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class Direction extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotorEx par0 = hardwareMap.get(DcMotorEx.class, "shooterLeft");
        DcMotorEx par1 = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx perp = hardwareMap.get(DcMotorEx.class, "leftBack");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("par0 (left forward)", par0.getCurrentPosition());
            telemetry.addData("par1 (right forward)", par1.getCurrentPosition());
            telemetry.addData("perp (lateral)", perp.getCurrentPosition());
            telemetry.update();
        }
    }
}
