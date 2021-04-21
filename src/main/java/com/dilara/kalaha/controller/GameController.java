package com.dilara.kalaha.controller;

import com.dilara.kalaha.model.GameState;
import com.dilara.kalaha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping("/start")
    public GameState start(){
        return gameService.initializeGame();
    }

    @RequestMapping("/move")
    public GameState move(@RequestParam("playerId") String playerId,
                          @RequestParam("pitId") Integer pitId) {
        return gameService.makeMove(playerId,pitId);
    }
}
