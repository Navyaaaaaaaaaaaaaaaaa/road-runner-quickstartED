package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-57, 43, Math.toRadians(-60)))
                //afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(-40, -25), Math.toRadians(-130))
                       // .afterTime(1,startIntake())
                        .waitSeconds(3)
                        //.afterTime(1.5,stopShooter())
                        .strafeToLinearHeading(new Vector2d(-9, -31), Math.toRadians(-90))
                       // .afterTime(0,startIntake())
                        .strafeTo(new Vector2d(-9, -39))
                        .strafeTo(new Vector2d(-9, -47))
                       // .afterTime(0,stopIntake())
                       // .afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(-40, -25), Math.toRadians(-130))
                       // .afterTime(1,startIntake())
                        .waitSeconds(3)
                        //.afterTime(1.5,stopShooter())
                       // .afterTime(0,startIntake())
                        .strafeToLinearHeading(new Vector2d(12, -31), Math.toRadians(-90))
                        .strafeTo(new Vector2d(12, -39))
                        .strafeTo(new Vector2d(12, -47))
                       // .afterTime(0,startIntake())
                       // .afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(-40, -25), Math.toRadians(-130))
                       // .afterTime(1,startIntake())
                        .waitSeconds(3)
                       // .afterTime(1.5,stopShooter())
                       // .afterTime(0,startIntake())
                        .strafeToLinearHeading(new Vector2d(35, -31), Math.toRadians(-90))
                        .strafeTo(new Vector2d(35, -39))
                        .strafeTo(new Vector2d(35, -48))
                       // .afterTime(0,stopIntake())
                       // .afterTime(Math.max(0, EST_TRAVEL_SEC-2.5), startShooter())
                        .strafeToLinearHeading(new Vector2d(-40, -25), Math.toRadians(-130))
                       // .afterTime(1,startIntake())
                        .waitSeconds(3)
                       // .afterTime(1.5,stopShooter())
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_BLACK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}