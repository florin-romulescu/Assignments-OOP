package gameplayelements.cards;

import fileio.CardInput;
import gameplayelements.Board;

public final class Winterfell {
    private Winterfell() { }

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param row the affected row
     */
    public static void useAbility(final Board board, final int row) {
        for (CardInput card: board.getRows().get(row).getCells()) {
            card.setFrozen(true);
        }
    }
}
