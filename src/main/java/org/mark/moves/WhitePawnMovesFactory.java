package org.mark.moves;

import static org.mark.Material.WHITE_PAWN;
import static org.mark.Material.WHITE_QUEEN;
import static org.mark.bitboards.BitboardFactory.getBlackMaterialToCapture;
import static org.mark.bitboards.BitboardFactory.getEmptySquares;
import static org.mark.bitboards.BitboardFactory.getEnPassantMove;
import static org.mark.bitboards.BitboardFactory.getWhitePawns;
import static org.mark.bitboards.BitboardFactory.isNotOnFileA;
import static org.mark.bitboards.BitboardFactory.isNotOnFileH;
import static org.mark.bitboards.BitboardFactory.isNotOnRank8;
import static org.mark.bitboards.BitboardFactory.isOnRank4;
import static org.mark.bitboards.BitboardFactory.isOnRank8;

public class WhitePawnMovesFactory extends MovesFactory {

    public static String createPossibleMoves(Long[] boards) {
        return captureNorthEast(boards) +
                captureNorthEastAndPromote(boards) +
                captureNorthEastEnPassant(boards) +
                captureNorthWest(boards) +
                captureNorthWestAndPromote(boards) +
                captureNorthWestEnPassant(boards) +
                goOneStepNorth(boards) +
                goOneStepNorthAndPromote(boards) +
                goTwoStepsNorth(boards);
    }

    private static String captureNorthEast(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthEast(getWhitePawns(boards)) &
                getBlackMaterialToCapture(boards) &
                isNotOnRank8() &
                isNotOnFileA(), SOUTH_WEST, WHITE_PAWN.getCode());
    }

    private static String captureNorthEastAndPromote(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthEast(getWhitePawns(boards)) &
                getBlackMaterialToCapture(boards) &
                isOnRank8() &
                isNotOnFileA(), SOUTH_WEST, WHITE_QUEEN.getCode());
    }

    private static String captureNorthEastEnPassant(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthEast(getWhitePawns(boards)) & getEnPassantMove(boards) & isNotOnRank8() & isNotOnFileA(),
                SOUTH_WEST,
                WHITE_PAWN.getCode());
    }

    private static String captureNorthWest(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthWest(getWhitePawns(boards)) &
                getBlackMaterialToCapture(boards) &
                isNotOnRank8() &
                isNotOnFileH(), SOUTH_EAST, WHITE_PAWN.getCode());
    }

    private static String captureNorthWestAndPromote(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthWest(getWhitePawns(boards)) &
                getBlackMaterialToCapture(boards) &
                isOnRank8() &
                isNotOnFileH(), SOUTH_EAST, WHITE_QUEEN.getCode());
    }

    private static String captureNorthWestEnPassant(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthWest(getWhitePawns(boards)) & getEnPassantMove(boards) & isNotOnRank8() & isNotOnFileH(),
                SOUTH_EAST,
                WHITE_PAWN.getCode());
    }

    public static String goOneStepNorth(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthToAnEmptySquare(getWhitePawns(boards), getEmptySquares(boards)) & isNotOnRank8(),
                SOUTH,
                WHITE_PAWN.getCode());
    }

    static String goOneStepNorthAndPromote(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthToAnEmptySquare(getWhitePawns(boards), getEmptySquares(boards)) & isOnRank8(),
                SOUTH,
                WHITE_QUEEN.getCode());
    }

    public static String goTwoStepsNorth(Long[] boards) {
        return createListOfMovesFromMultipleOrigins(goNorthToAnEmptySquare(goNorthToAnEmptySquare(getWhitePawns(boards), getEmptySquares(boards)),
                getEmptySquares(boards)) & isOnRank4(), SOUTH_SOUTH, WHITE_PAWN.getCode());
    }
}
