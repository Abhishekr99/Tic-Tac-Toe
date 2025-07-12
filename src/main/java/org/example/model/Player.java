package org.example.model;

import org.example.model.enums.PlayerType;

public abstract class Player {
    private int playerId;
    private String name;
    private Symbol symbol;
    private PlayerType playerType;

    public Player(int playerId, String name, Symbol symbol, PlayerType playerType) {
        this.playerId = playerId;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
    }

    public abstract Move makeMove(Board board);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
