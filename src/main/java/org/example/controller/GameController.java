package org.example.controller;

import org.example.model.Game;
import org.example.model.Player;
import org.example.model.enums.GameState;
import org.example.strategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int boardSize, List<Player> players, List<WinningStrategy> winningStrategies){
        return Game
                .getBuilder()
                .setBoardSize(boardSize)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();

    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public void undo(Game game){
        game.undo();
    }

    public GameState checkGameState(Game game){
        //check winner
        return  game.getGameState();
    }

    public void display(Game game) {
        game.displayBoard();
    }


}
