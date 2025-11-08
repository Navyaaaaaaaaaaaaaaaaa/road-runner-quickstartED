package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "BlueHumanAuto")
public class BlueHumanAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drivetrain
        MecanumDrive drive = new MecanumDrive(hardwareMap);

        waitForStart();

        // Start at (-57, 44, -90 degrees)
        drive.setPoseEstimate(new Pose2d(-57, 44, Math.toRadians(-90)));

        // 1st strafe to (-15, 0)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(-15, 0))
                        .build()
        );
        sleep(2000);

        // 1st strafe to (52, 52)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(52, 52))
                        .build()
        );
        sleep(500);

        // 2nd strafe to (-15, 0)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(-15, 0))
                        .build()
        );
        sleep(2000);

        // 2nd strafe to (52, 52)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(52, 52))
                        .build()
        );
        sleep(500);

        // 3rd strafe to (-15, 0)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(-15, 0))
                        .build()
        );
        sleep(2000);

        // 3rd strafe to (52, 52)
        drive.followTrajectorySync(
                drive.trajectoryBuilder(drive.getPoseEstimate())
                        .strafeTo(new Vector2d(52, 52))
                        .build()
        );
        sleep(500);
    }
}
