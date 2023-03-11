package com.example.tictactoe.model;

import java.util.List;

import javax.persistence.*;

import com.example.tictactoe.dto.enums.StatusGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private StatusGame status;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<StepEntity> steps;
}
