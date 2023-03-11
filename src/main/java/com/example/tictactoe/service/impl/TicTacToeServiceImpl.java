package com.example.tictactoe.service.impl;

import java.util.stream.Stream;

import com.example.tictactoe.dto.GameDto;
import com.example.tictactoe.dto.StepDto;
import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.engine.TicTacToeArtificialIntelligence;
import com.example.tictactoe.exeption.TicTacToeException;
import com.example.tictactoe.mapper.GameMapper;
import com.example.tictactoe.model.GameEntity;
import com.example.tictactoe.model.StepEntity;
import com.example.tictactoe.service.GameService;
import com.example.tictactoe.service.StepService;
import com.example.tictactoe.service.TicTacToeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.tictactoe.constant.ConstantMessage.POSITION_ALREADY_TAKEN_MESSAGE;
import static com.example.tictactoe.dto.enums.AuthorStep.COMPUTER;
import static com.example.tictactoe.dto.enums.AuthorStep.USER;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@Service
@Slf4j
public class TicTacToeServiceImpl implements TicTacToeService {

    private final StepService stepService;

    private final GameService gameService;

    private final TicTacToeArtificialIntelligence engine;

    private final GameMapper gameMapper;

    @Override
    public GameResponse getGameStateById(Long gameId) {
        return getGameResponse(gameId);
    }

    @Override
    public GameResponse createNewGame(GameRequest gameRequest) {
        GameEntity gameEntity = gameService.createNewGame();

        if (COMPUTER.equals(gameRequest.getFirstStep())) {
            Integer positionComputer = engine.getNextStep(gameMapper.toDto(gameEntity));
            stepService.createNewStep(COMPUTER, positionComputer, gameEntity);
        }

        return getGameResponse(gameEntity.getId());
    }

    @Override
    public GameResponse createNewStep(Long gameId, StepRequest stepRequest) {
        GameEntity gameEntity = gameService.getGameById(gameId);

        Stream<Integer> stream = gameEntity.getSteps().stream().map(StepEntity::getPosition);
        if (stream.anyMatch(position -> position.equals(stepRequest.getPosition()))) {
            throw new TicTacToeException(POSITION_ALREADY_TAKEN_MESSAGE, FORBIDDEN, stepRequest.getPosition());
        }

        stepService.createNewStep(USER, stepRequest.getPosition(), gameEntity);
        GameDto gameDto = gameMapper.toDto(gameEntity);
        gameDto.getSteps().add(StepDto.builder().author(USER).position(stepRequest.getPosition()).build());
        Integer positionComputer = engine.getNextStep(gameDto);
        stepService.createNewStep(COMPUTER, positionComputer, gameEntity);

        return getGameResponse(gameEntity.getId());
    }

    private GameResponse getGameResponse(Long gameId) {
        return gameMapper.toResponse(gameService.getGameById(gameId));
    }
}
