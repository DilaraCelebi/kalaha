package com.dilara.kalaha.model;

import org.springframework.stereotype.Component;

@Component
public class Move {

    private Player currentPlayer;
    private Pit chosenPit;

    public Move(Player currentPlayer, Pit chosenPit) {
        this.currentPlayer = currentPlayer;
        this.chosenPit = chosenPit;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Pit getChosenPit() {
        return chosenPit;
    }

    public void setChosenPit(Pit chosenPit) {
        this.chosenPit = chosenPit;
    }

    public boolean isValid(){
        return (currentPlayer.equals(chosenPit.getOwner()) && chosenPit.getPitType() != PitType.STORE && chosenPit.getNumberOfStones() != 0 );
    }


}
