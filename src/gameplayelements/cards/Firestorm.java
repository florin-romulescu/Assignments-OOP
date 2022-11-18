package gameplayelements.cards;

import fileio.CardInput;
import gameplayelements.Board;

public final class Firestorm {
    private Firestorm() { }

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param row the affected row
     */
    public static void useAbility(final Board board, final int row) {
        for (CardInput card: board.getRows().get(row).getCells()) {
            card.setHealth(
                    card.getHealth() - 1
            );
        }
    }
}
