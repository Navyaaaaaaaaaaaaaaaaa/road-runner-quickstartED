package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "RonakAutoRed1")
public class RonakAutoRed1 extends LinearOpMode {

    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private Servo hardstop;
    private MecanumDrive drive;

    private static final double EST_TRAVEL_SEC = 2.5;

    @Override
    public void runOpMode() throws InterruptedException {

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        hardstop = hardwareMap.get(Servo.class, "hardstop");
        hardstop.setPosition(0.6);

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Pose2d beginPose = new Pose2d(57, -44, Math.toRadians(-45));
        drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        // preload shooting
                        .afterTime(Math.max(0, EST_TRAVEL_SEC - 2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(34, -34), Math.toRadians(-46))
                        .waitSeconds(0.5)
                        .afterTime(1, startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5, stopShooter())

                        // cycle 1
                        .strafeToLinearHeading(new Vector2d(6, -30), Math.toRadians(-90))
                        .afterTime(0, startIntake())
                        .afterTime(0, starthardStop())
                        .strafeTo(new Vector2d(6, -32))
                        .strafeTo(new Vector2d(6, -55))
                        .afterTime(0.5, stopIntake())
                        .afterTime(2.5, stophardStop())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC - 3), startballShooter())
                        .strafeToLinearHeading(new Vector2d(36, -34), Math.toRadians(-35))
                        .waitSeconds(0.5)
                        .afterTime(0.5, startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5, stopShooter())
                        .afterTime(0, stopIntake())

                        // cycle 2
                        .strafeToLinearHeading(new Vector2d(-13, -29), Math.toRadians(-90))
                        .afterTime(0, starthardStop())
                        .afterTime(0, startIntake())
                        .strafeTo(new Vector2d(-13,-32))
                        .strafeTo(new Vector2d(-13, -54))
                        .afterTime(2.5, stophardStop())
                        .afterTime(0,startIntake())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC - 3), startballShooter())
                        .strafeToLinearHeading(new Vector2d(38, -34), Math.toRadians(-33))
                        .waitSeconds(0.5)
                        .afterTime(0.5, startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5, stopShooter())
                        .afterTime(0, stopIntake())

                        // cycle 3
                        .strafeToLinearHeading(new Vector2d(-34, -31), Math.toRadians(-90))
                        .afterTime(0.5, startIntake())
                        .afterTime(0, starthardStop())
                        .strafeTo(new Vector2d(-34, -32))
                        .strafeTo(new Vector2d(-34, -65))
                        .afterTime(1, stopIntake())
                        .afterTime(2.5, stophardStop())
//                        .strafeToLinearHeading(new Vector2d(15, -46), Math.toRadians(0))
//                        .strafeToLinearHeading(new Vector2d(15, -68), Math.toRadians(0))
                        .afterTime(Math.max(0, EST_TRAVEL_SEC - 3), startballShooter())
                        .strafeToLinearHeading(new Vector2d(38, -34), Math.toRadians(-33))
                        .waitSeconds(0.5)
                        .afterTime(0.5, startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5, stopShooter())
                        .afterTime(0, stopIntake())
                        .strafeToLinearHeading(new Vector2d(60, -31), Math.toRadians(-33))

                        .build()
        );
    }

    private Action startShooter() {
        return p -> { shooterLeft.setPower(-0.445); shooterRight.setPower(-0.445); return false; };
    }

    private Action stopShooter() {
        return p -> { shooterLeft.setPower(0); shooterRight.setPower(0); return false; };
    }

    private Action startIntake() {
        return p -> { intake.setPower(-0.8); return false; };
    }

    private Action stopIntake() {
        return p -> { intake.setPower(0); return false; };
    }

    private Action startballShooter() {
        return p -> { shooterLeft.setPower(-0.47); shooterRight.setPower(-0.47); return false; };
    }

    private Action starthardStop() {
        return p -> { hardstop.setPosition(0.3); return false; };
    }

    private Action stophardStop() {
        return p -> { hardstop.setPosition(0.6); return false; };
    }
}
