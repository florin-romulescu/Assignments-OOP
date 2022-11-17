package gameplay_elements.cards;

import fileio.CardInput;

public class Miraj {
    private Miraj() {}

    /**
     * Apply the special ability of this card.
     * @param allyCard the ally card affected
     * @param enemyCard the enemy card affected
     */
    public static void useAbility(CardInput allyCard, CardInput enemyCard) {
        int allyHealth = allyCard.getHealth();
        allyCard.setHealth(enemyCard.getHealth());
        enemyCard.setHealth(allyHealth);
    }
}
