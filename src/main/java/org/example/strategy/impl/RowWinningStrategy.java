package org.example.strategy.impl;

import org.example.model.Board;
import org.example.model.Move;
import org.example.model.Symbol;
import org.example.strategy.WinningStrategy;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy {
    Map<Integer, Map<Symbol, Integer>> count = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        Symbol symbol = lastMove.getPlayer().getSymbol();

        //note: getOrDefault does not put key-val pair -- use putIfAbsent for ini
        // belwoo logic is wrong: here rowCount map not inserted in count


//        Map<Symbol, Integer> rowCount = count.getOrDefault(row, new HashMap<>());
//        rowCount.put(symbol, rowCount.getOrDefault(symbol, 0)+1);

        //ini
        //aliter:
        //count.put(row, count.getOrDefault(row, new HashMap<>()); -- but unnessary inserts same obj again
        count.putIfAbsent(row, new HashMap<>());
        Map<Symbol, Integer> rowCount = count.get(row);

        //alter:
//        rowCount.putIfAbsent(symbol, 0);
//        rowCount.put(symbol, rowCount.get(symbol) + 1);
        rowCount.put(symbol, rowCount.getOrDefault(symbol, 0)+1); //here its ok -- ini  + inc




        if(rowCount.get(symbol) == board.getSize())
            return true;

        return false;
    }

    @Override
    public void handleUndo(Move move) {
        Map<Symbol, Integer> rowCount = count.get(move.getCell().getRow());
        Symbol symbol = move.getPlayer().getSymbol();
        rowCount.put(symbol, rowCount.get(symbol) - 1);
    }
}
