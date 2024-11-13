package org.mark;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.mark.UserInterface.squareSize;

public final class IconFactory {

    private static final String                 EXTENSION  = ".png";
    private static final Map<String, Image> ICONS      = new HashMap<>();
    private static final String                 UNDERSCORE = "_";

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
        return color + UNDERSCORE + pieceType + EXTENSION;
    }

    private static Image createImage(String pieceType, String playerColor) {
        return getResource(createIconPath(pieceType, playerColor))
                .getImage()
                .getScaledInstance((int) squareSize, (int) squareSize, Image.SCALE_SMOOTH);
    }

    private static ImageIcon getResource(String iconPath) {
        ImageIcon icon;

        try {
            icon = new ImageIcon(ImageIO.read(Objects.requireNonNull(Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource(iconPath))));
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return icon;
    }
}
