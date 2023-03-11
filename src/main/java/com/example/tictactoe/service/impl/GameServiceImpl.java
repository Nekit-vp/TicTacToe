package com.example.tictactoe.service.impl;

import com.example.tictactoe.dto.enums.StatusGame;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.exeption.TicTacToeException;
import com.example.tictactoe.mapper.GameMapper;
import com.example.tictactoe.model.GameEntity;
import com.example.tictactoe.repository.GameRepository;
import com.example.tictactoe.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.tictactoe.constant.ConstantMessage.GAME_BY_ID_NOT_FOUND_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    @Transactional(readOnly = true)
    public GameEntity getGameById(Long gameId) {
        return getGameEntityById(gameId);
    }

    @Override
    @Transactional
    public GameEntity createNewGame() {
        GameEntity gameEntity = GameEntity.builder()
                .status(StatusGame.PROCESS)
                .build();
        GameEntity gameEntity1 = gameEntity =  gameRepository.save(gameEntity);
        return gameEntity1;
    }

    private GameEntity getGameEntityById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new TicTacToeException(GAME_BY_ID_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND, gameId));
    }
}
