package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtil;
import app.chess.utils.ValidationsUtilImpl;

public class Queen implements Piece {

    private static Queen instance;

    private Queen() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        if(isRookMove(start,end)){
            return Rook.getInstance().isValidMove(start,end,board);
        }else if(isBishopMove(start,end)){
            return Bishop.getInstance().isValidMove(start,end,board);
        }
        return false;
    }

    private boolean isRookMove(int[] start, int[] end){
        return start[0]==end[0]||start[1]==end[1];
    }
    private boolean isBishopMove(int[] start, int[] end){
        return Math.abs(start[0]-end[0])==Math.abs(start[1]-end[1]);
    }

    public static Queen getInstance() {
        if(instance == null){
            instance = new Queen();
        }
        return instance;
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
        int[][] directions = {{1, 0}, {0, -1}, {-1, 0}, {0, 1},{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
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

}
