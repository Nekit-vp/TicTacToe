package com.example.tictactoe.engine;

import com.example.tictactoe.dto.GameDto;

public interface TicTacToeArtificialIntelligence {

    Integer getNextStep(GameDto gameDto);
}
