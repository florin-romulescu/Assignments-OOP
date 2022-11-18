package gameplayelements.cards;

import fileio.CardInput;
import gameplayelements.Board;
import gameplayelements.Row;

public final class LordRoyce {
    private LordRoyce() { }

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
        CardInput maxAttackCard = null;

        for (CardInput card: row.getCells()) {
            if (maxAttackCard == null) {
                maxAttackCard = card;
            } else if (maxAttackCard.getAttackDamage() < card.getAttackDamage()) {
                maxAttackCard = card;
            }
        }
        maxAttackCard.setFrozen(true);
    }
}
