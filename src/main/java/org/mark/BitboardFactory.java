package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mark.Material.BLACK_BISHOP;
import static org.mark.Material.BLACK_KING;
import static org.mark.Material.BLACK_KNIGHT;
import static org.mark.Material.BLACK_MATERIAL_TO_CAPTURE;
import static org.mark.Material.BLACK_PAWN;
import static org.mark.Material.BLACK_QUEEN;
import static org.mark.Material.BLACK_ROOK;
import static org.mark.Material.EMPTY_SQUARES;
import static org.mark.Material.NO_BLACK_MATERIAL_NO_WHITE_KING;
import static org.mark.Material.NO_WHITE_MATERIAL_NO_BLACK_KING;
import static org.mark.Material.WHITE_BISHOP;
import static org.mark.Material.WHITE_KING;
import static org.mark.Material.WHITE_KNIGHT;
import static org.mark.Material.WHITE_MATERIAL_TO_CAPTURE;
import static org.mark.Material.WHITE_PAWN;
import static org.mark.Material.WHITE_QUEEN;
import static org.mark.Material.WHITE_ROOK;

public class BitboardFactory {

    private static final int    CONVERT_FROM_BINARY        = 2;
    private static final String EMPTY_STRING               = "";
    private static final int    FILE_LENGTH                = 8;
    private static final String INITIAL_BOARD_BLACK_PLAYER = """
                                                             RNBQKBNR
                                                             PPPPPPPP
                                                             --------
                                                             --------
                                                             --------
                                                             --------
                                                             pppppppp
                                                             rnbqkbnr""";
    private static final String INITIAL_BOARD_WHITE_PLAYER = """
                                                             rnbqkbnr
                                                             pppppppp
                                                             --------
                                                             --------
                                                             --------
                                                             --------
                                                             PPPPPPPP
                                                             RNBQKBNR""";
    private static final String LEADING_ZERO               = "0";
    private static final String NEW_LINE                   = "\n";
    private static final long   CENTRE                     = getDecimalValueFromBitboard("""
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00011000
                                                                                         00011000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000""");
    private static final long   EXTENDED_CENTRE            = getDecimalValueFromBitboard("""
                                                                                         00000000
                                                                                         00000000
                                                                                         00111100
                                                                                         00111100
                                                                                         00111100
                                                                                         00111100
                                                                                         00000000
                                                                                         00000000""");
    private static final long   FILE_A                     = getDecimalValueFromBitboard("""
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000
                                                                                         10000000""");
    private static final long   FILE_H                     = getDecimalValueFromBitboard("""
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001
                                                                                         00000001""");
    private static final int    NUMBER_OF_SQUARES          = 64;
    private static final long   RANK_4                     = getDecimalValueFromBitboard("""
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         11111111
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000""");
    private static final long   RANK_8                     = getDecimalValueFromBitboard("""
                                                                                         11111111
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000
                                                                                         00000000""");

    //    public static String createBitboardFromDecimal(long decimalValue) {
//        return createBitboardFromBinaryString(createBinaryStringFromDecimal(decimalValue));
//    }

    public static String createBinaryStringFromDecimal(Long decimalValue) {
        return StringUtils.leftPad(Long.toBinaryString(decimalValue), NUMBER_OF_SQUARES, LEADING_ZERO);
    }

