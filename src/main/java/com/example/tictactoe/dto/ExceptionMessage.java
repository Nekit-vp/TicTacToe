package com.example.tictactoe.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Сообщение описывающее возникшую исключительную ситуацию.
 */
@Data
@Builder
public class ExceptionMessage {

    /**
     * Описание пути, по которому возникла исключительная ситуация
     */
    private String endpoint;

    /**
     * Сообщение исключения
     */
    private String message;

    /**
     * Наименование исключения
     */
    private String exceptionName;

}
