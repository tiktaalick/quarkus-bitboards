package org.mark;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static org.mark.BitboardFactory.createDecimalsFromBitboards;
import static org.mark.BitboardFactory.createInitialBitboardsWhitePlayer;
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

public class UserInterface extends JPanel {

    public static  double        squareSize   = 64;
    private static Long[]        boards;
    private static int           border       = 10;
    private static int           humanIsWhite = 1;
    private static JFrame        jFrame       = new JFrame("Bitboards chess app");
    private static int           rating       = 0;
    private static UserInterface ui           = new UserInterface();

    public static void main(String[] args) {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(ui);
        jFrame.setSize((int) (Toolkit.getDefaultToolkit()
                                     .getScreenSize().width * 0.9),
                (int) (Toolkit.getDefaultToolkit()
                              .getScreenSize().height * 0.9));
        jFrame.setLocation((Toolkit.getDefaultToolkit()
                                   .getScreenSize().width - jFrame.getWidth()) / 2,
                (Toolkit.getDefaultToolkit()
                        .getScreenSize().height - jFrame.getHeight()) / 2);
        squareSize = (double) (Math.min(jFrame.getHeight(), jFrame.getWidth()) - 2 * border - 40) / 8;
        jFrame.setVisible(true);
        newGame();
        jFrame.repaint();
    }

    public static void newGame() {
        boards = createDecimalsFromBitboards(createInitialBitboardsWhitePlayer());

        Moves.possibleMovesWhite("", boards);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(240, 240, 240));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize = (double) (Math.min(jFrame.getHeight(), jFrame.getWidth()) - 2 * border - 40) / 8;
            }
        });
        drawBorders(g);
        drawBoard(g);
        drawPieces(g);
    }

    public void drawBorders(Graphics g) {
        g.setColor(new Color(240, 240, 200));
        g.fill3DRect(0, 0, border, (int) (8 * squareSize) + border, true);
        g.fill3DRect((int) (8 * squareSize) + border, border, border, (int) (8 * squareSize) + border, true);
        g.fill3DRect(border, 0, (int) (8 * squareSize) + border, border, true);
        g.fill3DRect(0, (int) (8 * squareSize) + border, (int) (8 * squareSize) + border, border, true);
    }

    public void drawBoard(Graphics g) {
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(240, 240, 200));
            g.fill3DRect((int) ((i % 8 + (i / 8) % 2) * squareSize) + border,
                    (int) ((i / 8) * squareSize) + border,
                    (int) squareSize,
                    (int) squareSize,
                    true);
            g.setColor(new Color(170, 110, 90));
            g.fill3DRect((int) (((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize) + border,
                    (int) (((i + 1) / 8) * squareSize) + border,
                    (int) squareSize,
                    (int) squareSize,
                    true);
        }
    }

    public void drawPieces(Graphics g) {
        for (int i = 0; i < 64; i++) {
            Image chessPieceImage = null;
            if (((boards[WHITE_PAWN.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "pawn");
            } else if (((boards[WHITE_BISHOP.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "bishop");
            } else if (((boards[WHITE_KNIGHT.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "knight");
            } else if (((boards[WHITE_QUEEN.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "queen");
            } else if (((boards[WHITE_ROOK.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "rook");
            } else if (((boards[WHITE_KING.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "king");
            } else if (((boards[BLACK_PAWN.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "pawn");
            } else if (((boards[BLACK_BISHOP.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "bishop");
            } else if (((boards[BLACK_KNIGHT.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "knight");
            } else if (((boards[BLACK_QUEEN.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "queen");
            } else if (((boards[BLACK_ROOK.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "rook");
            } else if (((boards[BLACK_KING.ordinal()] >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "king");
            }
            g.drawImage(chessPieceImage,
                    (int) ((7 - (i % 8)) * squareSize) + border,
                    (int) ((7 - (i / 8)) * squareSize) + border,
                    (int) (squareSize),
                    (int) (squareSize),
                    this);
        }
    }
}
