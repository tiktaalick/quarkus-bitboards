package org.mark;

public enum Bitboard {
    NO_WHITE_MATERIAL_NO_BLACK_KING,
    NO_BLACK_MATERIAL_NO_WHITE_KING,
    WHITE_MATERIAL_TO_CAPTURE,
    BLACK_MATERIAL_TO_CAPTURE,
    EMPTY_SQUARES,
    EN_PASSANT_MOVE;

    public int getIndex() {
        return Material.values().length + ordinal();
    }
}
