package com.example.tictactoe.dto;

import com.example.tictactoe.dto.enums.AuthorStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StepDto {

    private Integer position;
    private AuthorStep author;
}
