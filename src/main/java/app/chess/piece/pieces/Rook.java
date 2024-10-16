package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtil;
import app.chess.utils.ValidationsUtilImpl;

public class Rook implements Piece {


    private static Rook instance;

    private Rook() {

    }

    @Override
    public boolean isValidMove(int[] start,int[] end, Board board) {
        if(start[0]!=end[0]&&start[1]!=end[1]){
            return false;
        }
        if(start[0]==end[0]){
            return checkHorizontal(start,end,board);
        }else {
            return checkVertical(start,end,board);
        }
    }

    @Override
    public void movePieceOnBoard(int[] start, int[] end, Board board) {
        board.move(start, end);

        board.setPreviousMove(new String[]{
                Mappings.getChessNotation(start[0], start[1]),
                Mappings.getChessNotation(end[0], end[1])
        });

        updateCastlingStatus(board, start);
    }

    @Override
    public boolean hasAnyLegalMove(int[] position, Board board) {
        int[][] directions = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
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

    private void updateCastlingStatus(Board board, int[] start) {
        char pieceColor = board.getBoard()[start[0]][start[1]].charAt(0);

        if (pieceColor == 'W') {
            if (start[1] == 1 && board.isWhiteLongCastlePossible()) {
                board.setWhiteLongCastlePossible(false);
            } else if (start[1] == 8 && board.isWhiteShortCastlePossible()) {
                board.setWhiteShortCastlePossible(false);
            }
        } else {
            if (start[1] == 1 && board.isBlackLongCastlePossible()) {
                board.setBlackLongCastlePossible(false);
            } else if (start[1] == 8 && board.isBlackShortCastlePossible()) {
                board.setBlackShortCastlePossible(false);
            }
        }
    }

    private boolean checkHorizontal(int[] start,int[] end, Board board) {
        int increment = start[1]>end[1]?-1:1;
        int s = start[1]+increment;
        while(s!=end[1]){
            if(!board.getBoard()[start[0]][s].equals(PieceNotation.EMPTY_SQUARE.value())){
                return false;
            }
            s+=increment;
        }
        return true;
    }

    private boolean checkVertical(int[] start,int[] end, Board board) {
        int increment = start[0]>end[0]?-1:1;
        int s = start[0]+increment;
        while(s!=end[0]){
            if(!board.getBoard()[s][start[1]].equals(PieceNotation.EMPTY_SQUARE.value())){
                return false;
            }
            s+=increment;
        }
        return true;
    }

    public static Rook getInstance() {
        if(instance==null) {
            instance = new Rook();
        }
        return instance;
    }

}
