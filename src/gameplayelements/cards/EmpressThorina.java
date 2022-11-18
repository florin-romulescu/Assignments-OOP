package gameplayelements.cards;

import fileio.CardInput;
import gameplayelements.Board;
import gameplayelements.Row;

public final class EmpressThorina {
    private EmpressThorina() { }

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param rowIndex the affected row
     */
    public static void useAbility(final Board board, final int rowIndex) {
        Row row = board.getRows().get(rowIndex);
        if (row.getCells().size() == 0) {
            return;
        }
        CardInput maxHealthCard = null;

        for (CardInput card: row.getCells()) {
            if (maxHealthCard == null) {
                maxHealthCard = card;
            } else if (maxHealthCard.getHealth() < card.getHealth()) {
                maxHealthCard = card;
            }
        }
        maxHealthCard.setHealth(0);
    }
}
