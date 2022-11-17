package gameplay_elements;

import fileio.CardInput;

import java.util.ArrayList;

public class Player {
    private ArrayList<CardInput> cardsInHand;
    private int index;
    private final int MAX_MANA = 10;
    private int mana = 1;

    private int wins = 0;

    public Player(int index) {
        cardsInHand = new ArrayList<>();
        this.index = index;
    }

    public ArrayList<CardInput> getCardsInHand() {
        return cardsInHand;
    }

    public int getIndex() {
        return index;
    }

    public int getMana() {
        return mana;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMAX_MANA() {
        return MAX_MANA;
    }

    public void incMana(int increment) {
        mana += Math.min(increment, 10);
    }

    public void addCardInHand(ArrayList<CardInput> deck) {
        if (deck.size() == 0)
            return;
        cardsInHand.add(deck.remove(0));
    }

    public ArrayList<CardInput> getEnvironmentCardsInHand() {
        ArrayList<CardInput> cards = new ArrayList<>();

        for (int i = 0; i < cardsInHand.size(); ++i) {
            if (cardsInHand.get(i).isEnvironmentCard())
                cards.add(cardsInHand.get(i));
        }

        return cards;
    }
}
