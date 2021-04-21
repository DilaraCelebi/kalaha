package com.dilara.kalaha.service;

import com.dilara.kalaha.model.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private Player player1;
    private Player player2;

    @Value("#{new Integer ('${P1_STORE_PIT}')}")
    private Integer P1_STORE_PIT;

    @Value("#{new Integer ('${P2_STORE_PIT}')}")
    private Integer P2_STORE_PIT;

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void initializePlayers() {
        player1 = new Player(Player.PlayerId.PLAYER_ONE, P1_STORE_PIT);
        player2 = new Player(Player.PlayerId.PLAYER_TWO, P2_STORE_PIT);
    }
    public Player getEmptyPlayer() {
        return new Player(Player.PlayerId.NONE, -1);
    }
    public Player getPlayerById(Player.PlayerId id){
        switch (id) {
            case PLAYER_ONE:
                return player1;
            case PLAYER_TWO:
                return player2;
        }
        return getEmptyPlayer();
    }
}
