package app.chess.validation;

import app.chess.board.Board;

public interface Validation {
    boolean isValidMove(int[] start, int[] end, Board board, boolean whiteTurn);
}
