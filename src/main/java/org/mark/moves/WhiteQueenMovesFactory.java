package org.mark.moves;

import static org.mark.Material.WHITE_QUEEN;
import static org.mark.bitboards.BitboardFactory.getDiagonals;
import static org.mark.bitboards.BitboardFactory.getOrthogonals;

public class WhiteQueenMovesFactory extends MovesFactory {

    public static String createPossibleMoves(Long[] boards) {
        return createPossibleMovesForRookBishopOrQueen(boards, index -> getOrthogonals(index) | getDiagonals(index), WHITE_QUEEN);
    }
}
