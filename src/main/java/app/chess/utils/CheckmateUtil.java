package app.chess.utils;

import app.chess.board.Board;

public interface CheckmateUtil {
    boolean isCheckmate(int[] kingPosition, Board board,boolean isWhiteKing);
    boolean isStalemate(int[] kingPosition, Board board,boolean isWhiteKing);
}
