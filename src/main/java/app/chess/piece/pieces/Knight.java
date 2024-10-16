package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtil;
import app.chess.utils.ValidationsUtilImpl;

public class Knight implements Piece {

    private static Knight instance;

    private Knight() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        return (Math.abs(start[0] - end[0]) != 1 || Math.abs(start[1] - end[1]) == 2) &&
                (Math.abs(start[1] - end[1]) != 1 || Math.abs(start[0] - end[0]) == 2);
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
        int[][] directions = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();
        for (int[] direction : directions) {
            int newRow = position[0] + direction[0];
            int newCol = position[1] + direction[1];
            if (validationsUtil.isValidCoordinate(new int[]{newRow, newCol}) &&
                    (validationsUtil.isEmptySquare(new int[]{newRow,newCol},board) ||
                            validationsUtil.isSameColorPieceAtEnd(position,new int[]{newRow,newCol},board))) {
                return true;
            }
        }
        return false;
    }

    public static Knight getInstance() {
        if(instance==null) {
            instance = new Knight();
        }
        return instance;
    }
}
