package app.chess.contants;

public enum PieceNotation {
    WHITE_KING("WK"),
    WHITE_QUEEN("WQ"),
    WHITE_KNIGHT("WN"),
    WHITE_BISHOP("WB"),
    WHITE_ROOK("WR"),
    WHITE_PAWN("WP"),
    BLACK_KING("BK"),
    BLACK_QUEEN("BQ"),
    BLACK_KNIGHT("BN"),
    BLACK_BISHOP("BB"),
    BLACK_ROOK("BR"),
    BLACK_PAWN("BP"),
    EMPTY_SQUARE("--");


    private String value;
    private PieceNotation(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }
}
