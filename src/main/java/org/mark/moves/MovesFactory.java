package org.mark.moves;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mark.bitboards.BitboardFactory.createBinaryStringFromBoard;
import static org.mark.bitboards.BitboardFactory.createBinaryStringFromDecimal;

public class MovesFactory {

    public static final  int SOUTH           = 8;
    public static final  int NORTH           = -SOUTH;
    public static final  int SOUTH_EAST      = 9;
    public static final  int NORTH_WEST      = -SOUTH_EAST;
    public static final  int SOUTH_SOUTH     = 16;
    public static final  int SOUTH_WEST      = 7;
    public static final  int NORTH_EAST      = -SOUTH_WEST;
    public static final  int WEST            = 1;
    public static final  int EAST            = -WEST;
    private static final int NUMBER_OF_FILES = 8;
    private static final int NUMBER_OF_RANKS = 8;

    public static String createPossibleMovesForPlayer(Long[] boards) {
        return WhitePawnMovesFactory.createPossibleMoves(boards);
    }

    public static long goNorthEast(long bitboard) {
        return bitboard << -NORTH_EAST;
    }

    public static long goNorthWest(long board) {
        return board << -NORTH_WEST;
    }

    public static String createListOfMoves(long targets, int reversedMove, String material) {
        return createListOfMoves(createBinaryStringFromDecimal(targets), reversedMove, material);
    }

    public static long goNorthToAnEmptySquare(long board, long empty) {
        return (board << -NORTH) & empty;
    }

    private static String createListOfMoves(String bitboardTarget, int move, String material) {
        var binaryStringTarget = createBinaryStringFromBoard(bitboardTarget);

        return IntStream.rangeClosed(binaryStringTarget.indexOf("1"), binaryStringTarget.lastIndexOf("1"))
                        .filter(index -> index >= 0 && binaryStringTarget.charAt(index) == '1')
                        .mapToObj(index -> createCoordinates(index + move) + material + (createCoordinates(index)))
                        .collect(Collectors.joining());
    }

    private static String createCoordinates(int index) {
        return "" + (char) ('a' + index % NUMBER_OF_FILES) + ((NUMBER_OF_RANKS + 1) - (index / NUMBER_OF_RANKS + 1));
    }
}
