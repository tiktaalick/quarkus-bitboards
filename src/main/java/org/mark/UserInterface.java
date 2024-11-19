package org.mark;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.stream.IntStream;

import static org.mark.BitboardFactory.createDecimalsFromBitboards;
import static org.mark.BitboardFactory.createInitialBitboardsWhitePlayer;

public class UserInterface extends JPanel {

    private static final Color  BACKGROUND            = new Color(240, 240, 240);
    private static final int    BORDER                = 20;
    private static final Color  DARK                  = new Color(170, 110, 90);
    private static final int    FILE_LENGTH           = 8;
    private static final int    FILE_LENGTH_MINUS_ONE = 7;
    private static final Color  LIGHT                 = new Color(240, 240, 200);
    private static final double NINETY_PERCENT        = 0.9;
    private static final int    NUMBER_OF_SQUARES     = 64;
    private static final int    TITLE_BAR_SIZE        = 40;

    private static double        squareSize;
    private static Long[]        boards;
    private static JFrame        jFrame        = new JFrame("Bitboards chess app");
    private static UserInterface userInterface = new UserInterface();

    public static double getSquareSize() {
        return squareSize;
    }

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
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(BACKGROUND);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize = (double) (Math.min(jFrame.getHeight(), jFrame.getWidth()) - 2 * BORDER - TITLE_BAR_SIZE) /
                        FILE_LENGTH;
            }
        });
        drawBorders(graphics);
        drawBoard(graphics);
        drawMaterial(graphics);
    }

    public void drawBorders(Graphics graphics) {
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

    public void drawBoard(Graphics graphics) {
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
