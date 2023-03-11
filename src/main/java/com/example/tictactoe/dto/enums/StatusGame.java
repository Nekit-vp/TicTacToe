package com.example.tictactoe.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusGame {

    PROCESS("Игра продолжается"),
    FINISHED_USER_WON("Игра закончена в победу пользователя"),
    FINISHED_COMPUTER_WON("Игра закончена в победу компьютера"),
    FINISHED_DEAD_HEAT("Игра закончена, ничья");

    private final String description;
}
