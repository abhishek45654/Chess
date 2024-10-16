package app.chess.piece;

import app.chess.board.Board;

public interface Piece {

    boolean isValidMove(int[] start,int[] end, Board board);
    void movePieceOnBoard(int[] start, int[] end, Board board);
    boolean hasAnyLegalMove(int[] position,Board board);
}
