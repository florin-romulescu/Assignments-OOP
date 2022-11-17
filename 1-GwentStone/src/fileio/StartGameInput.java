package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.output.GameEndedOutput;
import gameplay_elements.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class StartGameInput {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
    private CardInput playerOneHero;
    private CardInput playerTwoHero;
    private int startingPlayer;
    private ArrayList<CardInput> deck1;
    private ArrayList<CardInput> deck2;

    Player player1;
    Player player2;
    private int numberOfRounds = 1;
    private Board board;

    private int numberOfTurns = 0;

    public StartGameInput() {
    }

    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    public void setPlayerOneDeckIdx(final int playerOneDeckIdx) {
        this.playerOneDeckIdx = playerOneDeckIdx;
    }

    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    public void setPlayerTwoDeckIdx(final int playerTwoDeckIdx) {
        this.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public CardInput getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(final CardInput playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public CardInput getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(final CardInput playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incNumberOfTurns() {
        this.numberOfTurns += 1;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<CardInput> getDeck1() {
        return deck1;
    }

    public void setDeck1(ArrayList<CardInput> deck1) {
        this.deck1 = deck1;
    }

    public ArrayList<CardInput> getDeck2() {
        return deck2;
    }

    public void setDeck2(ArrayList<CardInput> deck2) {
        this.deck2 = deck2;
    }

    @Override
    public String toString() {
        return "StartGameInput{"
                + "playerOneDeckIdx="
                + playerOneDeckIdx
                + ", playerTwoDeckIdx="
                + playerTwoDeckIdx
                + ", shuffleSeed="
                + shuffleSeed
                +  ", playerOneHero="
                + playerOneHero
                + ", playerTwoHero="
                + playerTwoHero
                + ", startingPlayer="
                + startingPlayer
                + '}';
    }

    /**
     * Main loop for a game. It iterates through all commands
     * and append to the output parameter the return of Command.run() method.
     * @param input the data of all games
     * @param output the output stream
     * @param game the current game
     */
    public void start(Input input, ArrayNode output, GameInput game) {
        System.out.println(game.getActions().get(0));
        int numberOfCommands = game.getActions().size();
        board = new Board();
        player1 = new Player(1);
        player2 = new Player(2);
        deck1 = new ArrayList<>(input.getPlayerOneDecks().getDecks().get(playerOneDeckIdx));
        deck2 = new ArrayList<>(input.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx));
        /* Shuffle decks*/
        Collections.shuffle(
                deck1,
                new Random(game.getStartGame().getShuffleSeed())
        );

        Collections.shuffle(
                deck2,
                new Random(game.getStartGame().getShuffleSeed())
        );
        /* Initializing Heroes */
        setPlayerOneHero(new HeroCard(getPlayerOneHero()));
        setPlayerTwoHero(new HeroCard(getPlayerTwoHero()));
        /* Adding starting cards */
        player1.addCardInHand(
                deck1
        );

        player2.addCardInHand(
                deck2
        );
        HeroCard hero1 = (HeroCard) getPlayerOneHero();
        HeroCard hero2 = (HeroCard) getPlayerTwoHero();
        /* Setting the current player */
        game.setCurrentPlayer(startingPlayer);
        ObjectNode out;
        /* Iterating through all commands */
        for (int commandIndex = 0; commandIndex < numberOfCommands; ++commandIndex) {
            if (hero1.getHealth() <= 0 && !game.isGameEnded()) {
                output.add(new GameEndedOutput(2).convertToObjectNode());
                game.setGameEnded(true);
                input.incTotalGames();
                input.incPlayerTowWins();
            } else if (hero2.getHealth() <= 0 && !game.isGameEnded()) {
                output.add(new GameEndedOutput(1).convertToObjectNode());
                game.setGameEnded(true);
                input.incTotalGames();
                input.incPlayerOneWins();
            }
            ActionsInput action = game.getActions().get(commandIndex);
            out = CommandRun.run(input, game, action);
            if (out != null) {
                output.add(out);
            }
            /* Starting a new round */
            if (getNumberOfTurns() == 2) {
                numberOfTurns = 0;
                numberOfRounds += 1;
                player1.incMana(numberOfRounds);
                player2.incMana(numberOfRounds);
                board.initialiseNewRound();
                hero1.setHasAttacked(false);
                hero2.setHasAttacked(false);
                /* Adding new cards in hand */
                player1.addCardInHand(
                        deck1
                );

                player2.addCardInHand(
                        deck2
                );
            }
            board.removeEliminatedCards();
        }
    }
}
