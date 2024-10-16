package app.chess.utils;

import app.chess.board.Board;

public interface ValidationsUtil {

    boolean isValidCoordinate(int[] coordinate);
    boolean isValidNotation(String start);
    boolean isCheckOnKing(int[] kingPosition, String[][] board, boolean isWhiteKing);
    boolean isSameEnd(int[] start,int[] end);
    boolean isValidPlayerTurn(int[] start, Board board, boolean isWhiteTurn);
    boolean isSameColorPieceAtEnd(int[] start,int[] end, Board board);
    boolean isEmptySquare(int[] start, Board board);
}
