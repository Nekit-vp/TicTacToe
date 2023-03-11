package com.example.tictactoe.controller;

import com.example.tictactoe.api.TicTacToeApi;
import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicTacToeController implements TicTacToeApi {

    private final GameService gameService;

    @Override
    public GameResponse getGameState(Long gameId) {
        return gameService.getGameResponseById(gameId);
    }

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        return gameService.createNewGame(gameRequest);
    }

    @Override
    public GameResponse createStep(Long gameId, StepRequest stepRequest) {
        return gameService.createNewStep(gameId, stepRequest);
    }
}
