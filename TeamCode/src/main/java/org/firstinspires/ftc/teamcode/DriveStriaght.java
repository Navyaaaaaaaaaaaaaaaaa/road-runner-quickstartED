package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class DriveStriaght extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {


        Pose2d startPose = new Pose2d(60, -8, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);




        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(startPose)
                        .strafeTo(new Vector2d(25,-8))

                        .build()
        );



    }
}
