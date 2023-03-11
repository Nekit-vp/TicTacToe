package com.example.tictactoe.mapper;

import java.util.List;

import com.example.tictactoe.dto.StepDto;
import com.example.tictactoe.dto.response.StepResponse;
import com.example.tictactoe.model.StepEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface StepMapper {

    @Mapping(target = "figure", source = "stepEntity.author.figureOnBoard")
    StepResponse toResponse(StepEntity stepEntity);
    List<StepResponse> toResponses(List<StepEntity> stepEntities);

    StepDto toDto(StepEntity stepEntity);
    List<StepDto> toDtos(List<StepEntity> stepEntities);

}
