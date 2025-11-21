package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "NavyaAuto")
public class Navya extends LinearOpMode {

    // Declare hardware
    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private MecanumDrive drive;
    private static final double EST_TRAVEL_SEC = 3.5;


    @Override
    public void runOpMode() throws InterruptedException {

        // ---- Initialize Hardware ----
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        // Set motor directions
        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        // ---- Initialize Drive ----
        Pose2d beginPose = new Pose2d(65, -10, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();


        // ---- Run Trajectory ----
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .afterTime(0,startShooter())
                        .strafeToLinearHeading(new Vector2d(-12.3, 0), Math.toRadians(-135))
                        .waitSeconds(2.5)
                        .afterTime(0,startIntake())
                        .afterTime(0,stopShooter())

                        .strafeToLinearHeading(new Vector2d(-12.3,-25),Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .strafeToLinearHeading(new Vector2d(-12.3, -51.6), Math.toRadians(-90))
                        .afterTime(0,stopIntake())

                        .afterTime(0,startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(-135))
                        .waitSeconds(2.5)
                        .afterTime(0,startIntake())
                        .afterTime(0,stopShooter())

                        .strafeToLinearHeading(new Vector2d(11.3, -24.7), Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .strafeTo(new Vector2d(11.3, -51.6))
                        .afterTime(0,stopIntake())

                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(-135))
                        .afterTime(0,startShooter())
                        .waitSeconds(2.5)
                        .afterTime(0,stopShooter())

                        .strafeToLinearHeading(new Vector2d(36, -24.7), Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .strafeTo(new Vector2d(36, -51.6))
                        .afterTime(0,stopShooter())

                        .afterTime(0,startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(-135))
                        .waitSeconds(2.5)
                        .afterTime(0,startIntake())
                        .afterTime(0,stopShooter())

                        .strafeToLinearHeading(new Vector2d(60, 49), Math.toRadians(90))
                        .afterTime(0,startIntake())
                        .waitSeconds(1.5)
                        .afterTime(0,stopIntake())

                        .afterTime(0,startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(-135))
                        .afterTime(0,startIntake())
                        .waitSeconds(2.5)
                        .afterTime(0,stopShooter())

                        .build()
        );
    }

    private Action startShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.5);
            shooterRight.setPower(-0.5);
            return false;
        };
    }

    private Action stopShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(0);
            shooterRight.setPower(0);
            return false;
        };

    }

    private Action startIntake() {
        return telemetryPacket -> {
            intake.setPower(-1);
            return false;
        };

    }

    private Action stopIntake() {
        return telemetryPacket -> {
            intake.setPower(0);
            return false;
        };

    }
}