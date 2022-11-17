package gameplay_elements.cards;

import fileio.CardInput;
import gameplay_elements.Board;
import gameplay_elements.Row;

public class GeneralKocioraw {
    private static final int damageBuff = 1;
    private GeneralKocioraw() {}

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param rowIndex the affected row
     */
    public static void useAbility(Board board, int rowIndex) {
        Row row = board.getRows().get(rowIndex);
        if (row.getCells().size() == 0)
            return;
        for (CardInput card: row.getCells()) {
            card.setAttackDamage(
                    card.getAttackDamage() + damageBuff
            );
        }
    }
}
