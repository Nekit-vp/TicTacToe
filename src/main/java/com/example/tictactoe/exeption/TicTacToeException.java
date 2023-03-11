package com.example.tictactoe.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Getter
public class TicTacToeException extends RuntimeException {

    private final HttpStatus httpStatus;

    public TicTacToeException(String message, HttpStatus httpStatus, Object... args) {
        super(getErrorMessage(message, args));
        this.httpStatus = httpStatus;
    }

    private static String getErrorMessage(String message, Object... args) {
        return isEmpty(args) ? message : String.format(message, args);
    }
}
