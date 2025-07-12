package org.example.strategy.impl;

import org.example.model.Board;
import org.example.model.Move;
import org.example.model.Symbol;
import org.example.strategy.WinningStrategy;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy {
    Map<Integer, Map<Symbol, Integer>> count = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        int col = lastMove.getCell().getCol();
        Symbol symbol = lastMove.getPlayer().getSymbol();

        count.putIfAbsent(col, new HashMap<>());
        Map<Symbol, Integer> rowCount = count.get(col);

        rowCount.put(symbol, rowCount.getOrDefault(symbol, 0)+1); //here its ok -- ini  + inc




        if(rowCount.get(symbol) == board.getSize())
            return true;

        return false;
    }

    @Override
    public void handleUndo(Move move) {
        Map<Symbol, Integer> rowCount = count.get(move.getCell().getCol());
        Symbol symbol = move.getPlayer().getSymbol();
        rowCount.put(symbol, rowCount.get(symbol) - 1);
    }
}
