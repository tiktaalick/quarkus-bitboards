package org.mark;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mark.BitboardFactory.blackMaterialToCapture;
import static org.mark.BitboardFactory.createBinaryStringFromBoard;
import static org.mark.BitboardFactory.createBinaryStringFromDecimal;
import static org.mark.BitboardFactory.emptySquares;
import static org.mark.BitboardFactory.enPassantMove;
import static org.mark.BitboardFactory.isNotOnFileA;
import static org.mark.BitboardFactory.isNotOnFileH;
import static org.mark.BitboardFactory.isNotOnRank8;
import static org.mark.BitboardFactory.isOnRank4;
import static org.mark.BitboardFactory.isOnRank8;
import static org.mark.BitboardFactory.whitePawns;
import static org.mark.Material.WHITE_PAWN;
import static org.mark.Material.WHITE_QUEEN;

public class MovesFactory {

    private static final int SOUTH       = 8;
    private static final int NORTH       = -SOUTH;
    private static final int SOUTH_EAST  = 9;
    private static final int NORTH_WEST  = -SOUTH_EAST;
    private static final int SOUTH_SOUTH = 16;
    private static final int SOUTH_WEST  = 7;
    private static final int NORTH_EAST  = -SOUTH_WEST;
    private static final int WEST        = 1;
    private static final int EAST        = -WEST;

    public static String createPossibleMovesForWhite(Long[] boards) {
        return createPossiblePawnMovesForWhite(boards);
    }

    private static String createPossiblePawnMovesForWhite(Long[] boards) {
        return createListOfMoves(goNorthEast(whitePawns(boards)) &
                blackMaterialToCapture(boards) &
                isNotOnRank8() &
                isNotOnFileA(), SOUTH_WEST, WHITE_PAWN.getCode()) +

                createListOfMoves(goNorthWest(whitePawns(boards)) &
                        blackMaterialToCapture(boards) &
                        isNotOnRank8() &
                        isNotOnFileH(), SOUTH_EAST, WHITE_PAWN.getCode()) +

                createListOfMoves(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)) & isNotOnRank8(),
                        SOUTH,
                        WHITE_PAWN.getCode()) +

                createListOfMoves(goNorthToAnEmptySquare(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)),
                        emptySquares(boards)) & isOnRank4(), SOUTH_SOUTH, WHITE_PAWN.getCode()) +

                createListOfMoves(goNorthEast(whitePawns(boards)) & blackMaterialToCapture(boards) & isOnRank8() & isNotOnFileA(),
                        SOUTH_WEST,
                        WHITE_QUEEN.getCode()) +

                createListOfMoves(goNorthWest(whitePawns(boards)) & blackMaterialToCapture(boards) & isOnRank8() & isNotOnFileH(),
                        SOUTH_EAST,
                        WHITE_QUEEN.getCode()) +

                createListOfMoves(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)) & isOnRank8(),
                        SOUTH,
                        WHITE_QUEEN.getCode()) +

                createListOfMoves(goNorthEast(whitePawns(boards)) & enPassantMove(boards) & isNotOnRank8() & isNotOnFileA(),
                        SOUTH_WEST,
                        WHITE_PAWN.getCode()) +

                createListOfMoves(goNorthWest(whitePawns(boards)) & enPassantMove(boards) & isNotOnRank8() & isNotOnFileH(),
                        SOUTH_EAST,
                        WHITE_PAWN.getCode());
    }

    private static String createListOfMoves(long targets, int reversedMove, String material) {
        return createListOfMoves(createBinaryStringFromDecimal(targets), reversedMove, material);
    }

    private static long goNorthEast(long bitboard) {
        return bitboard << -NORTH_EAST;
    }

    private static long goNorthWest(long board) {
        return board << -NORTH_WEST;
    }

    private static long goNorthToAnEmptySquare(long board, long empty) {
        return (board << -NORTH) & empty;
    }

    private static String createListOfMoves(String bitboardTarget, int move, String material) {
        String binaryStringTarget = createBinaryStringFromBoard(bitboardTarget);

        return IntStream.rangeClosed(binaryStringTarget.indexOf("1"), binaryStringTarget.lastIndexOf("1"))
                        .filter(index -> index >= 0 && binaryStringTarget.charAt(index) == '1')
                        .mapToObj(index -> createCoordinates(index + move) + material + (createCoordinates(index)))
                        .collect(Collectors.joining());
    }

    private static String createCoordinates(int index) {
        return "" + (char) ('a' + index % 8) + (9 - (index / 8 + 1));
    }
}
