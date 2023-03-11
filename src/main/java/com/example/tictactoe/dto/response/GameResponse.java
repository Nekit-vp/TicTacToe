package com.example.tictactoe.dto.response;

import java.time.Instant;
import java.util.List;

import com.example.tictactoe.dto.enums.StatusGame;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Игра")
public class GameResponse {

    private Long id;

    private StatusGame status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private Instant createdAt;

    private List<StepResponse> steps;
}
