package org.example.factory;

import org.example.model.enums.BotDifficultyType;
import org.example.strategy.BotPlayingStrategy;
import org.example.strategy.impl.EasyBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getStrategy(BotDifficultyType botDifficultyType){
        return new EasyBotPlayingStrategy();

        //TODO: impl oter difficlty strategy
    }
}
