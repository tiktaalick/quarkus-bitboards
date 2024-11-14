package org.mark;

public class Moves {

    private static final long CENTRE          = 103481868288L;
    private static final long EXTENDED_CENTRE = 66229406269440L;
    private static final long FILE_A          = 72340172838076673L;
    private static final long FILE_AB         = 217020518514230019L;
    private static final long FILE_GH         = -4557430888798830400L;
    private static final long FILE_H          = -9187201950435737472L;
    private static final long KING_B7         = 460039L;
    private static final long KING_SIDE       = -1085102592571150096L;
    private static final long KNIGHT_C6       = 43234889994L;
    private static final long QUEEN_SIDE      = 1085102592571150095L;
    private static final long RANK_1          = -72057594037927936L;
    private static final long RANK_4          = 1095216660480L;
    private static final long RANK_5          = 4278190080L;
    private static final long RANK_8          = 255L;
    private static       long blackPiecesButNoBlackKing;
    private static       long empty;
    private static       long noWhitePiecesAndNoBlackKing;

    public static String possibleMovesWhite(String history,
            long wr,
            long wn,
            long wb,
            long wq,
            long wk,
            long wp,
            long br,
            long bn,
            long bb,
            long bq,
            long bk,
            long bp) {
        noWhitePiecesAndNoBlackKing = ~(wr | wn | wb | wq | wk | wp | bk);
        blackPiecesButNoBlackKing = br | bn | bb | bq | bp;
        empty = ~(wr | wn | wb | wq | wk | wp | br | bn | bb | bq | bk | bp);
        timeExperiment(wp);
        return possiblePawnMoves(history, wp);
    }

    private static String possiblePawnMoves(String history, long wp) {
        StringBuilder list = new StringBuilder();

        long pawnMoves = (wp >> 7) & blackPiecesButNoBlackKing & ~RANK_8 & ~FILE_A;
        for (int i = Long.numberOfTrailingZeros(pawnMoves); i < 64 - Long.numberOfLeadingZeros(pawnMoves); i++) {
            if (((pawnMoves >> i) & 1) == 1) {
                list.append(i / 8 + 1)
                    .append(i % 8 - 1)
                    .append(i / 8)
                    .append(i % 8);
            }
        }

        pawnMoves = (wp >> 9) & blackPiecesButNoBlackKing & ~FILE_H;
        for (int i = Long.numberOfTrailingZeros(pawnMoves); i < 64 - Long.numberOfLeadingZeros(pawnMoves); i++) {
            if (((pawnMoves >> i) & 1) == 1) {
                list.append(i / 8 + 1)
                    .append(i % 8 + 1)
                    .append(i / 8)
                    .append(i % 8);
            }
        }
        return list.toString();
    }

    private static void timeExperiment(long wp) {
    }
}
