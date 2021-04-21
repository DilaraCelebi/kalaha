package com.dilara.kalaha.model;

import org.springframework.stereotype.Component;

@Component
public class Pit {

    private Integer id;
    private PitType pitType;
    private Player owner;
    private Integer numberOfStones = 0;

    public Integer getPitId() {
        return id;
    }

    public void setPitId(Integer pitId) {

        this.id = pitId;
    }

    public PitType getPitType() {
        return pitType;
    }

    public void setPitType(PitType pitType) {
        this.pitType = pitType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer getNumberOfStones() {
        return numberOfStones;
    }

    public void setNumberOfStones(Integer numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    public void addStones(int amount) {
        this.numberOfStones += amount;
    }

    public void removeStones(int amount) {
        this.numberOfStones -= amount;
    }
}
