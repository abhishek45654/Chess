package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtilImpl;
import app.chess.utils.ValidationsUtil;

public class King implements Piece {

    private static King instance;

    private King() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        boolean isWhiteKing = board.getBoard()[start[0]][start[1]].charAt(0)=='W';

        if(isCastlePossible(start, end, board, isWhiteKing)) {
            return true;
        }
        if(isOpponentKingAround(end,board,isWhiteKing)){
            return false;
        }
        return !ValidationsUtilImpl.validationUtils().isCheckOnKing(end, board.getBoard(),isWhiteKing);
    }

    @Override
    public void movePieceOnBoard(int[] start, int[] end, Board board) {
        if (Math.abs(start[1] - end[1]) == 2) {
            performCastle(start, end, board);
        } else {
            board.move(start, end);
        }

        board.setPreviousMove(new String[]{
                Mappings.getChessNotation(start[0], start[1]),
                Mappings.getChessNotation(end[0], end[1])
        });
        if (board.getBoard()[end[0]][end[1]].charAt(0) == 'W') {
            board.setWhiteKingPosition(end);
        } else {
            board.setBlackKingPosition(end);
        }
        updateCastlingStatus(board, start);
    }

    @Override
    public boolean hasAnyLegalMove(int[] position, Board board) {
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();
        boolean isWhiteKing = board.getBoard()[position[0]][position[1]].charAt(0) == 'W';
        int[][] directions = {
                {1,1},{1,-1},{1,0},{-1,-1},{-1,1},{-1,0},{0,1},{0,-1}
        };
        for(int[] direction:directions) {
            int newRow = position[0]+direction[0];
            int newCol = position[1]+direction[1];
            if(validationsUtil.isValidCoordinate(new int[]{newRow,newCol})&&
                    !validationsUtil.isCheckOnKing(new int[]{newRow,newCol},board.getBoard(),isWhiteKing)){
                return true;
            }
        }
        return false;
    }

    private void performCastle(int[] start, int[] end, Board board) {
        board.move(start, end);
        int rookCol = start[1] < end[1] ? 8 : 1;
        int rookDestinationCol = start[1] < end[1] ? 6 : 4;
        board.move(new int[]{start[0], rookCol}, new int[]{start[0], rookDestinationCol});
    }

    private void updateCastlingStatus(Board board, int[] start) {
        if (board.getBoard()[start[0]][start[1]].charAt(0) == 'W') {
            board.setWhiteShortCastlePossible(false);
            board.setWhiteLongCastlePossible(false);
        } else {
            board.setBlackShortCastlePossible(false);
            board.setBlackLongCastlePossible(false);
        }
    }

    private boolean isOpponentKingAround(int[] end, Board board,boolean isWhiteKing){
        String opponentPrefix = isWhiteKing?"B":"W";
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();
        int[][] directions = {
                {1,1},{1,-1},{1,0},{-1,-1},{-1,1},{-1,0},{0,1},{0,-1}
        };
        for (int[] direction : directions) {
            int newRow = end[0] + direction[0];
            int newCol = end[1] + direction[1];

            if (validationsUtil.isValidCoordinate(new int[]{newRow,newCol}) &&
                    board.getBoard()[newRow][newCol].equals(opponentPrefix + "K")) {
                return true;
            }
        }
        return false;
    }

    public static King getInstance() {
        if(instance == null){
            instance = new King();
        }
        return instance;
    }
    private boolean isCastlePossible(int[] start, int[]end, Board board,boolean isWhiteKing) {
        if(Math.abs(start[1]-end[1])!=2){
            return false;
        }
        ValidationsUtil validationUtils = ValidationsUtilImpl.validationUtils();
        if(validationUtils.isCheckOnKing(start,board.getBoard(),isWhiteKing)){
            return false;
        }
        int direction = end[1] > start[1] ? 1 : -1;
        int limit = direction == 1?2:3;

        boolean isCastlePossible;
        if(direction>0) {
            isCastlePossible = isWhiteKing ? board.isWhiteShortCastlePossible() : board.isBlackShortCastlePossible();
        } else {
            isCastlePossible = isWhiteKing ? board.isWhiteLongCastlePossible() : board.isBlackLongCastlePossible();
        }
        if (!isCastlePossible) {
            return false;
        }

        for (int i = 1; i <= limit; i++) {
            int intermediateCol = start[1] + (direction * i);
            if (!board.getBoard()[start[0]][intermediateCol].equals(PieceNotation.EMPTY_SQUARE.value())||
                    isOpponentKingAround(new int[]{start[0], intermediateCol}, board, isWhiteKing) ||
                    validationUtils.isCheckOnKing(new int[]{start[0], intermediateCol}, board.getBoard(),isWhiteKing)) {
                return false;
            }
        }
        return true;
    }
}
