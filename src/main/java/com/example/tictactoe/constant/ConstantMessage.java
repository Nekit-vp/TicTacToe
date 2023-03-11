package com.example.tictactoe.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantMessage {

    public static final String GAME_BY_ID_NOT_FOUND_MESSAGE = "Game with id %s not found";
    public static final String GAME_FINISHED_MESSAGE = "Forbidden, the game №%s is over with status: %s";
    public static final String GAME_EMPTY_MESSAGE = "You cannot undo a move in an empty game with id %s";
    public static final String POSITION_ALREADY_TAKEN_MESSAGE = "This step '%s' is not possible, select another " +
            "position";



}
