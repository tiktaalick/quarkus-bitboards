package org.mark.moves;

import org.mark.Material;
import org.mark.bitboards.BitboardFactory;

import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mark.bitboards.BitboardFactory.createBinaryStringFromBoard;
import static org.mark.bitboards.BitboardFactory.createBinaryStringFromDecimal;
import static org.mark.bitboards.BitboardFactory.createBitboardFromIndex;
import static org.mark.bitboards.BitboardFactory.getDecimalValueFromBitboard;
import static org.mark.bitboards.BitboardFactory.getOccupiedSquares;
import static org.mark.bitboards.BitboardFactory.getWhiteMaterial;

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

    public static String createListOfMovesFromMultipleOrigins(long targets, int reversedMove, String material) {
        return createListOfMovesFromMultipleOrigins(createBinaryStringFromDecimal(targets), reversedMove, material);
    }

    /**
     * Creates a list of all possible moves for a rook, bishop or queen. This is done by looking at the occupied fields within the given mask. This
     * mask contains the possible directions of the material. Possible directions for a rook, bishop or queen are: orthogonally (horizontally and
     * vertically combined), diagonally (both diagonals combined) or both.
     * <p>
     * The list consists of squares with material that can be captured and of all the empty squares in between.
     * <p>
     * The formula is: whiteMaterial ^ (((occupied & mask) - 2 * attacker) ^ ((occupied & mask)' - 2 * attacker')') & mask where ' stands for a
     * reversed order of the bits.
     *
     * @param boards   The decimal representations of all the bitboards.
     * @param material The type of material of the attacker.
     * @return All the possible moves.
     */
    protected static String createPossibleMovesForRookBishopOrQueen(Long[] boards, IntToLongFunction mask, Material material) {
        var attackers = createBinaryStringFromDecimal(boards[material.ordinal()]);
        long occupiedSquares = getOccupiedSquares(boards);

        return IntStream.rangeClosed(attackers.indexOf("1"), attackers.lastIndexOf("1"))
                        .mapToObj(position -> {
                            long occupiedWithinMask = occupiedSquares & mask.applyAsLong(position);
                            long occupiedWithinMaskReversed = BitboardFactory.createReversedBoardFromDecimal(occupiedWithinMask);
                            long attacker = getDecimalValueFromBitboard(createBitboardFromIndex(position));
                            long attackerReversed = BitboardFactory.createReversedBoardFromDecimal(attacker);

                            return createListOfMovesFromSingleOrigin(createBinaryStringFromDecimal(getWhiteMaterial(boards) ^
                                    ((occupiedWithinMask - 2 * attacker) ^
                                            ((BitboardFactory.createReversedBoardFromDecimal(occupiedWithinMaskReversed - 2 * attackerReversed)) &
                                                    mask.applyAsLong(position)))), position, material.getCode());
                        })
                        .collect(Collectors.joining());
    }

    private static String createListOfMovesFromMultipleOrigins(String bitboardTarget, int reversedMove, String material) {
        var binaryStringTarget = createBinaryStringFromBoard(bitboardTarget);

        return IntStream.rangeClosed(binaryStringTarget.indexOf("1"), binaryStringTarget.lastIndexOf("1"))
                        .filter(index -> index >= 0 && binaryStringTarget.charAt(index) == '1')
                        .mapToObj(index -> createCoordinates(index + reversedMove) + material + (createCoordinates(index)))
                        .collect(Collectors.joining());
    }

    private static String createCoordinates(int index) {
        return "" + (char) ('a' + index % NUMBER_OF_FILES) + ((NUMBER_OF_RANKS + 1) - (index / NUMBER_OF_RANKS + 1));
    }

    public static String createListOfMovesFromSingleOrigin(String bitboardTarget, int fromIndex, String material) {
        var binaryStringTarget = createBinaryStringFromBoard(bitboardTarget);

        return IntStream.rangeClosed(binaryStringTarget.indexOf("1"), binaryStringTarget.lastIndexOf("1"))
                        .filter(index -> index >= 0 && binaryStringTarget.charAt(index) == '1')
                        .mapToObj(index -> createCoordinates(fromIndex) + material + (createCoordinates(index)))
                        .collect(Collectors.joining());
    }

    public static String createPossibleMovesForPlayer(Long[] boards) {
        return WhitePawnMovesFactory.createPossibleMoves(boards) +
                WhiteRookMovesFactory.createPossibleMoves(boards) +
                WhiteKnightMovesFactory.createPossibleMoves(boards) +
                WhiteBishopMovesFactory.createPossibleMoves(boards) +
                WhiteQueenMovesFactory.createPossibleMoves(boards) +
                WhiteKingMovesFactory.createPossibleMoves(boards);
    }

    public static long goNorthEast(long bitboard) {
        return bitboard << -NORTH_EAST;
    }

    public static long goNorthToAnEmptySquare(long board, long empty) {
        return (board << -NORTH) & empty;
    }

    public static long goNorthWest(long board) {
        return board << -NORTH_WEST;
    }
}
