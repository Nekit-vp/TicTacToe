package com.example.tictactoe.service.impl;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.tictactoe.dto.enums.AuthorStep;
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
import com.example.tictactoe.utils.CheckWonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.tictactoe.constant.ConstantMessage.GAME_BY_ID_NOT_FOUND_MESSAGE;
import static com.example.tictactoe.constant.ConstantMessage.GAME_EMPTY_MESSAGE;
import static com.example.tictactoe.constant.ConstantMessage.GAME_FINISHED_MESSAGE;
import static com.example.tictactoe.constant.ConstantMessage.POSITION_ALREADY_TAKEN_MESSAGE;
import static com.example.tictactoe.dto.enums.AuthorStep.COMPUTER;
import static com.example.tictactoe.dto.enums.AuthorStep.USER;
import static com.example.tictactoe.dto.enums.StatusGame.FINISHED_DEAD_HEAT;
import static com.example.tictactoe.dto.enums.StatusGame.PROCESS;
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
        checkGameInProcess(gameEntity);
        checkPositionAlreadyExist(gameEntity, stepRequest.getPosition());

        StepEntity stepUser = stepService.createNewStep(USER, stepRequest.getPosition(), gameEntity);
        gameEntity.getSteps().add(stepUser);
        if (checkGameFinishedWithWon(USER, gameEntity)) {
            gameEntity.setStatus(StatusGame.FINISHED_USER_WON);
            return gameMapper.toResponse(gameRepository.save(gameEntity));
        }

        if (gameIsDeadHeat(gameEntity)) {
            gameEntity.setStatus(FINISHED_DEAD_HEAT);
            return gameMapper.toResponse(gameRepository.save(gameEntity));
        }

        Integer positionComputer = engine.getNextStep(gameMapper.toDto(gameEntity));
        StepEntity stepComputer = stepService.createNewStep(COMPUTER, positionComputer, gameEntity);
        gameEntity.getSteps().add(stepComputer);
        if (checkGameFinishedWithWon(COMPUTER, gameEntity)) {
            gameEntity.setStatus(StatusGame.FINISHED_COMPUTER_WON);
            return gameMapper.toResponse(gameRepository.save(gameEntity));
        }

        if (gameIsDeadHeat(gameEntity)) {
            gameEntity.setStatus(FINISHED_DEAD_HEAT);
            return gameMapper.toResponse(gameRepository.save(gameEntity));
        }
        return gameMapper.toResponse(gameEntity);
    }

    @Override
    @Transactional
    public GameResponse deleteStep(Long gameId) {
        GameEntity gameEntity = getGameEntityById(gameId);
        checkGameInProcess(gameEntity);
        checkEmptyGame(gameEntity);

        StepEntity lastStep = findLastStep(gameEntity.getSteps());
        gameEntity.getSteps().remove(lastStep);
        stepService.deleteStep(lastStep.getId());

        if (!gameEntity.getSteps().isEmpty()) {
            StepEntity lastStep2 = findLastStep(gameEntity.getSteps());
            gameEntity.getSteps().remove(lastStep2);
            stepService.deleteStep(lastStep2.getId());
        }
        return gameMapper.toResponse(gameEntity);
    }

    private StepEntity findLastStep(List<StepEntity> steps) {
        return steps.stream()
                .max(Comparator.comparing(StepEntity::getCreatedAt))
                .orElseThrow(() -> new TicTacToeException("Step not found", HttpStatus.NOT_FOUND));
    }

    private void checkEmptyGame(GameEntity gameEntity) {
        if (gameEntity.getSteps().isEmpty()) {
            throw new TicTacToeException(GAME_EMPTY_MESSAGE, FORBIDDEN, gameEntity.getId());
        }
    }

    private boolean gameIsDeadHeat(GameEntity gameEntity) {
        return gameEntity.getSteps().size() == 9;
    }

    private void checkGameInProcess(GameEntity gameEntity) {
        if (!PROCESS.equals(gameEntity.getStatus())) {
            throw new TicTacToeException(GAME_FINISHED_MESSAGE, FORBIDDEN, gameEntity.getId(), gameEntity.getStatus());
        }
    }

    private boolean checkGameFinishedWithWon(AuthorStep author, GameEntity gameEntity) {
        Set<Integer> positionByAuthor = gameEntity.getSteps().stream()
                .filter(step -> author.equals(step.getAuthor()))
                .map(StepEntity::getPosition)
                .collect(Collectors.toSet());
        return CheckWonUtil.checkWinningPosition(positionByAuthor);
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
                .status(PROCESS)
                .createdAt(Instant.now())
                .build();
    }
}
