package gameplay_elements;

import fileio.CardInput;

import java.util.ArrayList;

public class Player {
    private ArrayList<CardInput> cardsInHand;
    private int index;
    private final int MAX_MANA = 10;
    private int mana = 1;

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
}
