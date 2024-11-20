package org.mark.frontend;

import org.mark.Material;
import org.mark.moves.MovesFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mark.bitboards.Bitboard.EN_PASSANT_MOVE;
import static org.mark.bitboards.BitboardFactory.createDecimalsFromBitboards;
import static org.mark.bitboards.BitboardFactory.createInitialBitboardsWhitePlayer;
import static org.mark.bitboards.BitboardFactory.getDecimalValueFromBitboard;

public final class UserInterface extends JPanel {

    private static final Color         BACKGROUND            = new Color(240, 240, 240);
    private static final int           BORDER                = 20;
    private static final Color         DARK                  = new Color(170, 110, 90);
    private static final int           FILE_LENGTH           = 8;
    private static final int           FILE_LENGTH_MINUS_ONE = 7;
    private static final JFrame        JFRAME                = new JFrame("Bitboards chess app");
    private static final Color         LIGHT                 = new Color(240, 240, 200);
    private static final double        NINETY_PERCENT        = 0.9;
    private static final int           NUMBER_OF_SQUARES     = 64;
    private static final double        SIXTY_PERCENT         = 0.6;
    private static final int           TITLE_BAR_SIZE        = 40;
    private static final UserInterface USER_INTERFACE        = new UserInterface();

    private static Long[] boards;
    private static double squareSize;

    public static double getSquareSize() {
        return squareSize;
    }

    public static void main(String[] args) {
        JFRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFRAME.add(USER_INTERFACE);
        JFRAME.setSize((int) (Toolkit.getDefaultToolkit()
                                     .getScreenSize().height * NINETY_PERCENT - TITLE_BAR_SIZE * SIXTY_PERCENT),
                (int) (Toolkit.getDefaultToolkit()
                              .getScreenSize().height * NINETY_PERCENT));
        JFRAME.setLocation((Toolkit.getDefaultToolkit()
                                   .getScreenSize().width - JFRAME.getWidth()) / 2,
                (Toolkit.getDefaultToolkit()
                        .getScreenSize().height - JFRAME.getHeight()) / 2);
        squareSize = (double) (JFRAME.getHeight() - 2 * BORDER - TITLE_BAR_SIZE) / FILE_LENGTH;
        JFRAME.setVisible(true);
        JFRAME.setResizable(false);
        newGame();
        JFRAME.repaint();
    }

    public static void newGame() {
        boards = createDecimalsFromBitboards(createInitialBitboardsWhitePlayer());
        boards = doADummyMoveForBlack(boards);
        JFRAME.setTitle(MovesFactory.createPossibleMovesForWhite(boards));
    }

    private static Long[] doADummyMoveForBlack(Long[] boards) {
        List<Long> boardsList = new ArrayList<>(List.of(boards));
        boardsList.add(EN_PASSANT_MOVE.getIndex(), getDecimalValueFromBitboard("""
                                                                               00000000
                                                                               00000000
                                                                               00000100
                                                                               00000000
                                                                               00000000
                                                                               00000000
                                                                               00000000
                                                                               00000000"""));
        return boardsList.toArray(Long[]::new);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(BACKGROUND);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize = (double) (Math.min(JFRAME.getHeight(), JFRAME.getWidth()) - 2 * BORDER - TITLE_BAR_SIZE) /
                        FILE_LENGTH;
            }
        });
        drawBorders(graphics);
        drawBoard(graphics);
        drawMaterial(graphics);
    }

    public static void drawBorders(Graphics graphics) {
        graphics.setColor(LIGHT);
        graphics.fill3DRect(0, 0, BORDER, (int) (FILE_LENGTH * squareSize) + BORDER, true);
        graphics.fill3DRect((int) (FILE_LENGTH * squareSize) + BORDER,
                BORDER,
                BORDER,
                (int) (FILE_LENGTH * squareSize) + BORDER,
                true);
        graphics.fill3DRect(BORDER, 0, (int) (FILE_LENGTH * squareSize) + BORDER, BORDER, true);
        graphics.fill3DRect(0,
                (int) (FILE_LENGTH * squareSize) + BORDER,
                (int) (FILE_LENGTH * squareSize) + BORDER,
                BORDER,
                true);
    }

    public static void drawBoard(Graphics graphics) {
        IntStream.range(0, NUMBER_OF_SQUARES)
                 .filter(index -> index % 2 == 0)
                 .forEach(index -> {
                     drawLightSquares(graphics, index);
                     drawDarkSquares(graphics, index);
                 });
    }

    public void drawMaterial(Graphics graphics) {
        IntStream.range(0, NUMBER_OF_SQUARES)
                 .forEach(index -> {
                     for (Material material : Material.values()) {
                         if (isASquareThatContainsMaterial(index, material)) {
                             drawImage(graphics, index, material);
                         }
                     }
                 });
    }

    private static void drawLightSquares(Graphics graphics, int index) {
        graphics.setColor(LIGHT);
        graphics.fill3DRect((int) ((index % FILE_LENGTH + (index / FILE_LENGTH) % 2) * squareSize) + BORDER,
                (int) ((index / FILE_LENGTH) * squareSize) + BORDER,
                (int) squareSize,
                (int) squareSize,
                true);
    }

    private static void drawDarkSquares(Graphics graphics, int index) {
        graphics.setColor(DARK);
        graphics.fill3DRect((int) (((index + 1) % FILE_LENGTH - ((index + 1) / FILE_LENGTH) % 2) * squareSize) + BORDER,
                (int) (((index + 1) / FILE_LENGTH) * squareSize) + BORDER,
                (int) squareSize,
                (int) squareSize,
                true);
    }

    private static boolean isASquareThatContainsMaterial(int index, Material material) {
        return ((boards[material.ordinal()] >> index) & 1) == 1;
    }

    private void drawImage(Graphics graphics, int index, Material material) {
        graphics.drawImage(IconFactory.getIcon(material.getName()),
                (int) ((FILE_LENGTH_MINUS_ONE - (index % FILE_LENGTH)) * squareSize) + BORDER,
                (int) ((FILE_LENGTH_MINUS_ONE - (index / FILE_LENGTH)) * squareSize) + BORDER,
                (int) (squareSize),
                (int) (squareSize),
                this);
    }
}
