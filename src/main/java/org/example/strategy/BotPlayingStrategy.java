package org.example.strategy;

import org.example.model.Board;
import org.example.model.Cell;

public interface BotPlayingStrategy {
    Cell makeMove(Board board);
}
