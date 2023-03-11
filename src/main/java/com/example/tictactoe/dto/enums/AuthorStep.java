package com.example.tictactoe.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorStep {

    USER("Пользователь", 'X'),
    COMPUTER("Компьютер", 'O');

    private final String description;

    private final Character figureOnBoard;
}
