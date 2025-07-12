package org.example.strategy;

import org.example.model.Board;
import org.example.model.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move lastMove);
    void handleUndo(Move move);
}
