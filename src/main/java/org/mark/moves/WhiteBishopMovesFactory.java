package org.mark.moves;

import org.mark.bitboards.BitboardFactory;

import static org.mark.Material.WHITE_BISHOP;

public class WhiteBishopMovesFactory extends MovesFactory {

    public static String createPossibleMoves(Long[] boards) {
        return createPossibleMovesForRookBishopOrQueen(boards, BitboardFactory::getDiagonals, WHITE_BISHOP);
    }
}
