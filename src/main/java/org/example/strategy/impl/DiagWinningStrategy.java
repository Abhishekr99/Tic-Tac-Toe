package org.example.strategy.impl;

import org.example.model.Board;
import org.example.model.Move;
import org.example.model.Symbol;
import org.example.strategy.WinningStrategy;

import java.util.HashMap;
import java.util.Map;

public class DiagWinningStrategy implements WinningStrategy {

    Map<Symbol, Integer> diagMap = new HashMap<>();
    Map<Symbol, Integer> offDiagMap = new HashMap<>();

    public DiagWinningStrategy(int boardSize) {
        this.boardSize = boardSize;
    }



    int boardSize;


    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        Symbol symbol = lastMove.getPlayer().getSymbol();

        if(row == col){
            //its main diag
            diagMap.put(symbol, diagMap.getOrDefault(symbol, 0) + 1);
            if(diagMap.get(symbol) == boardSize)
                return true;

        }
        if ((row + col) == boardSize-1) { //shd not be else-if as centre-ele part of both diag & off-diag
            offDiagMap.put(symbol, offDiagMap.getOrDefault(symbol, 0) + 1);
            if(offDiagMap.get(symbol) == boardSize)
                return true;
        }



        return false;

    }

    @Override
    public void handleUndo(Move lastMove) {
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        Symbol symbol = lastMove.getPlayer().getSymbol();

        if(row == col){
            //its main diag
            diagMap.put(symbol, diagMap.getOrDefault(symbol, 0) - 1);

        }
        else if ((row + col) == boardSize-1) {
            offDiagMap.put(symbol, offDiagMap.getOrDefault(symbol, 0) - 1);
        }

    }
}
