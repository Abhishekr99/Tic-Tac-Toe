package org.example.strategy.impl;

import org.example.model.Board;
import org.example.model.Cell;
import org.example.model.enums.CellState;
import org.example.strategy.BotPlayingStrategy;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Cell makeMove(Board board) {
        List<List<Cell>> grid = board.getGrid();
        for(List<Cell> row: grid){
            for(Cell cell : row){
                if(cell.getCellState().equals(CellState.EMPTY)){
                    return new Cell(cell.getRow(), cell.getCol()); //why new cell ?
                }
            }
        }
        return null;
    }
}
