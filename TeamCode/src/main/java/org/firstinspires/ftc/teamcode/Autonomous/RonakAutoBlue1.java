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


@Autonomous(name = "RonakAutoBlue1")
public class RonakAutoBlue1 extends LinearOpMode {

    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private MecanumDrive drive;
    private static final double EST_TRAVEL_SEC = 2.5;


    @Override
    public void runOpMode() throws InterruptedException {
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        Pose2d beginPose = new Pose2d(-57, -44, Math.toRadians(-135));


        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        //preload shooting ------------------------------------------------------------------
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(-48, -34), Math.toRadians(-123))
                        .waitSeconds(0.5)
                        .afterTime(1,startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5,stopShooter())

                        //cycle 1 ------------------------------------------------------------------

                        .strafeToLinearHeading(new Vector2d(-9.5, -28), Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .afterTime(0,startRevShooter())
                        .waitSeconds(0.5)
                        .strafeTo(new Vector2d(-9.5, -39))
                        .strafeTo(new Vector2d(-9.5, -56))
                        .afterTime(1,stopIntake())
                        .afterTime(0.5,stopShooter())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-2), startballShooter())
                        .strafeToLinearHeading(new Vector2d(-48, -34), Math.toRadians(-123))
                        .waitSeconds(1.5)
                        .afterTime(0.5,startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5,stopShooter())
                        .afterTime(0,stopIntake())

                        //cycle2---------------------------------------------------------------------

                        .strafeToLinearHeading(new Vector2d(9, -32), Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .afterTime(0,startRevShooter())
                        .waitSeconds(0.5)
                        .strafeTo(new Vector2d(9, -39))
                        .strafeTo(new Vector2d(9, -60))
                        .afterTime(1,stopIntake())
                        .afterTime(0.5,stopShooter())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-2), startShooter())
                        .strafeToLinearHeading(new Vector2d(-48, -34), Math.toRadians(-126))
                        .waitSeconds(1.5)
                        .afterTime(0.5,startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5,stopShooter())
                        .afterTime(0,stopIntake())

                        //cycle3---------------------------------------------------------------------

                        .strafeToLinearHeading(new Vector2d(30, -32), Math.toRadians(-90))
                        .afterTime(0,startIntake())
                        .afterTime(0,startRevShooter())
                        .waitSeconds(0.5)
                        .strafeTo(new Vector2d(30, -39))
                        .strafeTo(new Vector2d(30, -560))
                        .afterTime(1,stopIntake())
                        .afterTime(0.5,stopShooter())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-2), startShooter())
                        .strafeToLinearHeading(new Vector2d(-48, -34), Math.toRadians(-123))
                        .waitSeconds(1)
                        .afterTime(0.5,startIntake())
                        .waitSeconds(2)
                        .afterTime(0.5,stopShooter())
                        .afterTime(0,stopIntake())
                        .build()
        );
    }
    private Action startShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.48);
            shooterRight.setPower(-0.48) ;
            return false;
        };
    }
//    private Action startPowerShooter() {
//        return telemetryPacket -> {
//            shooterLeft.setPower(-0.52);
//            shooterRight.setPower(-0.52) ;
//            return false;
//        };
//    }

    private Action stopShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(0);
            shooterRight.setPower(0);
            return false;
        };

    }

    private Action startRevShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(0.84);
            shooterRight.setPower(0.84);
            return false;
        };
    }
    private Action startIntake() {
        return telemetryPacket -> {
            intake.setPower(-0.8);
            return false;
        };

    }

    private Action stopIntake() {
        return telemetryPacket -> {
            intake.setPower(0);
            return false;
        };

    }

    private Action startballShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.52);
            shooterRight.setPower(-0.52) ;
            return false;
        };
    }
//    private Action runback() {
//        return telemetryPacket -> {
//            shooterLeft.setPower(0.4);
//            shooterRight.setPower(0.4) ;
//            intake.setPower(0.2);
//            return false;
//        };
//    }


}