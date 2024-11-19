package org.mark;

public enum Material {
    WHITE_PAWN("P", "white pawn"),
    WHITE_ROOK("R", "white rook"),
    WHITE_KNIGHT("N", "white knight"),
    WHITE_BISHOP("B", "white bishop"),
    WHITE_QUEEN("Q", "white queen"),
    WHITE_KING("K", "white king"),
    BLACK_PAWN("p", "black pawn"),
    BLACK_ROOK("r", "black rook"),
    BLACK_KNIGHT("n", "black knight"),
    BLACK_BISHOP("b", "black bishop"),
    BLACK_QUEEN("q", "black queen"),
    BLACK_KING("k", "black king"),
    NO_WHITE_MATERIAL_NO_BLACK_KING("~WHITE", "no white material, no black king"),
    NO_BLACK_MATERIAL_NO_WHITE_KING("~BLACK", "no black material, no white king"),
    WHITE_MATERIAL_TO_CAPTURE("WHITE", "white material to capture"),
    BLACK_MATERIAL_TO_CAPTURE("BLACK", "black material to capture"),
    EMPTY_SQUARES("EMPTY", "empty squares");

    private final String code;
    private final String name;

    Material(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
