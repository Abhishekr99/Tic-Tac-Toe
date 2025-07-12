package org.example.model;

import org.example.factory.BotPlayingStrategyFactory;
import org.example.model.enums.BotDifficultyType;
import org.example.model.enums.PlayerType;
import org.example.strategy.BotPlayingStrategy;
import org.example.strategy.impl.EasyBotPlayingStrategy;

public class BotPlayer extends Player{
    private BotDifficultyType botDifficultyType;
    private BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(int playerId, String name, Symbol symbol, PlayerType playerType, BotDifficultyType botDifficultyType) {
        super(playerId, name, symbol, playerType);
        botPlayingStrategy = BotPlayingStrategyFactory.getStrategy(botDifficultyType);
    }

    @Override
    public Move makeMove(Board board) {
        Cell cell =  botPlayingStrategy.makeMove(board);
        return new Move(cell, this);

    }


    public BotDifficultyType getBotDifficultyType() {
        return botDifficultyType;
    }

    public void setBotDifficultyType(BotDifficultyType botDifficultyType) {
        this.botDifficultyType = botDifficultyType;
    }
}
