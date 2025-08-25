package uglytrivia;

import uglytrivia.enums.Categories;

public class Board {
    private static final int BOARD_SIZE = Categories.values().length * 3;
    private final Categories[] categories = Categories.values();

    public int calculateNewPosition(int currentPosition, int roll) {
        int newPosition = currentPosition + roll;
        return newPosition % BOARD_SIZE;
    }

    public Categories getCategoryForPosition(int position) {
        return categories[position % categories.length];
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}
