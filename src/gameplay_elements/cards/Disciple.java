package gameplay_elements.cards;

import fileio.CardInput;

public class Disciple {
    private static final int healthBuff = 2;
    private Disciple() {}

    /**
     * Apply the special ability of this card.
     * @param allyCard the card affected.
     */
    public static void useAbility(CardInput allyCard) {
        allyCard.setHealth(
            allyCard.getHealth() + healthBuff
        );
    }
}
