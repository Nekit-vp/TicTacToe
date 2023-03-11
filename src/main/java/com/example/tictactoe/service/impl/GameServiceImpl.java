package com.example.tictactoe.service.impl;

import java.time.Instant;
import java.util.stream.Stream;

import com.example.tictactoe.dto.enums.StatusGame;
import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.engine.TicTacToeArtificialIntelligence;
import com.example.tictactoe.exeption.TicTacToeException;
import com.example.tictactoe.mapper.GameMapper;
import com.example.tictactoe.model.GameEntity;
import com.example.tictactoe.model.StepEntity;
import com.example.tictactoe.repository.GameRepository;
import com.example.tictactoe.service.GameService;
import com.example.tictactoe.service.StepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.tictactoe.constant.ConstantMessage.GAME_BY_ID_NOT_FOUND_MESSAGE;
import static com.example.tictactoe.constant.ConstantMessage.POSITION_ALREADY_TAKEN_MESSAGE;
import static com.example.tictactoe.dto.enums.AuthorStep.COMPUTER;
import static com.example.tictactoe.dto.enums.AuthorStep.USER;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final StepService stepService;
    private final GameMapper gameMapper;
    private final TicTacToeArtificialIntelligence engine;

    @Override
    @Transactional(readOnly = true)
    public GameResponse getGameResponseById(Long gameId) {
        return gameMapper.toResponse(getGameEntityById(gameId));
    }

    @Override
    @Transactional
    public GameResponse createNewGame(GameRequest gameRequest) {
        GameEntity gameEntity = gameRepository.save(getNewGameEntity());

        if (COMPUTER.equals(gameRequest.getFirstStep())) {
            Integer positionComputer = engine.getNextStep(gameMapper.toDto(gameEntity));
            StepEntity stepEntity = stepService.createNewStep(COMPUTER, positionComputer, gameEntity);
            gameEntity.getSteps().add(stepEntity);
        }
        return gameMapper.toResponse(gameEntity);
    }

    @Override
    @Transactional
    public GameResponse createNewStep(Long gameId, StepRequest stepRequest) {
        GameEntity gameEntity = getGameEntityById(gameId);
        checkPositionAlreadyExist(gameEntity, stepRequest.getPosition());

        StepEntity stepUser = stepService.createNewStep(USER, stepRequest.getPosition(), gameEntity);
        gameEntity.getSteps().add(stepUser);

        Integer positionComputer = engine.getNextStep(gameMapper.toDto(gameEntity));
        StepEntity stepComputer = stepService.createNewStep(COMPUTER, positionComputer, gameEntity);
        gameEntity.getSteps().add(stepComputer);

        return gameMapper.toResponse(gameEntity);
    }

    private void checkPositionAlreadyExist(GameEntity gameEntity, Integer position) {
        Stream<Integer> stream = gameEntity.getSteps().stream().map(StepEntity::getPosition);
        if (stream.anyMatch(existPosition -> existPosition.equals(position))) {
            throw new TicTacToeException(POSITION_ALREADY_TAKEN_MESSAGE, FORBIDDEN, position);
        }
    }

    private GameEntity getGameEntityById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new TicTacToeException(GAME_BY_ID_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND, gameId));
    }

    private GameEntity getNewGameEntity() {
        return GameEntity.builder()
                .status(StatusGame.PROCESS)
                .createdAt(Instant.now())
                .build();
    }
}
