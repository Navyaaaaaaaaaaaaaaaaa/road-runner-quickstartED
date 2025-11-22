package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.MeepMeep.Background;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingRRAuton {
    public MeepMeepTestingRRAuton() {
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = (new DefaultBotBuilder(meepMeep)).setConstraints((double)60.0F, (double)60.0F, Math.toRadians((double)180.0F), Math.toRadians((double)180.0F), (double)15.0F).build();
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((double)61.0F, (double)-8.0F, Math.toRadians((double)90.0F))).lineToY((double)40.0F).build());
        meepMeep.setBackground(Background.FIELD_DECODE_JUICE_BLACK).setDarkMode(true).setBackgroundAlpha(0.95F).addEntity(myBot).start();
    }
}
