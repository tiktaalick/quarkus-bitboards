package org.mark;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.stream.IntStream;

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

    private static final Color  BACKGROUND            = new Color(240, 240, 240);
    private static final String BISHOP                = "bishop";
    private static final String BLACK                 = "black";
    private static final int    BORDER                = 20;
    private static final Color  DARK                  = new Color(170, 110, 90);
    private static final int    FILE_LENGTH           = 8;
    private static final int    FILE_LENGTH_MINUS_ONE = 7;
    private static final String KING                  = "king";
    private static final String KNIGHT                = "knight";
    private static final Color  LIGHT                 = new Color(240, 240, 200);
    private static final double NINETY_PERCENT        = 0.9;
    private static final int    NUMBER_OF_SQUARES     = 64;
    private static final String PAWN                  = "pawn";
    private static final String QUEEN                 = "queen";
    private static final String ROOK                  = "rook";
    private static final int    TITLE_BAR_SIZE        = 40;
    private static final String WHITE                 = "white";

    public static  double        squareSize;
    private static Long[]        boards;
    private static int           humanIsWhite  = 1;
    private static JFrame        jFrame        = new JFrame("Bitboards chess app");
    private static int           rating        = 0;
    private static UserInterface userInterface = new UserInterface();

    public static void main(String[] args) {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(userInterface);
        jFrame.setSize((int) (Toolkit.getDefaultToolkit()
                                     .getScreenSize().width * NINETY_PERCENT),
                (int) (Toolkit.getDefaultToolkit()
                              .getScreenSize().height * NINETY_PERCENT));
        jFrame.setLocation((Toolkit.getDefaultToolkit()
                                   .getScreenSize().width - jFrame.getWidth()) / 2,
                (Toolkit.getDefaultToolkit()
                        .getScreenSize().height - jFrame.getHeight()) / 2);
        squareSize = (double) (Math.min(jFrame.getHeight(), jFrame.getWidth()) - 2 * BORDER - TITLE_BAR_SIZE) / FILE_LENGTH;
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
        this.setBackground(BACKGROUND);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize = (double) (Math.min(jFrame.getHeight(), jFrame.getWidth()) - 2 * BORDER - TITLE_BAR_SIZE) /
                        FILE_LENGTH;
            }
        });
        drawBorders(g);
        drawBoard(g);
        drawPieces(g);
    }

    public void drawBorders(Graphics g) {
        g.setColor(LIGHT);
        g.fill3DRect(0, 0, BORDER, (int) (FILE_LENGTH * squareSize) + BORDER, true);
        g.fill3DRect((int) (FILE_LENGTH * squareSize) + BORDER, BORDER, BORDER, (int) (FILE_LENGTH * squareSize) + BORDER, true);
        g.fill3DRect(BORDER, 0, (int) (FILE_LENGTH * squareSize) + BORDER, BORDER, true);
        g.fill3DRect(0, (int) (FILE_LENGTH * squareSize) + BORDER, (int) (FILE_LENGTH * squareSize) + BORDER, BORDER, true);
    }

    public void drawBoard(Graphics graphics) {
        IntStream.range(0, NUMBER_OF_SQUARES)
                 .filter(index -> index % 2 == 0)
                 .forEach(index -> {
                     drawLightSquares(graphics, index);
                     drawDarkSquares(graphics, index);
                 });
    }

    public void drawPieces(Graphics g) {
        IntStream.range(0, NUMBER_OF_SQUARES)
                 .forEach(index -> {
                     Image chessPieceImage = null;
                     if (((boards[WHITE_PAWN.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, PAWN);
                     } else if (((boards[WHITE_BISHOP.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, BISHOP);
                     } else if (((boards[WHITE_KNIGHT.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, KNIGHT);
                     } else if (((boards[WHITE_QUEEN.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, QUEEN);
                     } else if (((boards[WHITE_ROOK.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, ROOK);
                     } else if (((boards[WHITE_KING.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(WHITE, KING);
                     } else if (((boards[BLACK_PAWN.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, PAWN);
                     } else if (((boards[BLACK_BISHOP.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, BISHOP);
                     } else if (((boards[BLACK_KNIGHT.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, KNIGHT);
                     } else if (((boards[BLACK_QUEEN.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, QUEEN);
                     } else if (((boards[BLACK_ROOK.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, ROOK);
                     } else if (((boards[BLACK_KING.ordinal()] >> index) & 1) == 1) {
                         chessPieceImage = IconFactory.getIcon(BLACK, KING);
                     }
                     g.drawImage(chessPieceImage,
                             (int) ((FILE_LENGTH_MINUS_ONE - (index % FILE_LENGTH)) * squareSize) + BORDER,
                             (int) ((FILE_LENGTH_MINUS_ONE - (index / FILE_LENGTH)) * squareSize) + BORDER,
                             (int) (squareSize),
                             (int) (squareSize),
                             this);
                 });
    }

    private static void drawLightSquares(Graphics g, int index) {
        g.setColor(LIGHT);
        g.fill3DRect((int) ((index % FILE_LENGTH + (index / FILE_LENGTH) % 2) * squareSize) + BORDER,
                (int) ((index / FILE_LENGTH) * squareSize) + BORDER,
                (int) squareSize,
                (int) squareSize,
                true);
    }

    private static void drawDarkSquares(Graphics g, int index) {
        g.setColor(DARK);
        g.fill3DRect((int) (((index + 1) % FILE_LENGTH - ((index + 1) / FILE_LENGTH) % 2) * squareSize) + BORDER,
                (int) (((index + 1) / FILE_LENGTH) * squareSize) + BORDER,
                (int) squareSize,
                (int) squareSize,
                true);
    }
}
