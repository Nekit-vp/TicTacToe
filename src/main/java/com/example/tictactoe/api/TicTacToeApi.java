package com.example.tictactoe.api;

import javax.validation.Valid;

import com.example.tictactoe.dto.request.GameRequest;
import com.example.tictactoe.dto.request.StepRequest;
import com.example.tictactoe.dto.response.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "TicTacToe Controller")
public interface TicTacToeApi {

    String BASE_URL = "/game";

    @Operation(summary = "Получить состояние партии в крестики нолики")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Состояние игры в крестики нолики",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Партии с таким идентификатором не существует")
    })
    @GetMapping(value = BASE_URL + "/{gameId}")
    GameResponse getGameState(@Schema(required = true, description = "Идентификатор партии")
                              @PathVariable Long gameId);

    @Operation(summary = "Создать новую игру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новая игра создана",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))}
            )
    })
    @PostMapping(value = BASE_URL)
    GameResponse createGame(@Schema(required = true, description = "Принадлежность первого хода")
                            @RequestBody @Valid GameRequest gameRequest);

    @Operation(summary = "Сделать ход")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ход сделан",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Партии с таким идентификатором не существует"),
            @ApiResponse(responseCode = "403", description = "Данный ход невозможен")
    })
    @PostMapping(value = BASE_URL + "/{gameId}/step")
    GameResponse createStep(@Schema(required = true, description = "Идентификатор партии")
                            @PathVariable Long gameId,
                            @Schema(required = true, description = "Ход пользователя")
                            @RequestBody @Valid StepRequest stepRequest);
}
