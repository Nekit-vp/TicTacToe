package com.example.tictactoe.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckWonUtil {

    private static final Set<Set<Integer>> winningPositions = Set.of(
            Set.of(1, 2, 3),
            Set.of(4, 5, 6),
            Set.of(7, 8, 9),
            Set.of(1, 4, 7),
            Set.of(2, 5, 8),
            Set.of(3, 6, 9),
            Set.of(1, 5, 9),
            Set.of(3, 5, 7)
    );

    public static boolean checkWinningPosition(Set<Integer> position) {

        for (Set<Integer> winPos : winningPositions) {
            if (position.containsAll(winPos)) {
                return true;
            }
        }
        return false;
    }

}
