package org.mark;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mark.BitboardFactory.createBinaryStringFromBoard;
import static org.mark.BitboardFactory.createBinaryStringFromDecimal;
import static org.mark.BitboardFactory.isNotOnFileA;
import static org.mark.BitboardFactory.isNotOnFileH;
import static org.mark.BitboardFactory.isNotOnRank8;
import static org.mark.BitboardFactory.isOnRank4;
import static org.mark.Material.BLACK_MATERIAL_TO_CAPTURE;
import static org.mark.Material.EMPTY_SQUARES;
import static org.mark.Material.WHITE_PAWN;

public class Moves {

    private static final int SOUTH       = 8;
    private static final int NORTH       = -SOUTH;
    private static final int SOUTH_EAST  = 9;
    private static final int NORTH_WEST  = -SOUTH_EAST;
    private static final int SOUTH_SOUTH = 16;
    private static final int SOUTH_WEST  = 7;
    private static final int NORTH_EAST  = -SOUTH_WEST;
    private static final int WEST        = 1;
    private static final int EAST        = -WEST;

    public static String possibleMovesWhite(String history, Long[] decimals) {
        return possiblePawnMoves(history, decimals);
    }

    private static String possiblePawnMoves(String history, Long[] board) {
        var listOfMoves = new StringBuilder();

        listOfMoves.append(getListOfMoves(goNorthEast(whitePawns(board)) &
                blackMaterialToCapture(board) &
                isNotOnRank8() &
                isNotOnFileA(), SOUTH_WEST));

        listOfMoves.append(getListOfMoves(goNorthWest(whitePawns(board)) &
                blackMaterialToCapture(board) &
                isNotOnRank8() &
                isNotOnFileH(), SOUTH_EAST));

        listOfMoves.append(getListOfMoves(goNorthToAnEmptySquare(whitePawns(board), emptySquares(board)) & isNotOnRank8(),
                SOUTH));

        listOfMoves.append(getListOfMoves(goNorthToAnEmptySquare(goNorthToAnEmptySquare(whitePawns(board), emptySquares(board)),
                emptySquares(board)) & isOnRank4(), SOUTH_SOUTH));

        //y1, y2, Promotion Type, "P"

        return listOfMoves.toString();
    }

    private static String getListOfMoves(long targets, int reversedMove) {
        return createListOfMoves(createBinaryStringFromDecimal(targets), reversedMove);
    }

    private static long goNorthEast(long bitboard) {
        return bitboard << -NORTH_EAST;
    }

    private static long whitePawns(Long[] board) {
        return board[WHITE_PAWN.ordinal()];
    }

    private static long blackMaterialToCapture(Long[] board) {
        return board[BLACK_MATERIAL_TO_CAPTURE.ordinal()];
    }

    private static long goNorthWest(long board) {
        return board << -NORTH_WEST;
    }

    private static long goNorthToAnEmptySquare(long board, long empty) {
        return (board << -NORTH) & empty;
    }

    private static long emptySquares(Long[] board) {
        return board[EMPTY_SQUARES.ordinal()];
    }

    private static String createListOfMoves(String bitboardTarget, int move) {
        String binaryStringTarget = createBinaryStringFromBoard(bitboardTarget);

        return IntStream.rangeClosed(binaryStringTarget.indexOf("1"), binaryStringTarget.lastIndexOf("1"))
                        .filter(index -> binaryStringTarget.charAt(index) == '1')
                        .mapToObj(index -> createCoordinates(index + move) + (createCoordinates(index)))
                        .collect(Collectors.joining());
    }

    private static String createCoordinates(int index) {
        return "" + (char) ('a' + index % 8) + (9 - (index / 8 + 1));
    }

    private static int getFirstTarget(long moves) {
        return Long.numberOfTrailingZeros(moves);
    }

    private static int getX(int i) {
        return i / 8;
    }

    private static int getY(int i) {
        return i % 8;
    }

    private static long goNorth(long board) {
        return board >> 8;
    }

    private static long goSouthWest(long board) {
        return board << 7;
    }

    private static long goTwoStepsToTheNorth(long board) {
        return board >> 16;
    }

    private static boolean hasATarget(long possiblePawnTargets, int i) {
        return ((possiblePawnTargets >> i) & 1) == 1;
    }

    private static boolean hasNextTarget(int i, long moves) {
        return i < 64 - Long.numberOfLeadingZeros(moves);
    }
}
