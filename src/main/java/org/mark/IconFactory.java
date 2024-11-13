package org.mark;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mark.UserInterface.squareSize;

public final class IconFactory {

    private static final String             DIRECTORY  = System.getProperty("user.dir") + "\\src\\main\\resources" +
            "\\images\\";
    private static final String             EXTENSION  = ".png";
    private static final Map<String, Image> ICONS      = new HashMap<>();
    private static final String             UNDERSCORE = "_";

    static {
        for (String playerColor : List.of("white", "black")) {
            for (String pieceType : List.of("pawn", "rook", "knight", "bishop", "queen", "king")) {
                ICONS.put(playerColor + " " + pieceType, createImage(pieceType, playerColor));
            }
        }
    }

    private IconFactory() {
    }

    public static Image getIcon(String playerColor, String pieceType) {
        if (playerColor == null || pieceType == null) {
            return null;
        }

        return ICONS.get(playerColor + " " + pieceType);
    }

    private static String createIconPath(String pieceType, String color) {
        return DIRECTORY + color + UNDERSCORE + pieceType + EXTENSION;
    }

    private static Image createImage(String pieceType, String playerColor) {
        return getResource(createIconPath(pieceType, playerColor))
                .getScaledInstance((int) squareSize, (int) squareSize, Image.SCALE_SMOOTH);
    }

    private static Image getResource(String iconPath) {
        return new ImageIcon(iconPath).getImage();
    }
}
