package org.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BoardGeneration {

    private static final Logger log = LoggerFactory.getLogger(BoardGeneration.class);

    public static void arrayToBitboards(String[][] chessBoard,
            long WP,
            long WN,
            long WB,
            long WR,
            long WQ,
            long WK,
            long BP,
            long BN,
            long BB,
            long BR,
            long BQ,
            long BK) {
        String binary;
        for (int i = 0; i < 64; i++) {
            binary = "0".repeat(64);
            binary = binary.substring(i + 1) + "1" + binary.substring(0, i);
            switch (chessBoard[i / 8][i % 8]) {
                case "P":
                    WP += convertStringToBitboard(binary);
                    break;
                case "N":
                    WN += convertStringToBitboard(binary);
                    break;
                case "B":
                    WB += convertStringToBitboard(binary);
                    break;
                case "R":
                    WR += convertStringToBitboard(binary);
                    break;
                case "Q":
                    WQ += convertStringToBitboard(binary);
                    break;
                case "K":
                    WK += convertStringToBitboard(binary);
                    break;
                case "p":
                    BP += convertStringToBitboard(binary);
                    break;
                case "n":
                    BN += convertStringToBitboard(binary);
                    break;
                case "b":
                    BB += convertStringToBitboard(binary);
                    break;
                case "r":
                    BR += convertStringToBitboard(binary);
                    break;
                case "q":
                    BQ += convertStringToBitboard(binary);
                    break;
                case "k":
                    BK += convertStringToBitboard(binary);
                    break;
            }
        }
        drawArray(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
    }

    public static void initiateChess960() {
        long WP = 0L;
        long WN = 0L;
        long WB = 0L;
        long WR = 0L;
        long WQ = 0L;
        long WK = 0L;
        long BP = 0L;
        long BN = 0L;
        long BB = 0L;
        long BR = 0L;
        long BQ = 0L;
        long BK = 0L;
        String chessBoard[][] = {
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {" ", " ", " ", " ", " ", " ", " ", " "}
        };
        // Step 1: Zet de eerste lopers ergens neer
        int indexEersteLoper = (int) (Math.random() * 8);
        chessBoard[0][indexEersteLoper] = "b";
        chessBoard[7][indexEersteLoper] = "B";

        // Step 2: Zet de tweede lopers ergens neer op een anders gekleurd vakje
        int index = (int) (Math.random() * 8);
        while (!" ".equals(chessBoard[0][index]) || index % 2 == indexEersteLoper % 2) {
            index = (int) (Math.random() * 8);
        }
        chessBoard[0][index] = "b";
        chessBoard[7][index] = "B";

        // Step 3: Zet de dames ergens neer
        index = (int) (Math.random() * 8);
        while (!" ".equals(chessBoard[0][index])) {
            index = (int) (Math.random() * 8);
        }
        chessBoard[0][index] = "q";
        chessBoard[7][index] = "Q";

        // Step 4a: Zet de eerste paarden ergens neer
        index = (int) (Math.random() * 8);
        while (!" ".equals(chessBoard[0][index])) {
            index++;
            if(index > 7) {
                index = 0;
            }
        }
        chessBoard[0][index] = "n";
        chessBoard[7][index] = "N";

        // Step 4b: Zet de tweede paarden ergens neer
        index = (int) (Math.random() * 8);
        while (!" ".equals(chessBoard[0][index])) {
            index++;
            if(index > 7) {
                index = 0;
            }
        }
        chessBoard[0][index] = "n";
        chessBoard[7][index] = "N";

        // Step 5a: Zet de eerste torens op de eerste lege plaats neer
        index = 0;
        while (!" ".equals(chessBoard[0][index])) {
            index++;
        }
        chessBoard[0][index] = "r";
        chessBoard[7][index] = "R";

        // Step 5b: Zet de koning op de tweede lege plaats neer
        index = 0;
        while (!" ".equals(chessBoard[0][index])) {
            index++;
        }
        chessBoard[0][index] = "k";
        chessBoard[7][index] = "K";

        // Step 5c: Zet de tweede torens op de derde lege plaats neer
        index = 0;
        while (!" ".equals(chessBoard[0][index])) {
            index++;
        }
        chessBoard[0][index] = "r";
        chessBoard[7][index] = "R";

        arrayToBitboards(chessBoard, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
    }

    public static void initiateStandardChess() {
        long WP = 0L;
        long WN = 0L;
        long WB = 0L;
        long WR = 0L;
        long WQ = 0L;
        long WK = 0L;
        long BP = 0L;
        long BN = 0L;
        long BB = 0L;
        long BR = 0L;
        long BQ = 0L;
        long BK = 0L;
        String chessBoard[][] = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}
        };
        arrayToBitboards(chessBoard, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
    }

    private static long convertStringToBitboard(String binary) {
        if (binary.charAt(0) == '0') {
            return Long.parseLong(binary, 2);
        } else {
            return Long.parseLong("1" + binary.substring(2), 2) * 2;
        }
    }

    private static void drawArray(long wp,
            long wn,
            long wb,
            long wr,
            long wq,
            long wk,
            long bp,
            long bn,
            long bb,
            long br,
            long bq,
            long bk) {
        String chessboard[][] = new String[8][8];
        for (int i = 0; i < 64; i++) {
            chessboard[i / 8][i % 8] = " ";
        }
        for (int i = 0; i < 64; i++) {
            if (((wp >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "P";
            }
            if (((wn >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "N";
            }
            if (((wb >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "B";
            }
            if (((wr >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "R";
            }
            if (((wq >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "Q";
            }
            if (((wk >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "K";
            }
            if (((bp >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "p";
            }
            if (((bn >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "n";
            }
            if (((bb >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "b";
            }
            if (((br >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "r";
            }
            if (((bq >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "q";
            }
            if (((bk >> i) & 1) == 1) {
                chessboard[i / 8][i % 8] = "k";
            }
        }
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(chessboard[i]));
        }
    }
}
