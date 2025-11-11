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


@Autonomous(name = "Trial Auto With TrajBuilder")
public class BlueSideTest extends LinearOpMode {

    private DcMotor shooterLeft, shooterRight;
    private DcMotor intake;
    private MecanumDrive drive;
    private static final double EST_TRAVEL_SEC = 3.5;


    @Override
    public void runOpMode() throws InterruptedException {
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intake");

        shooterLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);


        Pose2d beginPose = new Pose2d(-57, -44, Math.toRadians(-135));


        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .afterTime(Math.max(0, EST_TRAVEL_SEC - 2), startShooter())
                        .strafeToLinearHeading(new Vector2d(-40,-25), Math.toRadians(-125))
                        .afterTime(3.5,startIntake())
                        .waitSeconds(4)
                        .build()
        );
    }
    private Action startShooter() {
        return telemetryPacket -> {
            shooterLeft.setPower(-0.6);
            shooterRight.setPower(-0.6);
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
            intake.setPower(-0.7);
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
