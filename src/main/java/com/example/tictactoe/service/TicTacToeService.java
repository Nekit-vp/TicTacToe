package com.example.tictactoe.service;

import java.util.UUID;

import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;

public interface TicTacToeService {

    GameResponse getGameStateById(Long gameId);

    GameResponse createNewGame(GameRequest gameRequest);

    GameResponse createNewStep(Long gameId, StepRequest stepRequest);
}
