package com.example.tictactoe.dto;

import java.util.List;

import com.example.tictactoe.dto.enums.StatusGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private StatusGame status;
    private List<StepDto> steps;
}
