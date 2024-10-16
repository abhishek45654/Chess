package app.chess.piece;

import app.chess.piece.pieces.*;

public class PieceFactory {

    private PieceFactory() {

    }

    public static Piece getPiece(char pieceChar) {
        switch (pieceChar) {
            case 'P':
                return Pawn.getInstance();
            case 'R':
                return Rook.getInstance();
            case 'N':
                return Knight.getInstance();
            case 'B':
                return Bishop.getInstance();
            case 'K':
                return King.getInstance();
            case 'Q':
                return Queen.getInstance();
            default:
                return NoPiece.getInstance();
        }
    }
}
