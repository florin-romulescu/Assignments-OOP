package gameplayelements.cards;

import fileio.CardInput;
import gameplayelements.Board;
import gameplayelements.Row;

public final class GeneralKocioraw {
    private GeneralKocioraw() { }

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
        for (CardInput card: row.getCells()) {
            card.setAttackDamage(
                    card.getAttackDamage() + 1
            );
        }
    }
}