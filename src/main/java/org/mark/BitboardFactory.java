package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mark.Bitboard.BLACK_MATERIAL_TO_CAPTURE;
import static org.mark.Bitboard.EMPTY_SQUARES;
import static org.mark.Bitboard.EN_PASSANT_MOVE;
import static org.mark.Bitboard.NO_BLACK_MATERIAL_NO_WHITE_KING;
import static org.mark.Bitboard.NO_WHITE_MATERIAL_NO_BLACK_KING;
import static org.mark.Bitboard.WHITE_MATERIAL_TO_CAPTURE;
import static org.mark.Material.BLACK_BISHOP;
import static org.mark.Material.BLACK_KING;
import static org.mark.Material.BLACK_KNIGHT;
import static org.mark.Material.BLACK_PAWN;
import static org.mark.Material.BLACK_QUEEN;
import static org.mark.Material.BLACK_ROOK;
import static org.mark.Material.WHITE_BISHOP;
import static org.mark.Material.WHITE_KING;
import static org.mark.Material.WHITE_KNIGHT;
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
    public static final  Long[] HORIZONTALS                = createHorizontals();
    public static final  Long[] VERTICALS                  = createVerticals();
    public static final  Long[] PRIMARY_DIAGONALS          = createPrimaryDiagonals();
    public static final  Long[] SECONDARY_DIAGONALS        = createPrimaryDiagonals();
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

    public static long blackMaterialToCapture(Long[] board) {
        return board[BLACK_MATERIAL_TO_CAPTURE.getIndex()];
    }

    public static String createBitboardFromDecimal(long decimalValue) {
        return createBitboardFromBinaryString(createBinaryStringFromDecimal(decimalValue));
    }

    public static String createBitboardFromBinaryString(String binaryString) {
        return IntStream.range(0, binaryString.length())
                        .mapToObj(index -> binaryString.charAt(index) +
                                ((index + 1) % FILE_LENGTH == 0 ? NEW_LINE : EMPTY_STRING))
                        .collect(Collectors.joining());
    }

    public static String createBinaryStringFromDecimal(Long decimalValue) {
        return StringUtils.leftPad(Long.toBinaryString(decimalValue), NUMBER_OF_SQUARES, LEADING_ZERO);
    }

    public static Long[] createDecimalsFromBitboards(String[] bitboards) {
        var decimals = Stream.of(bitboards)
                             .map(BitboardFactory::getDecimalValueFromBitboard)
                             .collect(Collectors.toList());

        decimals.add(NO_WHITE_MATERIAL_NO_BLACK_KING.getIndex(),
                ~(decimals.get(WHITE_PAWN.ordinal()) |
                        decimals.get(WHITE_ROOK.ordinal()) |
                        decimals.get(WHITE_KNIGHT.ordinal()) |
                        decimals.get(WHITE_BISHOP.ordinal()) |
                        decimals.get(WHITE_QUEEN.ordinal()) |
                        decimals.get(WHITE_KING.ordinal()) |
                        decimals.get(BLACK_KING.ordinal())));

        decimals.add(NO_BLACK_MATERIAL_NO_WHITE_KING.getIndex(),
                ~(decimals.get(BLACK_PAWN.ordinal()) |
                        decimals.get(BLACK_ROOK.ordinal()) |
                        decimals.get(BLACK_KNIGHT.ordinal()) |
                        decimals.get(BLACK_BISHOP.ordinal()) |
                        decimals.get(BLACK_QUEEN.ordinal()) |
                        decimals.get(BLACK_KING.ordinal()) |
                        decimals.get(WHITE_KING.ordinal())));

        decimals.add(WHITE_MATERIAL_TO_CAPTURE.getIndex(),
                decimals.get(WHITE_PAWN.ordinal()) |
                        decimals.get(WHITE_ROOK.ordinal()) |
                        decimals.get(WHITE_KNIGHT.ordinal()) |
                        decimals.get(WHITE_BISHOP.ordinal()) |
                        decimals.get(WHITE_QUEEN.ordinal()));

        decimals.add(BLACK_MATERIAL_TO_CAPTURE.getIndex(),
                decimals.get(BLACK_PAWN.ordinal()) |
                        decimals.get(BLACK_ROOK.ordinal()) |
                        decimals.get(BLACK_KNIGHT.ordinal()) |
                        decimals.get(BLACK_BISHOP.ordinal()) |
                        decimals.get(BLACK_QUEEN.ordinal()));

        decimals.add(EMPTY_SQUARES.getIndex(),
                ~(decimals.get(WHITE_PAWN.ordinal()) |
                        decimals.get(WHITE_ROOK.ordinal()) |
                        decimals.get(WHITE_KNIGHT.ordinal()) |
                        decimals.get(WHITE_BISHOP.ordinal()) |
                        decimals.get(WHITE_QUEEN.ordinal()) |
                        decimals.get(WHITE_KING.ordinal()) |
                        decimals.get(BLACK_PAWN.ordinal()) |
                        decimals.get(BLACK_ROOK.ordinal()) |
                        decimals.get(BLACK_KNIGHT.ordinal()) |
                        decimals.get(BLACK_BISHOP.ordinal()) |
                        decimals.get(BLACK_QUEEN.ordinal())));

        decimals.add(EN_PASSANT_MOVE.getIndex(), 0L);

        return decimals.toArray(new Long[0]);
    }

    public static long getDecimalValueFromBitboard(String bitboard) {
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

    public static long emptySquares(Long[] board) {
        return board[EMPTY_SQUARES.getIndex()];
    }

    public static long enPassantMove(Long[] board) {
        return board[EN_PASSANT_MOVE.getIndex()];
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

    public static long isOnRank8() {
        return RANK_8;
    }

    public static long occupiedSquares(Long[] board) {
        return ~board[EMPTY_SQUARES.getIndex()];
    }

    public static long whitePawns(Long[] board) {
        return board[WHITE_PAWN.ordinal()];
    }

    private static Long[] createHorizontals() {
        return IntStream.range(0, 8)
                        .mapToObj(parentIndex -> getDecimalValueFromBitboard(createRank(parentIndex)))
                        .toArray(Long[]::new);
    }

    private static String createRank(int parentIndex) {
        return IntStream.range(0, 8)
                        .mapToObj(childIndex -> childIndex == parentIndex ? "1".repeat(8) : "0".repeat(8))
                        .collect(Collectors.joining());
    }

    private static Long[] createPrimaryDiagonals() {
        return IntStream.range(0, 8)
                        .mapToObj(parentIndex -> getDecimalValueFromBitboard(createPrimaryDiagonal(parentIndex)))
                        .toArray(Long[]::new);
    }

    private static String createPrimaryDiagonal(int parentIndex) {
        return IntStream.range(0, 8)
                        .mapToObj(childIndex -> {
                            StringBuilder stringBuilder = new StringBuilder("0".repeat(8));
                            if (7 - parentIndex + childIndex >= 0 && childIndex <= parentIndex) {
                                stringBuilder.setCharAt(7 - parentIndex + childIndex, '1');
                            }
                            return stringBuilder.toString();
                        })
                        .collect(Collectors.joining());
    }

    private static Long[] createSecondaryDiagonals() {
        return IntStream.range(0, 8)
                        .mapToObj(parentIndex -> getDecimalValueFromBitboard(createSecondaryDiagonal(parentIndex)))
                        .toArray(Long[]::new);
    }

    private static String createSecondaryDiagonal(int parentIndex) {
        return IntStream.range(0, 8)
                        .mapToObj(childIndex -> {
                            StringBuilder stringBuilder = new StringBuilder("0".repeat(8));
                            if ((parentIndex - childIndex) <= 7 && childIndex <= parentIndex) {
                                stringBuilder.setCharAt(parentIndex - childIndex, '1');
                            }
                            return stringBuilder.toString();
                        })
                        .collect(Collectors.joining());
    }

    private static Long[] createVerticals() {
        return IntStream.range(0, 8)
                        .mapToObj(parentIndex -> getDecimalValueFromBitboard(createFile(parentIndex)))
                        .toArray(Long[]::new);
    }

    private static String createFile(int parentIndex) {
        return IntStream.range(0, 8)
                        .mapToObj(childIndex -> {
                            var stringBuilder = new StringBuilder("0".repeat(8));
                            stringBuilder.setCharAt(parentIndex, '1');
                            return stringBuilder.toString();
                        })
                        .collect(Collectors.joining());
    }
}
