package org.mark;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UserInterface extends JPanel {

    public static  double        squareSize   = 64;
    private static long          BB           = 0L;
    private static long          BK           = 0L;
    private static long          BN           = 0L;
    private static long          BP           = 0L;
    private static long          BQ           = 0L;
    private static long          BR           = 0L;
    private static long          WB           = 0L;
    private static long          WK           = 0L;
    private static long          WN           = 0L;
    private static long          WP           = 0L;
    private static long          WQ           = 0L;
    private static long          WR           = 0L;
    private static int           border       = 10;
    private static int           humanIsWhite = 1;
    private static JFrame        jFrame       = new JFrame("Bitboards chess app");
    private static int           rating       = 0;
    private static UserInterface ui           = new UserInterface();
    private static long          universalBB  = 0L;
    private static long          universalBK  = 0L;
    private static long          universalBN  = 0L;
    private static long          universalBP  = 0L;
    private static long          universalBQ  = 0L;
    private static long          universalBR  = 0L;
    private static long          universalWB  = 0L;
    private static long          universalWK  = 0L;
    private static long          universalWN  = 0L;
    private static long          universalWP  = 0L;
    private static long          universalWQ  = 0L;
    private static long          universalWR  = 0L;

    public static void main(String[] args) {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(ui);
        jFrame.setSize(757, 570);
        jFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - jFrame.getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - jFrame.getHeight()) / 2);
        jFrame.setVisible(true);
        newGame();
        jFrame.repaint();
    }

    public static void newGame() {
        BoardGeneration.initiateChess960();
    }

    public void drawBoard(Graphics g) {
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(255, 200, 100));
            g.fillRect((int) ((i % 8 + (i / 8) % 2) * squareSize) + border,
                    (int) ((i / 8) * squareSize) + border,
                    (int) squareSize,
                    (int) squareSize);
            g.setColor(new Color(150, 50, 30));
            g.fillRect((int) (((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize) + border,
                    (int) (((i + 1) / 8) * squareSize) + border,
                    (int) squareSize,
                    (int) squareSize);
        }
    }

    public void drawBorders(Graphics g) {
        g.setColor(new Color(100, 0, 0));
        g.fill3DRect(0, border, border, (int) (8 * squareSize), true);
        g.fill3DRect((int) (8 * squareSize) + border, border, border, (int) (8 * squareSize), true);
        g.fill3DRect(border, 0, (int) (8 * squareSize), border, true);
        g.fill3DRect(border, (int) (8 * squareSize) + border, (int) (8 * squareSize), border, true);

        g.setColor(Color.BLACK);
        g.fill3DRect(0, 0, border, border, true);
        g.fill3DRect((int) (8 * squareSize) + border, 0, border, border, true);
        g.fill3DRect(0, (int) (8 * squareSize) + border, border, border, true);
        g.fill3DRect((int) (8 * squareSize) + border, (int) (8 * squareSize) + border, border, border, true);
        g.fill3DRect((int) (8 * squareSize) + 2 + border + 200, 0, border, border, true);
        g.fill3DRect((int) (8 * squareSize) + 2 * border + 200, (int) (8 * squareSize) + border, border, border, true);
    }

    public void drawPieces(Graphics g) {
        Image chessPieceImage = null;
        for (int i = 0; i < 64; i++) {
            if (((WP >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "pawn");
            } else if (((WB >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "bishop");
            } else if (((WN >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "knight");
            } else if (((WQ >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "queen");
            } else if (((WR >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "rook");
            } else if (((WK >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("white", "king");
            } else if (((BP >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "pawn");
            } else if (((BB >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "bishop");
            } else if (((BN >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "knight");
            } else if (((BQ >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "queen");
            } else if (((BR >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "rook");
            } else if (((BK >> i) & 1) == 1) {
                chessPieceImage = IconFactory.getIcon("black", "king");
            }
            g.drawImage(chessPieceImage,
                    (int) ((i % 8) * squareSize) + border,
                    (int) ((i / 8) * squareSize) + border,
                    (int) ((i % 8 + 1) * squareSize),
                    (int) ((i % 8 + 1) * squareSize),
                    this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(200, 100, 0));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize = (double) (Math.min(getHeight(), getWidth() - 200 - border) - 2 * border) / 8;
            }
        });
        drawBorders(g);
        drawBoard(g);
        drawPieces(g);
    }
}
