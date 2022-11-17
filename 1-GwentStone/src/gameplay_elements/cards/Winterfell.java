package gameplay_elements.cards;

import fileio.CardInput;
import gameplay_elements.Board;

public class Winterfell {
    private Winterfell() {}

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param row the affected row
     */
    public static void useAbility(Board board, int row) {
        for (CardInput card: board.getRows().get(row).getCells()) {
            card.setFrozen(true);
        }
    }
}
