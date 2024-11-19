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
    BLACK_KING("k", "black king");

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
