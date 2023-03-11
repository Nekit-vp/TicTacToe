package com.example.tictactoe.model;

import javax.persistence.*;

import com.example.tictactoe.dto.enums.AuthorStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "step")
public class StepEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Enumerated(EnumType.STRING)
    private AuthorStep author;

    @Column(name = "board_position")
    private Integer position;
}
