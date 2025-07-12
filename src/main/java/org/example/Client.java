package org.example;

import org.example.controller.GameController;
import org.example.model.*;
import org.example.model.enums.BotDifficultyType;
import org.example.model.enums.GameState;
import org.example.model.enums.PlayerType;
import org.example.strategy.WinningStrategy;
import org.example.strategy.impl.ColWinningStrategy;
import org.example.strategy.impl.DiagWinningStrategy;
import org.example.strategy.impl.RowWinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Client {
    public static void main(String[] args) {

        /*
            1. ini Game dep:
                - startGame(): creates Game via builder pattern
                - while GameState is IN_PROGRESS: makeMove()/undo() -- re-display() & checkWinner() via winStrategy()
                - GameState is SUCCESS: store winner & show

            2. GameController:
                - indp of Game
                - handles everything via Game obj or game-id

         */

        GameController gameController = new GameController();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter board size: ");
        int boardSize = sc.nextInt();
        System.out.print("Enter No. of Players: ");
        int noPlayers = sc.nextInt();

        List<Player> players = new ArrayList<>();

        for(int i=0; i<noPlayers; i++){
            Player player;

            System.out.print("Enter player name: ");
            String playerName = sc.next();

            System.out.print("Player symbol: ");
            char sym = sc.next().charAt(0);
            Symbol symbol = new Symbol(sym);

            System.out.print("Is player BOT [y/n]: ");
            char isBot = sc.next().charAt(0);
            if(isBot == 'y'){
                player = new BotPlayer(i, playerName, symbol, PlayerType.BOT, BotDifficultyType.EASY);
            }
            else{
                player = new HumanPlayer(i, playerName, symbol, PlayerType.HUMAN);
            }

            players.add(player);
        }

        List<WinningStrategy> winningStrategies = List.of(
                new RowWinningStrategy(),
                new ColWinningStrategy(),
                new DiagWinningStrategy(boardSize)
        );

        Game game = gameController.startGame(boardSize, players, winningStrategies);

        //disp board
        gameController.display(game);

        while(game.getGameState().equals(GameState.IN_PROGRESS)){
            //makeMove -- checkwinner, ask for undo
            gameController.makeMove(game);
            gameController.display(game);

            if(game.getMoves().get(game.getMoves().size() - 1).getPlayer().getPlayerType().equals(PlayerType.HUMAN)){
                System.out.println("Do you want to undo? Press 1 to continue and 2 to undo");
                int undo = sc.nextInt();

                if(undo == 2){
                    gameController.undo(game);
                }
            }
        }

        if(gameController.checkGameState(game) == GameState.SUCCESS){
            System.out.println(game.getWinner().getName() + " is the Winner!");
        }else if(gameController.checkGameState(game) == GameState.DRAW){
            System.out.println("Game is draw!");
        }


    }
}