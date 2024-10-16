package app.chess.board;

import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;

public class Board {

    private String[][] board;
    private boolean isWhiteShortCastlePossible;
    private boolean isWhiteLongCastlePossible;
    private boolean isBlackShortCastlePossible;
    private boolean isBlackLongCastlePossible;
    private String[] previousMove;
    private int[] whiteKingPosition;
    private int[] blackKingPosition;

    public Board() {
        this.initialize();
        this.isWhiteShortCastlePossible = true;
        this.isWhiteLongCastlePossible = true;
        this.isBlackShortCastlePossible = true;
        this.isBlackLongCastlePossible = true;
    }

    public String[][] getBoard(){
        return board;
    }

    public boolean isWhiteShortCastlePossible() {
        return isWhiteShortCastlePossible;
    }

    public boolean isWhiteLongCastlePossible() {
        return isWhiteLongCastlePossible;
    }

    public boolean isBlackShortCastlePossible() {
        return isBlackShortCastlePossible;
    }

    public boolean isBlackLongCastlePossible() {
        return isBlackLongCastlePossible;
    }

    public int[] getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public int[] getBlackKingPosition() {
        return blackKingPosition;
    }

    public String[] getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(String[] previousMove) {
        this.previousMove = previousMove;
    }

    public void setWhiteShortCastlePossible(boolean whiteShortCastlePossible) {
        isWhiteShortCastlePossible = whiteShortCastlePossible;
    }

    public void setWhiteLongCastlePossible(boolean whiteLongCastlePossible) {
        isWhiteLongCastlePossible = whiteLongCastlePossible;
    }

    public void setBlackShortCastlePossible(boolean blackShortCastlePossible) {
        isBlackShortCastlePossible = blackShortCastlePossible;
    }

    public void setBlackLongCastlePossible(boolean blackLongCastlePossible) {
        isBlackLongCastlePossible = blackLongCastlePossible;
    }

    public void setWhiteKingPosition(int[] whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public void setBlackKingPosition(int[] blackKingPosition) {
        this.blackKingPosition = blackKingPosition;
    }

    public void initialize() {
        board = new String[9][9];
        board[0][0] = "+";
        for (int i = 1; i < 9; i++) {
            board[i][0] = (char) ('0' + (9 - i)) + "";
            board[0][i] = (char) ('a' + i - 1) + "";
        }
        setupPieces();
        blackKingPosition = new int[]{1,5};
        whiteKingPosition = new int[]{8,5};
    }


    private void setupPieces() {
        board[1][1] = PieceNotation.BLACK_ROOK.value();
        board[1][2] = PieceNotation.BLACK_KNIGHT.value();
        board[1][3] = PieceNotation.BLACK_BISHOP.value();
        board[1][4] = PieceNotation.BLACK_QUEEN.value();
        board[1][5] = PieceNotation.BLACK_KING.value();
        board[1][6] = PieceNotation.BLACK_BISHOP.value();
        board[1][7] = PieceNotation.BLACK_KNIGHT.value();
        board[1][8] = PieceNotation.BLACK_ROOK.value();
        for (int i = 1; i < 9; i++) {
            board[2][i] = PieceNotation.BLACK_PAWN.value();
        }

        board[8][1] = PieceNotation.WHITE_ROOK.value();
        board[8][2] = PieceNotation.WHITE_KNIGHT.value();
        board[8][3] = PieceNotation.WHITE_BISHOP.value();
        board[8][4] = PieceNotation.WHITE_QUEEN.value();
        board[8][5] = PieceNotation.WHITE_KING.value();
        board[8][6] = PieceNotation.WHITE_BISHOP.value();
        board[8][7] = PieceNotation.WHITE_KNIGHT.value();
        board[8][8] = PieceNotation.WHITE_ROOK.value();
        for (int i = 1; i < 9; i++) {
            board[7][i] = PieceNotation.WHITE_PAWN.value();
        }

        // Empty squares
        for (int i = 3; i < 7; i++) {
            for (int j = 1; j < 9; j++) {
                board[i][j] = PieceNotation.EMPTY_SQUARE.value();
            }
        }
    }
    public void printPOVWhite() {
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");

            }
            System.out.println();
        }
        for (int i = 0; i < 9; i++) {
            System.out.print(board[0][i] + "  ");
        }
        System.out.println();
    }

    public void printPOVBlack() {
        for (int i = 8; i >= 1; i--) {
            System.out.print(board[i][0] + " ");
            for (int j = 8; j >= 1; j--) {
                System.out.print(board[i][j] + " ");

            }
            System.out.println();
        }
        System.out.print(board[0][0] + " ");
        for (int i = 8; i >=1; i--) {
            System.out.print(board[0][i] + "  ");
        }
        System.out.println();
    }

    public void move(int[] source, int[] destination) {
        board[destination[0]][destination[1]] = board[source[0]][source[1]];
        board[source[0]][source[1]] = PieceNotation.EMPTY_SQUARE.value();
    }


}
