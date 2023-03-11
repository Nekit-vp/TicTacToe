package com.example.tictactoe.dto;

import java.time.Instant;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ResponseErrorMessage {
    @Builder.Default
    private long timestamp = Instant.now().getEpochSecond();
    private Set<Error> errors;

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    @Builder
    public static class Error {
        private String fieldName;
        private String code;
        private String detailMessage;
    }
}
