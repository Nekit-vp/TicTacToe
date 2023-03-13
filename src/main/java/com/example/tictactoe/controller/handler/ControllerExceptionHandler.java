package com.example.tictactoe.controller.handler;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.tictactoe.dto.ExceptionMessage;
import com.example.tictactoe.dto.ResponseErrorMessage;
import com.example.tictactoe.exeption.TicTacToeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    private static final String EXCEPTION_MESSAGE = "Handling exception in GlobalExceptionHandler";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionMessage> onAllExceptions(Exception exception, WebRequest request) {
        log.error(EXCEPTION_MESSAGE, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionMessage.builder()
                        .endpoint(((ServletWebRequest) request).getRequest().getRequestURI())
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(TicTacToeException.class)
    public final ResponseEntity<ExceptionMessage> onTicTacToeExceptions(
            TicTacToeException accountException, WebRequest request) {
        log.info(EXCEPTION_MESSAGE, accountException);
        return ResponseEntity.status(accountException.getHttpStatus())
                .body(ExceptionMessage.builder()
                        .endpoint(((ServletWebRequest) request).getRequest().getRequestURI())
                        .message(accountException.getMessage())
                        .exceptionName(accountException.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(BindException.class)
    public final ResponseEntity<ResponseErrorMessage> handleBindException(BindException ex) {
        log.info(EXCEPTION_MESSAGE, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseErrorMessage.builder()
                        .errors(collectErrors(ex))
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionMessage> handleException(HttpMessageNotReadableException ex,
                                                            WebRequest request) {
        log.info(EXCEPTION_MESSAGE, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.builder()
                        .endpoint(((ServletWebRequest) request).getRequest().getRequestURI())
                        .message(ex.getMessage())
                        .exceptionName(ex.getClass().getSimpleName())
                        .build());
    }

    private Set<ResponseErrorMessage.Error> collectErrors(BindException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String errorMessage = error.getDefaultMessage();

                    ResponseErrorMessage.Error.ErrorBuilder errorDto = ResponseErrorMessage.Error.builder()
                            .detailMessage(errorMessage);

                    if (error instanceof FieldError) {
                        errorDto.fieldName(((FieldError)error).getField());
                        errorDto.code((error).getCode());
                    } else {
                        errorDto.fieldName(error.getObjectName());
                    }
                    return errorDto.build();
                })
                .collect(Collectors.toSet());
    }
}
