
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

@Autonomous(name = "Trial Auto ", group = "RoadRunner")
public class Samanyu extends LinearOpMode {

    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private static final double EST_TRAVEL_SEC = 3.5;
    private static final double WAIT_TIME = 4;
    private static final double PREP_TIME = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        Pose2d beginPose = new Pose2d(-57, -54, Math.toRadians(-135));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        double shooterLead = Math.max(0.0, EST_TRAVEL_SEC - PREP_TIME);

        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        // SHOOTER #1
                        .afterTime(shooterLead, startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(130))
                        .afterTime(0, startIntake())
                        .afterTime(WAIT_TIME, stopShooter())
                        .afterTime(0, stopIntake())

                        // CLUSTER 1
                        .afterTime(0, startIntake())
                        .strafeToLinearHeading(new Vector2d(-12, -31), Math.toRadians(-90))
                        .strafeTo(new Vector2d(-12, -39))
                        .strafeTo(new Vector2d(-12, -47))
                        .afterTime(0, stopIntake())

                        // SHOOTER #2
                        .afterTime(shooterLead, startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(130))
                        .afterTime(0, startIntake())
                        .afterTime(WAIT_TIME, stopShooter())
                        .afterTime(0, stopIntake())

                        // CLUSTER 2
                        .afterTime(0, startIntake())
                        .strafeToLinearHeading(new Vector2d(12, -31), Math.toRadians(-90))
                        .strafeTo(new Vector2d(12, -39))
                        .strafeTo(new Vector2d(12, -47))
                        .afterTime(0, stopIntake())

                        // SHOOTER #3
                        .afterTime(shooterLead, startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(130))
                        .afterTime(0, startIntake())
                        .afterTime(WAIT_TIME, stopShooter())
                        .afterTime(0, stopIntake())

                        // CLUSTER 3
                        .afterTime(0, startIntake())
                        .strafeToLinearHeading(new Vector2d(35, -31), Math.toRadians(-90))
                        .strafeTo(new Vector2d(35, -39))
                        .strafeTo(new Vector2d(35, -48))
                        .afterTime(0, stopIntake())

                        // SHOOTER #4
                        .afterTime(shooterLead, startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(130))
                        .afterTime(0, startIntake())
                        .afterTime(WAIT_TIME, stopShooter())
                        .afterTime(0, stopIntake())

                        // CLUSTER 4
                        .afterTime(0, startIntake())
                        .strafeToLinearHeading(new Vector2d(-12, 31), Math.toRadians(90))
                        .strafeTo(new Vector2d(-12, 39))
                        .strafeTo(new Vector2d(-12, 48))
                        .strafeToLinearHeading(new Vector2d(1, 57), Math.toRadians(180))
                        .afterTime(0, stopIntake())

                        // FINAL SHOOTER
                        .afterTime(shooterLead, startShooter())
                        .strafeToLinearHeading(new Vector2d(-15, 0), Math.toRadians(130))
                        .afterTime(0, startIntake())
                        .afterTime(WAIT_TIME, stopShooter())
                        .afterTime(0, stopIntake())

                        .build()
        );
    }

    private Action startShooter() {
        return packet -> {
            shooterLeft.setPower(-0.5);
            shooterRight.setPower(-0.5);
            return false;
        };
    }

    private Action stopShooter() {
        return packet -> {
            shooterLeft.setPower(0);
            shooterRight.setPower(0);
            return false;
        };
    }

    private Action startIntake() {
        return packet -> {
            intake.setPower(-1);
            return false;
        };
    }

    private Action stopIntake() {
        return packet -> {
            intake.setPower(0);
            return false;
        };
    }
}