package com.example.tictactoe.controller;

import com.example.tictactoe.api.TicTacToeApi;
import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.service.TicTacToeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicTacToeController implements TicTacToeApi {

    private final TicTacToeService ticTacToeService;

    @Override
    public GameResponse getGameState(Long gameId) {
        return ticTacToeService.getGameStateById(gameId);
    }

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        return ticTacToeService.createNewGame(gameRequest);
    }

    @Override
    public GameResponse createStep(Long gameId, StepRequest stepRequest) {
        return ticTacToeService.createNewStep(gameId, stepRequest);
    }
}
