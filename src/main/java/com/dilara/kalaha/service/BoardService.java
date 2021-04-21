package com.dilara.kalaha.service;

import com.dilara.kalaha.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private Board board;
    private PlayerService playerService;
    private boolean playerContinues = false;

    @Value("#{new Integer ('${PITS_PER_PLAYER}')}")
    private Integer PITS_PER_PLAYER;

    @Value("#{new Integer ('${BEGINNING_STONE_AMOUNT}')}")
    private Integer BEGINNING_STONE_AMOUNT;

    @Value("#{new Integer ('${TOTAL_PITS}')}")
    private Integer TOTAL_PITS;

    @Value("#{new Integer ('${P1_STORE_PIT}')}")
    private Integer P1_STORE_PIT;

    @Value("#{new Integer ('${P2_STORE_PIT}')}")
    private Integer P2_STORE_PIT;

    @Autowired
    public BoardService(Board board, PlayerService playerService) {
        this.board = board;
        this.playerService = playerService;
    }

    public Board getBoard() {
        return board;
    }

    // checks if chosen pit is valid and in range
    public Pit getPitById(int id) throws IndexOutOfBoundsException {
        try {
            return board.getPit(id);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(String.format("Id %d is invalid", id));
        }
    }

    /**
     * create pits according to their type and owner.
     * if it's a store or regular pit
     * if it belongs to first player or second
     */

    public Pit initializePit(int pitId) {
        Pit pit = new Pit();
        pit.setPitId(pitId);
        if (pitId <= P1_STORE_PIT) {
            pit.setOwner(new Player(Player.PlayerId.PLAYER_ONE, P1_STORE_PIT));
            if (pitId < P1_STORE_PIT) {
                pit.setNumberOfStones(BEGINNING_STONE_AMOUNT);
                pit.setPitType(PitType.NORMAL);
            } else {
                pit.setNumberOfStones(0);
                pit.setPitType(PitType.STORE);
            }
        } else {
            pit.setOwner(new Player(Player.PlayerId.PLAYER_TWO, P2_STORE_PIT));
            if (pitId < P2_STORE_PIT) {
                pit.setNumberOfStones(BEGINNING_STONE_AMOUNT);
                pit.setPitType(PitType.NORMAL);
            } else {
                pit.setNumberOfStones(0);
                pit.setPitType(PitType.STORE);
            }
        }
        return pit;
    }

    public void initializeBoard() {
        List<Pit> createdBoard = new ArrayList<>();
        for (int i = 0; i < TOTAL_PITS; i++) {
            createdBoard.add(initializePit(i));
            board.setPits(createdBoard);
        }
    }

    /**
     * @return true if one of the player's pits are all empty
     * false if players' pits are still not empty
     */

    public boolean areAllPitsInRowEmpty(int beginning, int end) {
        for (int i = beginning; i < end; i++) {
            if (board.getPit(i).getNumberOfStones() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean doesPlayerContinue() {
        return playerContinues;
    }

    /**
     * @return the amount of the stones from store of the player
     */

    public int getAmountFromStore(Player player) {
        int storeId = player.getStoreId();
        Pit store = board.getPit(storeId);
        return store.getNumberOfStones();
    }


    // Player who still has stones in his pits, collects them in his store pit

    public void getRemainingStones() {
        int CountP1 = 0;
        int CountP2 = 0;
        for (int i = 0; i < P1_STORE_PIT; i++) {
            CountP1 += board.getPit(i).getNumberOfStones();
            board.getPit(P1_STORE_PIT).addStones(CountP1);
        }
        for (int i = P1_STORE_PIT + 1; i < P2_STORE_PIT; i++) {
            CountP2 += board.getPit(i).getNumberOfStones();
            board.getPit(P2_STORE_PIT).addStones(CountP2);
        }
    }

    /**
     * If last stone lands in player's own pit,
     * capture that one stone and other stones from opposite pit in his store
     */

    public void captureStones(Player currentPlayer, Pit lastPit) {
        // means that last pit wasn't empty and it has more than 1 stone
        if (lastPit.getNumberOfStones() != 1)
            return;

        // a validation to check if player landed in his opponent's empty pit or his own empty pit
        int bottomLimit = currentPlayer.getStoreId().equals(P1_STORE_PIT) ? 0 : P1_STORE_PIT + 1;
        int topLimit = currentPlayer.getStoreId().equals(P1_STORE_PIT) ? P1_STORE_PIT : P2_STORE_PIT;
        int oppositePitId = TOTAL_PITS - lastPit.getPitId() - 2;

        // if oppositePitId is between the limits, it means player landed in his opponents empty pit

        if ((oppositePitId >= bottomLimit && oppositePitId < topLimit) || (board.getPit(oppositePitId).getNumberOfStones() == 0)) {
            return;
        }

        int capturedStones = board.getPit(oppositePitId).getNumberOfStones() + 1; // 1 is players own stone
        board.getPit(oppositePitId).setNumberOfStones(0);
        board.getPit(lastPit.getPitId()).setNumberOfStones(0);
        board.getPit(currentPlayer.getStoreId()).addStones(capturedStones);
    }

    public void updateBoard(Move move) {
        Pit startingPit = move.getChosenPit();
        int nextPitId = startingPit.getPitId();
        int currentStoneAmount = startingPit.getNumberOfStones();
        Pit nextPit = null;

        while (currentStoneAmount > 0) {
            nextPitId = (nextPitId + 1) % TOTAL_PITS;
            nextPit = board.getPit(nextPitId);

            if ((nextPit.getPitType().equals(PitType.NORMAL)) || nextPit.getOwner().equals(move.getCurrentPlayer())) {
                nextPit.addStones(1);
                board.getPit(startingPit.getPitId()).removeStones(1);
                currentStoneAmount--;
            }
        }

        if (nextPit == null) {
            throw new NullPointerException("next pit");
        }
        if (nextPit.getPitType().equals(PitType.NORMAL)) {
            captureStones(move.getCurrentPlayer(), nextPit);
            playerContinues = false;
        } else {
            playerContinues = true; // if player landed his home, keeps playing
        }


    }


}
