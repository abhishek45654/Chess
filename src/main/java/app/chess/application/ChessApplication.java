package app.chess.application;

import app.chess.board.Board;
import app.chess.config.Mappings;
import app.chess.piece.PieceFactory;
import app.chess.utils.CheckmateUtil;
import app.chess.utils.CheckmateUtilImpl;
import app.chess.validation.Validation;
import app.chess.validation.ValidationImpl;

import java.util.Scanner;

public class ChessApplication {

    public void run() {
        Board board = new Board();
        Validation validation = ValidationImpl.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean whiteTurn = true;
        board.printPOVWhite();

        while (true) {
            String input = getUserInput(scanner, whiteTurn);

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Game Ended");
                break;
            }

            String[] moves = input.split(" ");
            if (!isValidInput(moves)) {
                System.out.println("Please enter both start and end moves.");
                continue;
            }

            int[] start = Mappings.getCoordinatesRef(moves[0]);
            int[] end = Mappings.getCoordinatesRef(moves[1]);

            if (validation.isValidMove(start, end, board, whiteTurn)) {
                makeMove(start, end, board);
                if (checkGameState(board, whiteTurn)) {
                    break;
                }
                whiteTurn = !whiteTurn;
                printBoard(whiteTurn, board);
            } else {
                System.out.println("Invalid Move");
            }
        }
        scanner.close();
    }

    private String getUserInput(Scanner scanner,boolean isWhiteTurn) {
        String color = isWhiteTurn?"White":"Black";
        System.out.println("Enter moves (or 'exit' to quit): ");
        System.out.println(color + " turn: ");
        return scanner.nextLine();
    }

    private boolean isValidInput(String[] moves) {
        return moves.length >= 2;
    }

    private void makeMove(int[] start, int[] end, Board board) {
        PieceFactory.getPiece(board.getBoard()[start[0]][start[1]].charAt(1))
                .movePieceOnBoard(start, end, board);
    }

    private boolean checkGameState(Board board, boolean whiteTurn) {
        CheckmateUtil checkmateUtil = new CheckmateUtilImpl();
        boolean isCheckmate = checkmateUtil.isCheckmate(
                whiteTurn ? board.getBlackKingPosition() : board.getWhiteKingPosition(), board, !whiteTurn);

        if (isCheckmate) {
            System.out.println(!whiteTurn ? "Black wins" : "White wins");
            return true;
        }

        boolean isStalemate = checkmateUtil.isStalemate(
                whiteTurn ? board.getBlackKingPosition() : board.getWhiteKingPosition(), board, !whiteTurn);

        if (isStalemate) {
            System.out.println("Draw by stalemate");
            return true;
        }

        return false;
    }

    private void printBoard(boolean whiteTurn, Board board) {
        if (whiteTurn) {
            board.printPOVWhite();
        } else {
            board.printPOVBlack();
        }
    }

}