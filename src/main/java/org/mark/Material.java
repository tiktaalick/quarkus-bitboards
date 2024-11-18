package org.mark;

public enum Material {
    WHITE_PAWN("P"),
    WHITE_ROOK("R"),
    WHITE_KNIGHT("N"),
    WHITE_BISHOP("B"),
    WHITE_QUEEN("Q"),
    WHITE_KING("K"),
    BLACK_PAWN("p"),
    BLACK_ROOK("r"),
    BLACK_KNIGHT("n"),
    BLACK_BISHOP("b"),
    BLACK_QUEEN("q"),
    BLACK_KING("k"),
    NO_WHITE_MATERIAL_NO_BLACK_KING("~WHITE"),
    NO_BLACK_MATERIAL_NO_WHITE_KING("~BLACK"),
    WHITE_MATERIAL_TO_CAPTURE("WHITE"),
    BLACK_MATERIAL_TO_CAPTURE("BLACK"),
    EMPTY_SQUARES("EMPTY");

    private final String code;

    Material(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
