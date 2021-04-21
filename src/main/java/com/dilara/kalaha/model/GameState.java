package com.dilara.kalaha.model;

import org.springframework.stereotype.Component;

@Component
public class GameState {

    private Board currentBoardState;
    private boolean isLegalMove;
    private boolean isGameOver;
    private Player nextPlayer;
    private Player winner;

    public Board getCurrentBoardState() {
        return currentBoardState;
    }

    public void setCurrentBoardState(Board currentBoardState) {
        this.currentBoardState = currentBoardState;
    }

    public boolean isLegalMove() {
        return isLegalMove;
    }

    public void setLegalMove(boolean legalMove) {
        isLegalMove = legalMove;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
