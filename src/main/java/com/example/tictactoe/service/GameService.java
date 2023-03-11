package com.example.tictactoe.service;

import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;

public interface GameService {
    GameResponse getGameResponseById(Long gameId);

    GameResponse createNewGame(GameRequest gameRequest);

    GameResponse createNewStep(Long gameId, StepRequest stepRequest);
}
