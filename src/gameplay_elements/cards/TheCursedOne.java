package gameplay_elements.cards;

import fileio.CardInput;

public class TheCursedOne {
    private TheCursedOne() {}

    /**
     * Apply the special ability of this card.
     * @param allyCard the ally card affected
     * @param enemyCard the enemy card affected
     */
    public static void useAbility(CardInput allyCard, CardInput enemyCard) {
        int enemyHealth = enemyCard.getHealth();
        enemyCard.setHealth(enemyCard.getAttackDamage());
        enemyCard.setAttackDamage(enemyHealth);
    }
}
