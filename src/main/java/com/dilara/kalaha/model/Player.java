package com.dilara.kalaha.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Player {

    public enum PlayerId {
        PLAYER_ONE, PLAYER_TWO, NONE
    }

    private PlayerId id;
    private Integer storeId;

    public Player() {
        this.id = PlayerId.NONE;
        this.storeId = -1;
    }

    public Player(PlayerId id, Integer storeId){
        this.id = id;
        this.storeId = storeId;
    }

    public PlayerId getId() {
        return id;
    }

    public void setId(PlayerId id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        Player player = (Player) o;
        return id == player.id && storeId.equals(player.storeId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,storeId);

    }

}
