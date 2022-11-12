package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import fileio.output.*;

import java.util.ArrayList;

public class CommandRun {
    private CommandRun() {
    }

    public static ArrayList<CardInput> getPlayerDeck(Input input, int playerIndex, GameInput game) {
        if (playerIndex == 1) {
            return input.getPlayerOneDecks()
                        .getDecks()
                        .get(game.getStartGame().getPlayerOneDeckIdx());
        } else {
            return input.getPlayerTwoDecks()
                    .getDecks()
                    .get(game.getStartGame().getPlayerTwoDeckIdx());
        }
    }

    public static CardInput getPlayerHero(int playerIndex, GameInput game) {
        if (playerIndex == 1) {
            return game.getStartGame().getPlayerOneHero();
        } else {
            return game.getStartGame().getPlayerTwoHero();
        }
    }

    public static int getPlayerTurn(int playerIndex, GameInput game) {
        return game.getCurrentPlayer().getIndex();
    }

    public static String placeCardOnBoard(GameInput game, int cardIndex) {
        Board board = game.getStartGame().getBoard();
        return board.placeCard(game.getCurrentPlayer(), cardIndex);
    }

    public static ArrayList<CardInput> getPlayerCards(GameInput game, int playerIndex) {
        return game.getPlayer(playerIndex).getCardsInHand();
    }

    public static int getPlayerMana(GameInput game, int playerIndex) {
        return game.getPlayer(playerIndex).getMana();
    }

    public static ArrayNode getCardsOnTable(GameInput game) {
        Board board = game.getStartGame().getBoard();
//        ArrayList<CardInput> cards = new ArrayList<>();
//
//        for (int i = 0; i < 4; ++i) {
//            for (CardInput card: board.getRows().get(i).getCells()) {
//                cards.add(card);
//            }
//        }
        return board.convertToArrayNode();
    }

    public static String cardUsesAttack(GameInput game, Coordinates coords1, Coordinates coords2) {
        Board board = game.getStartGame().getBoard();
        CardInput card1 = board.getCard(coords1);
        CardInput card2 = board.getCard(coords2);
        if (card1 == null || card2 == null) {
            return "Todo error: no card found";
        }
        if (card1.isFrozen()) {
            return "Attacker card is frozen.";
        }
        if (card1.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }
        if (board.getCardPlayerIndex(coords1) == board.getCardPlayerIndex(coords2)) {
            return "Attacked card does not belong to the enemy.";
        }
        if (board.enemyHasTank(game.getCurrentPlayer().getIndex()) &&
            !card2.isTankCard()) {
            return "Attacked card is not of type 'Tank'.";
        }

        card1.useAttack(card2);
        return "";
    }

    public static ObjectNode run(Input input, GameInput game, ActionsInput action) {
        Output out;
        switch (action.getCommand()) {
            case "getPlayerDeck":
                out = new ShowPlayerDeckOutput(action.getCommand(), action.getPlayerIdx());
                ((ShowPlayerDeckOutput) out).setOutput(CommandRun.getPlayerDeck(input, action.getPlayerIdx(), game));
                return ((ShowPlayerDeckOutput) out).convertToObjectNode();
            case "getPlayerHero":
                out = new ShowPlayerHeroOutput(action.getCommand(), action.getPlayerIdx());
                ((ShowPlayerHeroOutput) out).setOutput(CommandRun.getPlayerHero(action.getPlayerIdx(), game));
                return ((ShowPlayerHeroOutput) out).convertToObjectNode();
            case "getPlayerTurn":
                out = new ShowPlayerTurnOutput(action.getCommand(), action.getPlayerIdx());
                ((ShowPlayerTurnOutput) out).setOutput(CommandRun.getPlayerTurn(action.getPlayerIdx(), game));
                return ((ShowPlayerTurnOutput) out).convertToObjectNode();
            case "endPlayerTurn":
                if (game.getCurrentPlayer().getIndex() == 1) game.setCurrentPlayer(2);
                else game.setCurrentPlayer(1);
                game.getStartGame().incNumberOfTurns();
                break;
            case "placeCard":
                out = new PlaceError(action.getCommand(), action.getHandIdx());
                ((PlaceError) out).setError(CommandRun.placeCardOnBoard(game, action.getHandIdx()));
                if (!((PlaceError) out).getError().equals("")) {
                    return ((PlaceError) out).convertToObjectNode();
                }
                break;
            case "getCardsInHand":
                out = new ShowPlayerCardsOutput(action.getCommand(), action.getPlayerIdx());
                ((ShowPlayerCardsOutput) out).setOutput(CommandRun.getPlayerCards(game, action.getPlayerIdx()));
                return ((ShowPlayerCardsOutput) out).convertToObjectNode();
            case "getPlayerMana":
                out = new ShowPlayerManaOutput(action.getCommand(), action.getPlayerIdx());
                ((ShowPlayerManaOutput) out).setOutput(CommandRun.getPlayerMana(game, action.getPlayerIdx()));
                return ((ShowPlayerManaOutput) out).convertToObjectNode();
            case "getCardsOnTable":
                out = new ShowCardsOnTableOutput(action.getCommand());
                ((ShowCardsOnTableOutput) out).setOutput(CommandRun.getCardsOnTable(game));
                return ((ShowCardsOnTableOutput) out).convertToObjectNode();
            case "cardUsesAttack":
                out = new AttackCardError(action.getCommand(), action.getCardAttacker(), action.getCardAttacked());
                String msg = CommandRun.cardUsesAttack(game, action.getCardAttacker(), action.getCardAttacked());
                if (msg.equals(""))
                    break;
                ((AttackCardError) out).setError(msg);
                return ((AttackCardError) out).convertToObjectNode();
        }
        return null;
    }

}
