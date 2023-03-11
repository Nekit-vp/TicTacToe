package com.example.tictactoe.mapper;

import com.example.tictactoe.dto.GameDto;
import com.example.tictactoe.dto.response.GameResponse;
import com.example.tictactoe.model.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = StepMapper.class)
public interface GameMapper {

    GameResponse toResponse(GameEntity gameEntity);

    GameDto toDto(GameEntity gameEntity);
}
