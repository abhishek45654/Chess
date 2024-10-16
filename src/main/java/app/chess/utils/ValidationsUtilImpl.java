package app.chess.utils;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.contants.PieceNotation;

public class ValidationsUtilImpl implements ValidationsUtil{

    private static ValidationsUtilImpl instance;

    private ValidationsUtilImpl() {

    }

    @Override
    public boolean isValidCoordinate(int[] coordinate) {
        return coordinate[0]>=1&&coordinate[0]<=8&&coordinate[1]>=1&&coordinate[1]<=8;
    }



    @Override
    public boolean isValidNotation(String start) {
        return this.isValidCoordinate(Mappings.getCoordinatesRef(start));
    }

    @Override
    public boolean isCheckOnKing(int[] kingPosition, String[][] board, boolean isWhiteKing) {
        // Check for pawn attacks
        if (isPawnCheck(kingPosition, board, isWhiteKing)) {
            return true;
        }

        // Check for diagonal attacks (Bishops and Queens)
        if (isCheckOnDiagonal(kingPosition, board, isWhiteKing)) {
            return true;
        }

        // Check for horizontal and vertical attacks (Rooks and Queens)
        if (isStraightCheck(kingPosition, board, isWhiteKing)) {
            return true;
        }

        // Check for knight attacks
        return isKnightAttack(kingPosition, board, isWhiteKing);
    }

    @Override
    public boolean isSameEnd(int[] start, int[] end) {
        return start[0]==end[0]&&start[1]==end[1];
    }



    @Override
    public boolean isValidPlayerTurn(int[] start, Board board, boolean isWhiteTurn) {
        char color = isWhiteTurn? 'W':'B';
        return board.getBoard()[start[0]][start[1]].charAt(0)==color;
    }

    @Override
    public boolean isSameColorPieceAtEnd(int[] start, int[] end, Board board) {
        return board.getBoard()[start[0]][start[1]].charAt(0)==board.getBoard()[end[0]][end[1]].charAt(0);
    }

    @Override
    public boolean isEmptySquare(int[] start, Board board) {
        return board.getBoard()[start[0]][start[1]].equals(PieceNotation.EMPTY_SQUARE.value());
    }


    private boolean isPawnCheck(int[] kingPosition, String[][] board,boolean isWhiteKing) {
        int direction = isWhiteKing ? -1 : 1;
        String opponentPrefix = isWhiteKing?"B":"W";
        return (this.isValidCoordinate(new int[]{kingPosition[0] + direction, kingPosition[1] - 1}) &&
                board[kingPosition[0] + direction][kingPosition[1] - 1].equals(opponentPrefix+"P")) ||
                (this.isValidCoordinate(new int[]{kingPosition[0] + direction, kingPosition[1] + 1}) &&
                        board[kingPosition[0] + direction][kingPosition[1] + 1].equals(opponentPrefix+"P"));
    }

    private boolean isCheckOnDiagonal(int[] kingPosition, String[][] board,boolean isWhiteKing) {
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        String opponentPrefix = isWhiteKing?"B":"W";
        String player = isWhiteKing?"W":"B";
        for (int[] direction : directions) {
            int newRow = kingPosition[0]+direction[0];
            int newCol = kingPosition[1]+direction[1];

            while (this.isValidCoordinate(new int[]{newRow, newCol})) {

                String piece = board[newRow][newCol];
                if (piece.equals(opponentPrefix + "B") || piece.equals(opponentPrefix + "Q")) {
                    return true;
                }
                if (!piece.equals("--")&&!piece.equals(player+"K")) {
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];
            }
        }
        return false;
    }
    private boolean isStraightCheck(int[] kingPosition, String[][] board, boolean isWhiteKing) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        String opponentPrefix = isWhiteKing?"B":"W";
        String player = isWhiteKing?"W":"B";

        for (int[] direction : directions) {
            int newRow = kingPosition[0]+direction[0];
            int newCol = kingPosition[1]+direction[1];

            while (this.isValidCoordinate(new int[]{newRow,newCol})) {

                String piece = board[newRow][newCol];
                if (piece.equals(opponentPrefix + "R") || piece.equals(opponentPrefix + "Q")) {
                    return true;
                }
                if (!piece.equals("--")&&!piece.equals(player+"K")){
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];
            }
        }
        return false;
    }

    private boolean isKnightAttack(int[] kingPosition, String[][] board, boolean isWhiteKing) {
        String opponentPrefix = isWhiteKing ? "B" : "W";

        int[][] knightMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) {
            int newRow = kingPosition[0] + move[0];
            int newCol = kingPosition[1] + move[1];
            if (this.isValidCoordinate(new int[]{newRow, newCol}) && board[newRow][newCol].equals(opponentPrefix + "N")) {
                return true;
            }
        }
        return false;
    }
    public static ValidationsUtilImpl validationUtils(){
        if(instance==null){
            instance = new ValidationsUtilImpl();
        }
        return instance;
    }
}
