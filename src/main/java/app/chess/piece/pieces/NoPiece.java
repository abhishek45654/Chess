package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.piece.Piece;

public class NoPiece implements Piece {
    private static NoPiece instance;

    private NoPiece() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        return false;
    }

    @Override
    public void movePieceOnBoard(int[] start, int[] end, Board board) {
        board.move(start, end);
        board.setPreviousMove(new String[]{
                Mappings.getChessNotation(start[0], start[1]),
                Mappings.getChessNotation(end[0], end[1])});
    }

    @Override
    public boolean hasAnyLegalMove(int[] position, Board board) {
        return false;
    }

    public static NoPiece getInstance() {
        if(instance==null) {
            instance = new NoPiece();
        }
        return instance;
    }
}
