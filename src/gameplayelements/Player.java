package gameplayelements;

import fileio.CardInput;

import java.util.ArrayList;

public class Player {
    private final ArrayList<CardInput> cardsInHand;
    private final int index;
    private final int maxMana = 10;
    private int mana = 1;

    private int wins = 0;

    public Player(final int index) {
        cardsInHand = new ArrayList<>();
        this.index = index;
    }

    /**
     * Getter for cardsInHand field.
     * @return reference to cardsInHand
     */
    public ArrayList<CardInput> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * Getter for index field.
     * @return index field value
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for mana field.
     * @return value of the mana field
     */
    public int getMana() {
        return mana;
    }

    /**
     * Setter for mana field.
     * @param mana the new mana value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Increase the current mana.
     * @param increment the increment for the mana
     */
    public void incMana(final int increment) {
        mana += Math.min(increment, maxMana);
    }

    /**
     * Add a card in cardsInHand field from an outer deck.
     * The card will be removed from the previous deck.
     * @param deck
     */
    public void addCardInHand(final ArrayList<CardInput> deck) {
        if (deck.size() == 0) {
            return;
        }
        cardsInHand.add(deck.remove(0));
    }

    /**
     * Get all cards in hand that are environment.
     * @return an ArrayList of cards
     */
    public ArrayList<CardInput> getEnvironmentCardsInHand() {
        ArrayList<CardInput> cards = new ArrayList<>();

        for (int i = 0; i < cardsInHand.size(); ++i) {
            if (cardsInHand.get(i).isEnvironmentCard()) {
                cards.add(cardsInHand.get(i));
            }
        }

        return cards;
    }
}
