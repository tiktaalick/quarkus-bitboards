package org.mark.moves;

import static org.mark.Material.WHITE_PAWN;
import static org.mark.Material.WHITE_QUEEN;
import static org.mark.bitboards.BitboardFactory.blackMaterialToCapture;
import static org.mark.bitboards.BitboardFactory.emptySquares;
import static org.mark.bitboards.BitboardFactory.enPassantMove;
import static org.mark.bitboards.BitboardFactory.isNotOnFileA;
import static org.mark.bitboards.BitboardFactory.isNotOnFileH;
import static org.mark.bitboards.BitboardFactory.isNotOnRank8;
import static org.mark.bitboards.BitboardFactory.isOnRank4;
import static org.mark.bitboards.BitboardFactory.isOnRank8;
import static org.mark.bitboards.BitboardFactory.whitePawns;

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

    public static String goOneStepNorth(Long[] boards) {
        return createListOfMoves(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)) & isNotOnRank8(),
                SOUTH,
                WHITE_PAWN.getCode());
    }

    public static String goTwoStepsNorth(Long[] boards) {
        return createListOfMoves(goNorthToAnEmptySquare(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)),
                emptySquares(boards)) & isOnRank4(), SOUTH_SOUTH, WHITE_PAWN.getCode());
    }

    private static String captureNorthEast(Long[] boards) {
        return createListOfMoves(goNorthEast(whitePawns(boards)) &
                blackMaterialToCapture(boards) &
                isNotOnRank8() &
                isNotOnFileA(), SOUTH_WEST, WHITE_PAWN.getCode());
    }

    private static String captureNorthEastAndPromote(Long[] boards) {
        return createListOfMoves(goNorthEast(whitePawns(boards)) & blackMaterialToCapture(boards) & isOnRank8() & isNotOnFileA(),
                SOUTH_WEST,
                WHITE_QUEEN.getCode());
    }

    private static String captureNorthEastEnPassant(Long[] boards) {
        return createListOfMoves(goNorthEast(whitePawns(boards)) & enPassantMove(boards) & isNotOnRank8() & isNotOnFileA(),
                SOUTH_WEST,
                WHITE_PAWN.getCode());
    }

    private static String captureNorthWest(Long[] boards) {
        return createListOfMoves(goNorthWest(whitePawns(boards)) &
                blackMaterialToCapture(boards) &
                isNotOnRank8() &
                isNotOnFileH(), SOUTH_EAST, WHITE_PAWN.getCode());
    }

    private static String captureNorthWestAndPromote(Long[] boards) {
        return createListOfMoves(goNorthWest(whitePawns(boards)) & blackMaterialToCapture(boards) & isOnRank8() & isNotOnFileH(),
                SOUTH_EAST,
                WHITE_QUEEN.getCode());
    }

    private static String captureNorthWestEnPassant(Long[] boards) {
        return createListOfMoves(goNorthWest(whitePawns(boards)) & enPassantMove(boards) & isNotOnRank8() & isNotOnFileH(),
                SOUTH_EAST,
                WHITE_PAWN.getCode());
    }

    static String goOneStepNorthAndPromote(Long[] boards) {
        return createListOfMoves(goNorthToAnEmptySquare(whitePawns(boards), emptySquares(boards)) & isOnRank8(),
                SOUTH,
                WHITE_QUEEN.getCode());
    }
}
