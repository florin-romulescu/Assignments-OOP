package gameplayelements.cards;

import fileio.CardInput;

public final class Disciple {
    private Disciple() { }

    /**
     * Apply the special ability of this card.
     * @param allyCard the card affected.
     */
    public static void useAbility(final CardInput allyCard) {
        allyCard.setHealth(
            allyCard.getHealth() + 2
        );
    }
}
