package gameplayelements.cards;

import fileio.CardInput;

public final class Miraj {
    private Miraj() { }

    /**
     * Apply the special ability of this card.
     * @param allyCard the ally card affected
     * @param enemyCard the enemy card affected
     */
    public static void useAbility(final CardInput allyCard, final CardInput enemyCard) {
        int allyHealth = allyCard.getHealth();
        allyCard.setHealth(enemyCard.getHealth());
        enemyCard.setHealth(allyHealth);
    }
}
