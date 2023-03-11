package com.example.tictactoe.service;

import com.example.tictactoe.dto.enums.AuthorStep;
import com.example.tictactoe.model.GameEntity;
import com.example.tictactoe.model.StepEntity;

public interface StepService {
    StepEntity createNewStep(AuthorStep computer, Integer positionComputer, GameEntity gameEntity);
}