    public static Long[] createDecimalsFromBitboards(String[] bitboards) {
        Long[] decimals = Stream.of(bitboards)
                                .map(BitboardFactory::getDecimalValueFromBitboard)
                                .toArray(value -> new Long[Material.values().length]);

        decimals[NO_WHITE_MATERIAL_NO_BLACK_KING.ordinal()] = ~(decimals[WHITE_PAWN.ordinal()] |
                decimals[WHITE_ROOK.ordinal()] |
                decimals[WHITE_KNIGHT.ordinal()] |
                decimals[WHITE_BISHOP.ordinal()] |
                decimals[WHITE_QUEEN.ordinal()] |
                decimals[WHITE_KING.ordinal()] |
                decimals[BLACK_KING.ordinal()]);

        decimals[NO_BLACK_MATERIAL_NO_WHITE_KING.ordinal()] = ~(decimals[BLACK_PAWN.ordinal()] |
                decimals[BLACK_ROOK.ordinal()] |
                decimals[BLACK_KNIGHT.ordinal()] |
                decimals[BLACK_BISHOP.ordinal()] |
                decimals[BLACK_QUEEN.ordinal()] |
                decimals[BLACK_KING.ordinal()] |
                decimals[WHITE_KING.ordinal()]);

        decimals[WHITE_MATERIAL_TO_CAPTURE.ordinal()] = decimals[WHITE_PAWN.ordinal()] |
                decimals[WHITE_ROOK.ordinal()] |
                decimals[WHITE_KNIGHT.ordinal()] |
                decimals[WHITE_BISHOP.ordinal()] |
                decimals[WHITE_QUEEN.ordinal()];

        decimals[BLACK_MATERIAL_TO_CAPTURE.ordinal()] = decimals[BLACK_PAWN.ordinal()] |
                decimals[BLACK_ROOK.ordinal()] |
                decimals[BLACK_KNIGHT.ordinal()] |
                decimals[BLACK_BISHOP.ordinal()] |
                decimals[BLACK_QUEEN.ordinal()];

        decimals[EMPTY_SQUARES.ordinal()] = ~(decimals[WHITE_PAWN.ordinal()] |
                decimals[WHITE_ROOK.ordinal()] |
                decimals[WHITE_KNIGHT.ordinal()] |
                decimals[WHITE_BISHOP.ordinal()] |
                decimals[WHITE_QUEEN.ordinal()] |
                decimals[WHITE_KING.ordinal()] |
                decimals[BLACK_PAWN.ordinal()] |
                decimals[BLACK_ROOK.ordinal()] |
                decimals[BLACK_KNIGHT.ordinal()] |
                decimals[BLACK_BISHOP.ordinal()] |
                decimals[BLACK_QUEEN.ordinal()]);

        return decimals;
    }

    private static long getDecimalValueFromBitboard(String bitboard) {
        return new BigInteger(createBinaryStringFromBoard(bitboard), CONVERT_FROM_BINARY).longValue();
    }

    public static String createBinaryStringFromBoard(String board) {
        return board.replace(NEW_LINE, EMPTY_STRING);
    }

    public static String[] createInitialBitboardsBlackPlayer() {
        return createBitboardsFromChessboard(INITIAL_BOARD_BLACK_PLAYER);
    }

    public static String[] createBitboardsFromChessboard(String chessboardSource) {
        return Stream.of(WHITE_PAWN,
                             WHITE_ROOK,
                             WHITE_KNIGHT,
                             WHITE_BISHOP,
                             WHITE_QUEEN,
                             WHITE_KING,
                             BLACK_PAWN,
                             BLACK_ROOK,
                             BLACK_KNIGHT,
                             BLACK_BISHOP,
                             BLACK_QUEEN,
                             BLACK_KING)
                     .map(type -> createBitboardFromChessboard(chessboardSource, type.getCode()))
                     .toArray(String[]::new);
    }

    private static String createBitboardFromChessboard(String chessboardSource, String type) {
        return createBinaryStringFromBoard(chessboardSource).chars()
                                                            .mapToObj(character -> character == type.charAt(0) ? "1" : "0")
                                                            .collect(Collectors.joining());
    }

    public static String[] createInitialBitboardsWhitePlayer() {
        return createBitboardsFromChessboard(INITIAL_BOARD_WHITE_PLAYER);
    }

    public static long isNotOnFileA() {
        return ~FILE_A;
    }

    public static long isNotOnFileH() {
        return ~FILE_H;
    }

    public static long isNotOnRank8() {
        return ~RANK_8;
    }

    public static long isOnRank4() {
        return RANK_4;
    }

    private static String createBitboardFromBinaryString(String binaryString) {
        return IntStream.range(0, binaryString.length())
                        .mapToObj(index -> binaryString.charAt(index) +
                                ((index + 1) % FILE_LENGTH == 0 ? NEW_LINE : EMPTY_STRING))
                        .collect(Collectors.joining());
    }
}
