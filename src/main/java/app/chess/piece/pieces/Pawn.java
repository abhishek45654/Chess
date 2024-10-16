package app.chess.piece.pieces;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;
import app.chess.piece.Piece;
import app.chess.utils.ValidationsUtil;
import app.chess.utils.ValidationsUtilImpl;

public class Pawn implements Piece {

    private static Pawn instance;

    private Pawn() {

    }

    @Override
    public boolean isValidMove(int[] start, int[] end, Board board) {
        boolean isWhite = board.getBoard()[start[0]][start[1]].charAt(0) == 'W';
        int direction = isWhite ? -1 : 1;

        if (end[0] == start[0] + direction && end[1] == start[1]) {
            return board.getBoard()[end[0]][end[1]].equals(PieceNotation.EMPTY_SQUARE.value());
        }

        if (start[0] == (isWhite ? 7 : 2) && end[0] == start[0] + 2 * direction && end[1] == start[1]) {
            return board.getBoard()[end[0]][end[1]] .equals(PieceNotation.EMPTY_SQUARE.value()) &&
                    board.getBoard()[start[0] + direction][start[1]].equals(PieceNotation.EMPTY_SQUARE.value()); // Ensure both squares are empty
        }

        if (end[0] == start[0] + direction && (end[1] == start[1] + 1 || end[1] == start[1] - 1)) {
            return !board.getBoard()[end[0]][end[1]].equals(PieceNotation.EMPTY_SQUARE.value());
        }
        return isValidEnPassant(start,end,board,isWhite);

    }

    @Override
    public void movePieceOnBoard(int[] start, int[] end, Board board) {
        board.move(start, end);
        if (isPawnPromotion(end)) {
            promotePawn(end, board);
        }
        board.setPreviousMove(new String[]{
                Mappings.getChessNotation(start[0], start[1]),
                Mappings.getChessNotation(end[0], end[1])});
    }

    @Override
    public boolean hasAnyLegalMove(int[] position, Board board) {
        int direction = board.getBoard()[position[0]][position[1]].charAt(0) == 'W' ? 1 : -1;
        char opponentColor = direction == 1 ? 'B' : 'W';
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();

        if (validationsUtil.isValidCoordinate(new int[]{position[0] + direction, position[1]}) &&
                board.getBoard()[position[0] + direction][position[1]].equals(PieceNotation.EMPTY_SQUARE.value())) {
            return true;
        }

        if (validationsUtil.isValidCoordinate(new int[]{position[0] + direction, position[1] + 1}) &&
                board.getBoard()[position[0] + direction][position[1] + 1].charAt(0) == opponentColor) {
            return true;
        }

        return validationsUtil.isValidCoordinate(new int[]{position[0] + direction, position[1] - 1}) &&
                board.getBoard()[position[0] + direction][position[1] - 1].charAt(0) == opponentColor;
    }


    private boolean isPawnPromotion(int[] end) {
        return end[0] == 1 || end[0] == 8;
    }

    private void promotePawn(int[] end, Board board) {
        board.getBoard()[end[0]][end[1]] = board.getBoard()[end[0]][end[1]].charAt(0) + "Q";
    }

    public boolean isValidEnPassant(int[] start, int[] end, Board board, boolean isWhite) {
        int[] preStart = Mappings.getCoordinatesRef(board.getPreviousMove()[0]);
        int[] preEnd = Mappings.getCoordinatesRef(board.getPreviousMove()[1]);
        if(board.getBoard()[preEnd[0]][preEnd[1]].charAt(1)!='P'){
            return false;
        }
        boolean isEnPassantRow = end[0] == (isWhite ? 6 : 3);
        boolean isAdjacentColumn = (end[1] == start[1] + 1 || end[1] == start[1] - 1);

        boolean wasTwoSquareAdvance = (preEnd[0] == (isWhite ? 4 : 5) && preStart[1] == preEnd[1] && Math.abs(preEnd[0] - preStart[0]) == 2);

        if (isEnPassantRow && isAdjacentColumn && wasTwoSquareAdvance) {
            int adjacentCol = (end[1] == start[1] + 1) ? end[1] - 1 : end[1] + 1;
            return !board.getBoard()[end[0]][adjacentCol].equals(PieceNotation.EMPTY_SQUARE.value());
        }

        return false;
    }

    public static Pawn getInstance() {
        if(instance == null) {
            instance = new Pawn();
        }
        return instance;
    }

}
