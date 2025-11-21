package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class cooldude {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-57, -44, 0))


                .strafeToLinearHeading(new Vector2d(-40, -25), Math.toRadians(-130))
                .strafeToLinearHeading(new Vector2d(-12, -31), Math.toRadians(-90))
                .strafeTo(new Vector2d(-12, -39))
                .strafeTo(new Vector2d(-12, -47))
                .strafeToLinearHeading(new Vector2d(-45, -25), Math.toRadians(-130))
                .strafeToLinearHeading(new Vector2d(12, -31), Math.toRadians(-90))
                .strafeTo(new Vector2d(12, -39))
                .strafeTo(new Vector2d(12, -47))
                .strafeToLinearHeading(new Vector2d(-45, -25), Math.toRadians(-130))
                .strafeToLinearHeading(new Vector2d(35, -31), Math.toRadians(-90))
                .strafeTo(new Vector2d(35, -39))
                .strafeTo(new Vector2d(35, -48))
                .strafeToLinearHeading(new Vector2d(-45, -25), Math.toRadians(-130))

                .build()


        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_BLACK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}