INSERT INTO game (id, status, created_at)
VALUES (1, 'PROCESS', '2023-02-16 16:49:42.282287'),
       (2, 'PROCESS', '2023-03-08 11:22:23.154142');

INSERT INTO step (id, game_id, author, board_position, created_at)
VALUES (1, 1, 'USER', 6, '2023-02-16 16:55:42.282287'),
       (2, 1, 'COMPUTER', 4, '2023-02-16 16:56:42.282287'),
       (3, 1, 'USER', 2, '2023-02-16 16:57:42.282287'),
       (4, 1, 'COMPUTER', 5, '2023-02-16 16:58:42.282287'),
       (5, 1, 'USER', 1, '2023-02-16 16:59:42.282287');