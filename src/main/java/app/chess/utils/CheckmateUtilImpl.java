package app.chess.utils;

import app.chess.board.Board;
import app.chess.contants.PieceNotation;
import app.chess.piece.PieceFactory;
import app.chess.piece.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class CheckmateUtilImpl implements CheckmateUtil {


    @Override
    public boolean isCheckmate(int[] kingPosition, Board board, boolean isWhiteKing) {
        List<int[]> attackers = getAttackingPieces(kingPosition, board);
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();

        int checkCount = attackers.size();

        if (checkCount == 0) {
            return false;
        }

        if (checkCount == 2 || !isKingCanMoveOrInCheckAfterMoving(kingPosition, board, validationsUtil, isWhiteKing)) {
            return true;
        }

        int[] attackingPiecePosition = attackers.get(0);
        char attackingPieceType = board.getBoard()[attackingPiecePosition[0]][attackingPiecePosition[1]].charAt(1);

        if (attackingPieceType == 'P') {
            return handlePawnCheck(attackingPiecePosition, board, isWhiteKing);
        } else if (attackingPieceType == 'N') {
            return !canBeBlocked(attackingPiecePosition, board, isWhiteKing);
        }

        return !canBlockAttack(attackingPiecePosition, kingPosition, board, isWhiteKing);
    }

    private boolean handlePawnCheck(int[] attackingPiecePosition, Board board, boolean isWhiteKing) {
        String playerPrefix = isWhiteKing ? "W" : "B";
        boolean canBlockLeft = !board.getBoard()[attackingPiecePosition[0]][attackingPiecePosition[1] + 1].equals(playerPrefix + "P");
        boolean canBlockRight = !board.getBoard()[attackingPiecePosition[0]][attackingPiecePosition[1] - 1].equals(playerPrefix + "P");

        // Check for en passant
        int direction = isWhiteKing ? -1 : 1;
        int[] end = {attackingPiecePosition[0] + direction, attackingPiecePosition[1]};
        return !(canBlockLeft && canBlockRight) ||
                Pawn.getInstance().isValidEnPassant(new int[]{attackingPiecePosition[0], attackingPiecePosition[1] + 1}, end, board, isWhiteKing) ||
                Pawn.getInstance().isValidEnPassant(new int[]{attackingPiecePosition[0], attackingPiecePosition[1] - 1}, end, board, isWhiteKing);
    }

    private boolean canBlockAttack(int[] attackingPiecePosition, int[] kingPosition, Board board, boolean isWhiteKing) {
        int xDirection = Integer.signum(attackingPiecePosition[0] - kingPosition[0]);
        int yDirection = Integer.signum(attackingPiecePosition[1] - kingPosition[1]);
        int newRow = kingPosition[0] + xDirection;
        int newCol = kingPosition[1] + yDirection;

        while (newRow <= attackingPiecePosition[0] && newCol <= attackingPiecePosition[1]) {
            if (canBeBlocked(new int[]{newRow, newCol}, board, isWhiteKing)) {
                return true; // If a block is possible
            }
            newRow += xDirection;
            newCol += yDirection;
        }
        return false; // No blocks available
    }


    @Override
    public boolean isStalemate(int[] kingPosition, Board board, boolean isWhiteKing) {
        char playerColor = isWhiteKing?'W':'B';
        for(int i=1;i<9;i++) {
            for(int j=1;j<9;j++) {
                if(board.getBoard()[i][j].charAt(0)==playerColor &&
                        PieceFactory.getPiece(board.getBoard()[i][j].charAt(1)).hasAnyLegalMove(new int[]{i,j},board)){
                        return false;
                    }

            }
        }
        return true;
    }

    private boolean isKingCanMoveOrInCheckAfterMoving(int[] kingPosition, Board board,
                                             ValidationsUtil validationsUtil, boolean isWhiteKing) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        char playerColor = isWhiteKing?'W':'B';
        for (int[] direction : directions) {
            int newRow = kingPosition[0] + direction[0];
            int newCol = kingPosition[1] + direction[1];

            if (validationsUtil.isValidCoordinate(new int[]{newRow, newCol}) &&
                    (board.getBoard()[newRow][newCol].charAt(0)==playerColor||
                            !validationsUtil.isCheckOnKing(new int[]{newRow, newCol}, board.getBoard(), isWhiteKing))) {
                return false;
            }
        }
        return true;
    }

    private List<int[]> getAttackingPieces(int[] king, Board board) {
        List<int[]> attackers = new ArrayList<>();
        ValidationsUtil validationsUtil = ValidationsUtilImpl.validationUtils();

        String opponentPrefix = board.getBoard()[king[0]][king[1]].charAt(0) == 'W' ? "B" : "W";

        attackers.addAll(getSlidingPieceAttackers(king, board, opponentPrefix, validationsUtil));
        attackers.addAll(getKnightAttackers(king, board, opponentPrefix, validationsUtil));
        attackers.addAll(getPawnAttackers(king, board, opponentPrefix, validationsUtil));

        return attackers;
    }

    private List<int[]> getSlidingPieceAttackers(int[] king, Board board, String opponentPrefix, ValidationsUtil validationsUtil) {
        List<int[]> attackers = new ArrayList<>();
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) {
            attackers.addAll(getAttackersInDirection(king, board, opponentPrefix, validationsUtil, direction));
            if (attackers.size() == 2) return attackers;
        }

        return attackers;
    }

    private List<int[]> getAttackersInDirection(int[] king, Board board, String opponentPrefix, ValidationsUtil validationsUtil, int[] direction) {
        List<int[]> attackers = new ArrayList<>();
        for (int step = 1; ; step++) {
            int newRow = king[0] + direction[0] * step;
            int newCol = king[1] + direction[1] * step;

            if (!validationsUtil.isValidCoordinate(new int[]{newRow, newCol})) break;

            String piece = board.getBoard()[newRow][newCol];
            if (!piece.equals(PieceNotation.EMPTY_SQUARE.value())) {
                if (isSlidingPieceAttacker(piece, opponentPrefix, direction)) {
                    attackers.add(new int[]{newRow, newCol});
                }
                break;
            }
        }
        return attackers;
    }

    private boolean isSlidingPieceAttacker(String piece, String opponentPrefix, int[] direction) {
        boolean isDiagonal = Math.abs(direction[0]) == 1 && Math.abs(direction[1]) == 1;
        boolean isStraight = Math.abs(direction[0]) == 0 || Math.abs(direction[1]) == 0;

        return (piece.equals(opponentPrefix + "B") || piece.equals(opponentPrefix + "Q") && isDiagonal) ||
                (piece.equals(opponentPrefix + "R") || piece.equals(opponentPrefix + "Q") && isStraight);
    }


    private List<int[]> getKnightAttackers(int[] king, Board board, String opponentPrefix, ValidationsUtil validationsUtil) {
        List<int[]> attackers = new ArrayList<>();
        int[][] knightDirections = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] direction : knightDirections) {
            int newRow = king[0] + direction[0];
            int newCol = king[1] + direction[1];
            if (validationsUtil.isValidCoordinate(new int[]{newRow, newCol}) &&
                    board.getBoard()[newRow][newCol].equals(opponentPrefix + "N")) {
                attackers.add(new int[]{newRow, newCol});
                break;
            }
        }

        return attackers;
    }

    private List<int[]> getPawnAttackers(int[] king, Board board, String opponentPrefix, ValidationsUtil validationsUtil) {
        List<int[]> attackers = new ArrayList<>();
        int direction = opponentPrefix.equals("W") ? -1 : 1;


        if (validationsUtil.isValidCoordinate(new int[]{king[0] + direction, king[1] + 1}) &&
                board.getBoard()[king[0] + direction][king[1] + 1].equals(opponentPrefix + "P")) {
            attackers.add(new int[]{king[0] + direction, king[1] + 1});
        }


        if (validationsUtil.isValidCoordinate(new int[]{king[0] + direction, king[1] - 1}) &&
                board.getBoard()[king[0] + direction][king[1] - 1].equals(opponentPrefix + "P")) {
            attackers.add(new int[]{king[0] + direction, king[1] - 1});
        }

        return attackers;
    }

    private boolean canBeBlocked(int[] position, Board board,boolean isWhiteKing) {
        String playerPrefix = isWhiteKing?"W":"B";
        List<int[]> attackingPieces = getSlidingPieceAttackers(position,board,playerPrefix,ValidationsUtilImpl.validationUtils());
        if(!attackingPieces.isEmpty()){
            return true;
        }
        List<int[]> knightBlock = getKnightAttackers(position,board,playerPrefix,ValidationsUtilImpl.validationUtils());
        if(!knightBlock.isEmpty()){
            return true;
        }
        return isPawnCanBlock(position,board,isWhiteKing);

    }
    private boolean isPawnCanBlock(int[] position,Board board,boolean isWhiteKing) {
        int direction = isWhiteKing?1:-1;
        String playerPrefix = isWhiteKing?"W":"B";
        int pawnRank = playerPrefix.equals("W")?7:2;
        ValidationsUtilImpl validationsUtil = ValidationsUtilImpl.validationUtils();
        if(!board.getBoard()[position[0]][position[1]].equals(PieceNotation.EMPTY_SQUARE.value())){
            return false;
        }
        if(validationsUtil.isValidCoordinate(new int[]{position[0]+direction,position[1]})&&
        board.getBoard()[position[0]+direction][position[1]].equals(playerPrefix+'P')){
            return true;
        }else return position[0] + direction * 2 == pawnRank &&
                board.getBoard()[position[0] + direction][position[1]].equals(PieceNotation.EMPTY_SQUARE.value())&&
                board.getBoard()[position[0] + direction * 2][position[1]].equals(playerPrefix + 'P');
    }



}
