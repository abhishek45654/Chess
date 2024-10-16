package app.chess.config;

import java.util.Arrays;

public class Mappings {

    private Mappings() {
    }


    public static int[] getCoordinatesRef(String position) {
        if ('a' <= position.charAt(0) && position.charAt(0) <= 'h' &&
                position.charAt(1) <= '8' && position.charAt(1) >= '1') {
            return new int[]{9 - (position.charAt(1) - '0'), position.charAt(0) - 'a'+1};
        } else {
            return new int[]{(position.charAt(1) - '0'), position.charAt(0) - 'a'};
        }

    }

    public static int[] getPOVBCoordinatesRef(String position) {
        if ('a' <= position.charAt(0) && position.charAt(0) <= 'h' &&
                position.charAt(1) <= '8' && position.charAt(1) >= '1') {
            return new int[]{(position.charAt(1) - '0'), 8 - (position.charAt(0) - 'a')};
        } else {
            throw new IllegalArgumentException("Invalid move coordinates");
        }

    }

    public static String getChessNotation(int row, int col) {
        return ((char) ('a' + (col - 1))) + "" + (char) ('0' + (9 - row));
    }
}
