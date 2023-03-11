package com.example.tictactoe.dto.response;

import com.example.tictactoe.dto.enums.AuthorStep;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Один ход")
public class StepResponse {

    private Integer position;

    private Character figure;

    private AuthorStep author;
}
