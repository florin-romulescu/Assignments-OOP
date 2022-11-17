package gameplay_elements.cards;

import fileio.CardInput;

public class TheRipper {
    private final static int damageNerf = 2;
    private TheRipper() {}

    /**
     * Apply the special ability of this card.
     * @param enemyCard the card affected
     */
    public static void useAbility(CardInput enemyCard) {
        enemyCard.setAttackDamage(
                Math.max(0, enemyCard.getAttackDamage() - damageNerf)
        );
    }
}
