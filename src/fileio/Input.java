package fileio;

import java.util.ArrayList;

public final class Input {
    private DecksInput playerOneDecks;
    private DecksInput playerTwoDecks;
    private ArrayList<GameInput> games;

    private int totalGames = 0;
    private int playerOneWins = 0;
    private int playerTowWins = 0;

    public Input() {
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void incTotalGames() {
        this.totalGames += 1;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void incPlayerOneWins() {
        this.playerOneWins += 1;
    }

    public int getPlayerTowWins() {
        return playerTowWins;
    }

    public void incPlayerTowWins() {
        this.playerTowWins += 1;
    }

    public ArrayList<GameInput> getGames() {
        return games;
    }

    public void setGames(final ArrayList<GameInput> games) {
        this.games = games;
    }

    public DecksInput getPlayerOneDecks() {
        return playerOneDecks;
    }

    public void setPlayerOneDecks(final DecksInput playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    public DecksInput getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    public void setPlayerTwoDecks(final DecksInput playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    @Override
    public String toString() {
        return "Input{"
                + "player_one_decks="
                + playerOneDecks
                + ", player_two_decks="
                + playerTwoDecks
                +  ", games="
                + games
                +  '}';
    }
}
