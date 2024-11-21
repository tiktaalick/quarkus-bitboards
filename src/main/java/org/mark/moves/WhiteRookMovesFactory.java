package org.mark.moves;

import org.mark.bitboards.BitboardFactory;

import static org.mark.Material.WHITE_ROOK;

public class WhiteRookMovesFactory extends MovesFactory {

    public static String createPossibleMoves(Long[] boards) {
        return createPossibleMovesForRookBishopOrQueen(boards, BitboardFactory::getOrthogonals, WHITE_ROOK);
    }
}
