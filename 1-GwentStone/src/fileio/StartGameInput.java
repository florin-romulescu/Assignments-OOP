package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    Player player1 = new Player(1);
    Player player2 = new Player(2);
    private int numberOfRounds = 1;
    private Board board = new Board();

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
    /** Return the current player.*/
//    public Player getCurrentPlayer() {
//        if (startingPlayer == 1) {
//            if (numberOfTurns == 0)
//                return player1;
//            else
//                return player2;
//        } else {
//            if (numberOfTurns == 0)
//                return player2;
//            else
//                return player1;
//        }
//    }

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

    public void start(Input input, ArrayNode output, GameInput game) {
        int numberOfCommands = game.getActions().size();
        /* Shuffle decks*/
        Collections.shuffle(
                input.getPlayerOneDecks().getDecks().get(
                        game.getStartGame().getPlayerOneDeckIdx()
                ),
                new Random(game.getStartGame().getShuffleSeed())
        );

        Collections.shuffle(
                input.getPlayerTwoDecks().getDecks().get(
                        game.getStartGame().getPlayerTwoDeckIdx()
                ),
                new Random(game.getStartGame().getShuffleSeed())
        );
        /* Initializing Heroes */
        setPlayerOneHero(new HeroCard(getPlayerOneHero()));
        setPlayerTwoHero(new HeroCard(getPlayerTwoHero()));
        /* Adding starting cards */
        player1.addCardInHand(
                input.getPlayerOneDecks().getDecks().get(
                        game.getStartGame().getPlayerOneDeckIdx()
                )
        );

        player2.addCardInHand(input.getPlayerTwoDecks().getDecks().get(
                game.getStartGame().getPlayerTwoDeckIdx()
        ));
        /* Setting the current player */
        game.setCurrentPlayer(startingPlayer);
        ObjectNode out;
        /* Iterating through all commands */
        for (int commandIndex = 0; commandIndex < numberOfCommands; ++commandIndex) {
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
                /* Adding new cards in hand */
                player1.addCardInHand(
                        input.getPlayerOneDecks().getDecks().get(
                                game.getStartGame().getPlayerOneDeckIdx()
                        )
                );

                player2.addCardInHand(input.getPlayerTwoDecks().getDecks().get(
                        game.getStartGame().getPlayerTwoDeckIdx()
                ));
            }
        }
    }
}
