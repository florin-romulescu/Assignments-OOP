package gameplayelements.cards;

import fileio.CardInput;

public final class TheRipper {
    private TheRipper() { }

    /**
     * Apply the special ability of this card.
     * @param enemyCard the card affected
     */
    public static void useAbility(final CardInput enemyCard) {
        enemyCard.setAttackDamage(
                Math.max(0, enemyCard.getAttackDamage() - 2)
        );
    }
}
