package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtil;
import app.chess.utils.ValidationsUtilImpl;

public class Bishop implements Piece {

    private static Bishop instance;

    private Bishop() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        if (Math.abs(start[0] - end[0]) != Math.abs(start[1] - end[1])) {
            return false;
        }
        int rowIncrement = (end[0] > start[0]) ? 1 : -1;
        int colIncrement = (end[1] > start[1]) ? 1 : -1;

        int r = start[0] + rowIncrement;
        int c = start[1] + colIncrement;

        while (r != end[0] && c != end[1]) {
            if (!board.getBoard()[r][c].equals(PieceNotation.EMPTY_SQUARE.value())) {
                return false;
            }
            r += rowIncrement;
            c += colIncrement;
        }
        return true;
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
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
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

    public static Bishop getInstance() {
        if (instance == null) {
            instance = new Bishop();
        }
        return instance;
    }

}
