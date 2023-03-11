package com.example.tictactoe.service;

import com.example.tictactoe.model.GameEntity;

public interface GameService {
    GameEntity getGameById(Long gameId);

    GameEntity createNewGame();
}
