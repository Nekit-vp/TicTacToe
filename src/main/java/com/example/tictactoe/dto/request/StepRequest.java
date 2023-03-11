package com.example.tictactoe.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ход пользователя")
public class StepRequest {

    @Schema(description = "Позиция хода")
    @NotNull
    @Min(1)
    @Max(9)
    private Integer position;
}
