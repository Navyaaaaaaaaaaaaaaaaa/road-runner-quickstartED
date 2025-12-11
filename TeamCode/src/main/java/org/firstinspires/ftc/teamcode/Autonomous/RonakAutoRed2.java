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


@Autonomous(name = "RonakAutoRed2")
public class RonakAutoRed2 extends LinearOpMode {

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
        shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        Pose2d beginPose = new Pose2d(60, 10, Math.toRadians(180));


        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        // shooting ----------------------------------------------------------------
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(55, 9), Math.toRadians(160))
                        .afterTime(1,startIntake())
                        .waitSeconds(2)
                        .afterTime(1.5,stopShooter())

                        //1st cycle ----------------------------------------------------------------

                        .afterTime(0,startIntake())
                        .afterTime(0,startRevShooter())
                        .waitSeconds(0.5)
                        .strafeToLinearHeading(new Vector2d(60, 50), Math.toRadians(90))
                        .afterTime(1,stopIntake())
                        .afterTime(0,stopShooter())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-1.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(55, 9), Math.toRadians(156))
                        .waitSeconds(1)
                        .afterTime(0.5,startIntake())
                        .waitSeconds(3)
                        .afterTime(0.5,stopShooter())
                        .afterTime(0,stopIntake())

                        //2nd cycle ----------------------------------------------------------------

                        .afterTime(0,startIntake())
                        .afterTime(0,startRevShooter())
                        .waitSeconds(0.5)
                        .strafeToLinearHeading(new Vector2d(60, 50), Math.toRadians(90))
                        .afterTime(0.8,stopIntake())
                        .afterTime(0,stopShooter())
                        .afterTime(Math.max(0, EST_TRAVEL_SEC-1.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(55, 9), Math.toRadians(156))
                        .waitSeconds(1)
                        .afterTime(0.5,startIntake())
                        .waitSeconds(3)
                        .afterTime(0.5,stopShooter())
                        .afterTime(0,stopIntake())
                        .build()
        );
    }
    private Action startShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.83);
            shooterRight.setPower(-0.83) ;
            return false;
        };
    }
    private Action startPowerShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.54);
            shooterRight.setPower(-0.54) ;
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

    private Action startRevShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(0.98);
            shooterRight.setPower(0.98) ;
            return false;
        };
    }
    private Action startIntake() {
        return telemetryPacket -> {
            intake.setPower(-0.6);
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
