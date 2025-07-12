package org.example.model;

import org.example.model.enums.CellState;
import org.example.model.enums.GameState;
import org.example.strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private GameState gameState;
    //private Player nextPlayer;
    private int nextPlayerId;

    private Game(Builder builder){
        this.board = new Board(builder.boardSize);
        this.players = builder.players;
        this.moves = new ArrayList<>();
        this.winningStrategies =builder.winningStrategies;
        this.winner = null;
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerId = 0;
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerId() {
        return nextPlayerId;
    }

    public void setNextPlayerId(int nextPlayerId) {
        this.nextPlayerId = nextPlayerId;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public void displayBoard() {

        board.display();
    }

    public void makeMove() {
        Player curPlayer = players.get(nextPlayerId);
        Move move = null;
        //makeMOve and check if valid move
        do{
            move = curPlayer.makeMove(board);
        }while(!isValidMove(move));

        //upd cell on board
        int r = move.getCell().getRow();
        int c = move.getCell().getCol();
        Cell cellToUpd = board.getGrid().get(r).get(c);
        cellToUpd.setCellState(CellState.FILLED);
        cellToUpd.setSymbol(curPlayer.getSymbol());

        //add to move history
        moves.add(move);

        //check winner or draw
        if(checkWinner(move)){
            setGameState(GameState.SUCCESS);
            setWinner(curPlayer);

        }
        else if(moves.size() == board.getSize()*board.getSize()){
            setGameState(GameState.DRAW);
        }

        nextPlayerId = (nextPlayerId+1) % players.size();
    }

    private boolean checkWinner(Move move) {
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(board, move)){
                return true;
            }
        }
        return false;
    }

    private boolean isValidMove(Move move) {

        Cell cell = move.getCell();
        int row = cell.getRow();
        int col = cell.getCol();

        //wrong -- check existing board
//        if(cell.getCellState().equals(CellState.FILLED)){
//            return false;
//        }

        //will throw out of bound excep -- put this cond later -- order matters
//        if(board.getGrid().get(row).get(col).getCellState().equals(CellState.FILLED)){
//            System.out.println("Invalid Move: Cell is filled");
//            return false;
//        }
        if(row < 0 || row >= board.getSize()){
            System.out.println("Invalid Move: Outside of the board");
            return false;
        }
        if(col < 0 || col >= board.getSize()){
            System.out.println("Invalid Move: Outside of the board");
            return false;
        }

        if(board.getGrid().get(row).get(col).getCellState().equals(CellState.FILLED)){
            System.out.println("Invalid Move: Cell is filled");
            return false;
        }


        return true;
    }

    public void undo(){
        if(moves.size() == 0){
            System.out.println("No moves to undo");
            return;
        }

        Move lastMove =moves.get(moves.size()-1);
        int r = lastMove.getCell().getRow();
        int c = lastMove.getCell().getCol();

        moves.remove(lastMove);

        //upd cell on board
        Cell cellToUpd = board.getGrid().get(r).get(c);
        cellToUpd.setCellState(CellState.EMPTY);
        cellToUpd.setSymbol(null);

        setGameState(GameState.IN_PROGRESS);
        setWinner(null);

        //revert nextplayer
        nextPlayerId = (nextPlayerId - 1 + players.size()) % players.size();

        undoWinningStrategy(lastMove);

    }

    private void undoWinningStrategy(Move lastMove) {
        for(WinningStrategy winningStrategy : winningStrategies){
            winningStrategy.handleUndo(lastMove);
        }
    }

    public static class Builder{
        private int boardSize;
        private List<Player> players;
        //private List<Move> moves;
        private List<WinningStrategy> winningStrategies;
//        private Player winner;
//        private GameState gameState;
//        private Player nextPlayer;
        public Builder setBoardSize(int boardSize) {
            this.boardSize = boardSize;
            return this;
        }
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validate() {
            //board_size & players validation
            if(players.size() <= 1 || players.size() >= boardSize ){
                throw new RuntimeException("Players size and Dimension are not valid");
            }
            //uniq symbol validation
            Set<Character> uniqSymbol = new HashSet<>();
            for(Player p : players){
                char sym = p.getSymbol().getSym();
                if(uniqSymbol.contains(sym)){
                    throw new RuntimeException("Player symbols must be unique");
                }
                else{
                    uniqSymbol.add(sym);
                }
            }
        }

        public Game build(){
            this.validate();
            return new Game(this);
        }



    }
}
