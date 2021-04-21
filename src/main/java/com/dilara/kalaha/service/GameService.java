package com.dilara.kalaha.service;

import com.dilara.kalaha.model.GameState;
import com.dilara.kalaha.model.Move;
import com.dilara.kalaha.model.Pit;
import com.dilara.kalaha.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private BoardService boardService;
    private PlayerService playerService;
    private GameState gameState;


    @Autowired
    public GameService(BoardService boardService, PlayerService playerService, GameState gameState) {
        this.boardService = boardService;
        this.playerService = playerService;
        this.gameState = gameState;
    }

    @Value("#{new Integer ('${P1_STORE_PIT}')}")
    private Integer P1_STORE_PIT;

    @Value("#{new Integer ('${P2_STORE_PIT}')}")
    private Integer P2_STORE_PIT;


    public GameState initializeGame() {
        boardService.initializeBoard();
        playerService.initializePlayers();
        gameState.setCurrentBoardState(boardService.getBoard());
        gameState.setGameOver(false);
        gameState.setLegalMove(true);
        gameState.setNextPlayer(playerService.getPlayer1());
        gameState.setWinner(playerService.getEmptyPlayer());
        return gameState;
    }

    public boolean isGameOver() {
        return boardService.areAllPitsInRowEmpty(0, P1_STORE_PIT) ||
                boardService.areAllPitsInRowEmpty(P1_STORE_PIT + 1, P2_STORE_PIT);
    }

    public Player calculateWinner() {
        boardService.getRemainingStones();
        int countP1 = boardService.getAmountFromStore(playerService.getPlayer1());
        int countP2 = boardService.getAmountFromStore(playerService.getPlayer2());
        return countP1 > countP2 ? playerService.getPlayer1() :
                countP2 > countP1 ? playerService.getPlayer2() :
                        playerService.getEmptyPlayer();
    }

    private Player playerTurns(Player currentPlayer) {
        return (currentPlayer.getId() == Player.PlayerId.PLAYER_ONE) ?
                playerService.getPlayer2() :
                playerService.getPlayer1();
    }


    public GameState makeMove(String playerId, int pitId) throws IllegalArgumentException, IndexOutOfBoundsException {
        Player player = playerService.getPlayerById(Player.PlayerId.valueOf(playerId));
        if (player.getId() == Player.PlayerId.NONE) {
            throw new IllegalArgumentException("Invalid Player Id");
        }

        Pit pit = boardService.getPitById(pitId);
        Move move = new Move(player, pit);
        if (move.isValid()) {
            gameState.setLegalMove(true);
            boardService.updateBoard(move);
            gameState.setCurrentBoardState(boardService.getBoard());
            if (isGameOver()) {
                gameState.setGameOver(true);
                gameState.setWinner(calculateWinner());
            }

            if (!boardService.doesPlayerContinue()) {
                gameState.setNextPlayer(playerTurns(gameState.getNextPlayer()));
            }
        } else {
            gameState.setLegalMove(false);
        }

        return gameState;
    }

}
