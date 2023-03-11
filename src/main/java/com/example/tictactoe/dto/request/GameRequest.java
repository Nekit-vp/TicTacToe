package com.example.tictactoe.dto.request;

import javax.validation.constraints.NotNull;

import com.example.tictactoe.dto.enums.AuthorStep;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на создание новой игры")
public class GameRequest {

    @Schema(description = "Принадлежность первого хода")
    @NotNull
    private AuthorStep firstStep;
}
