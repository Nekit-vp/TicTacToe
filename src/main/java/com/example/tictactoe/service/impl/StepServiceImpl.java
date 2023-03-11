package com.example.tictactoe.service.impl;

import com.example.tictactoe.dto.enums.AuthorStep;
import com.example.tictactoe.model.GameEntity;
import com.example.tictactoe.model.StepEntity;
import com.example.tictactoe.repository.StepRepository;
import com.example.tictactoe.service.StepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;

    @Override
    @Transactional
    public void createNewStep(AuthorStep authorStep, Integer position, GameEntity gameEntity) {
        StepEntity stepEntity = StepEntity.builder()
                .author(authorStep)
                .game(gameEntity)
                .position(position)
                .build();
        stepRepository.save(stepEntity);
    }
}
