package org.example.model;

import org.example.model.enums.PlayerType;

import java.util.Scanner;

public class HumanPlayer extends Player{

    public HumanPlayer(int playerId, String name, Symbol symbol, PlayerType playerType) {
        super(playerId, name, symbol, playerType);
    }

    @Override
    public Move makeMove(Board board) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Its "+ getName() +" turn...");
        System.out.print("Enter row: ");
        int row = sc.nextInt();
        System.out.print("Enter col: ");
        int col = sc.nextInt();

        Cell cell = new Cell(row, col);

        return new Move(cell, this);

    }


}
