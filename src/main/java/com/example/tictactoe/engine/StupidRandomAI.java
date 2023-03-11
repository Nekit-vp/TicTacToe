package com.example.tictactoe.engine;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.tictactoe.dto.GameDto;
import com.example.tictactoe.dto.StepDto;
import org.springframework.stereotype.Component;

@Component
public class StupidRandomAI implements TicTacToeArtificialIntelligence {

    private static final Random random = new Random();

    @Override
    public Integer getNextStep(GameDto gameDto) {
        List<Integer> allPositions = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> listForRandom;
        if (Objects.nonNull(gameDto.getSteps()) && !gameDto.getSteps().isEmpty()) {
            List<Integer> exists = gameDto.getSteps().stream()
                    .map(StepDto::getPosition)
                    .collect(Collectors.toList());
            listForRandom = allPositions.stream()
                    .filter(position -> !exists.contains(position))
                    .collect(Collectors.toList());
        } else {
            listForRandom = allPositions;
        }
        int randomIndex = random.nextInt(listForRandom.size());
        return listForRandom.get(randomIndex);
    }
}
