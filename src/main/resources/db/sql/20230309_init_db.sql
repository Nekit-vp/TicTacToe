CREATE TABLE game
(
    id         NUMERIC     NOT NULL PRIMARY KEY ,
    status     VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE step
(
    id             NUMERIC     NOT NULL PRIMARY KEY ,
    game_id        NUMERIC     NOT NULL REFERENCES game (id),
    author         VARCHAR(10) NOT NULL ,
    board_position INT         NOT NULL ,
    created_at     TIMESTAMP   NOT NULL DEFAULT NOW()
);

