package app.chess.validation;

import app.chess.board.Board;
import app.chess.piece.PieceFactory;
import app.chess.utils.ValidationsUtilImpl;
import app.chess.utils.ValidationsUtil;

public class ValidationImpl implements Validation {

    private static ValidationImpl instance;

    private ValidationImpl() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board, boolean isWhiteTurn) {
        if(!isBasicValidationsPassed(start,end,board,isWhiteTurn)){
            return false;
        }
        if(board.getBoard()[start[0]][start[1]].charAt(1)!='K'&&!isValidPieceMove(start,end,board)){
            return false;
        }
        else if(board.getBoard()[start[0]][start[1]].charAt(1)=='K'){
            return isValidPieceMove(start,end,board);
        }
        int[] kingPosition = isWhiteTurn?board.getWhiteKingPosition():board.getBlackKingPosition();
        return !ValidationsUtilImpl.validationUtils().isCheckOnKing(kingPosition,board.getBoard(),isWhiteTurn);
    }
    public boolean isBasicValidationsPassed(int[] start, int[] end, Board board,
                                          boolean isWhiteTurn){
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();

        return validationsUtil.isValidCoordinate(start) &&
                validationsUtil.isValidCoordinate(end)&&
                !validationsUtil.isEmptySquare(start, board) &&
                !validationsUtil.isSameEnd(start, end) &&
                validationsUtil.isValidPlayerTurn(start, board, isWhiteTurn) &&
                !validationsUtil.isSameColorPieceAtEnd(start, end, board);
    }

    public boolean isValidPieceMove(int[] start, int[] end, Board board) {
        char piece = board.getBoard()[start[0]][start[1]].charAt(1);
        return PieceFactory.getPiece(piece).isValidMove(start,end,board);
    }

    public static ValidationImpl getInstance() {
        if(instance==null) {
            instance = new ValidationImpl();
        }
        return instance;
    }

}
