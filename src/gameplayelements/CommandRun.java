package gameplayelements;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import fileio.output.Output;
import fileio.output.AttackCardError;
import fileio.output.AttackHeroError;
import fileio.output.GetCardError;
import fileio.output.GetCardOutput;
import fileio.output.PlaceError;
import fileio.output.ShowCardsOnTableOutput;
import fileio.output.ShowPlayerCardsOutput;
import fileio.output.ShowPlayerDeckOutput;
import fileio.output.ShowPlayerHeroOutput;
import fileio.output.ShowPlayerManaOutput;
import fileio.output.ShowPlayerTurnOutput;
import fileio.output.TotalGamesOutput;
import fileio.output.UseEnvironmentCardError;
import fileio.output.UseHeroAbilityError;

import java.util.ArrayList;


public final class CommandRun {
    private CommandRun() {
    }

    /**
     * Get a player's deck
     * @param playerIndex the corresponding player
     * @param game the current game
     * @return an Array of cards corresponding to the given player's deck.
     */
    public static ArrayList<CardInput> getPlayerDeck(final int playerIndex,
                                                     final GameInput game) {
        if (playerIndex == 1) {
            return game.getStartGame().getDeck1();
        } else {
            return game.getStartGame().getDeck2();
        }
    }

    /**
     * Get the player's Hero.
     * @param playerIndex the corresponding player
     * @param game the current game
     * @return the given player's Hero
     */
    public static CardInput getPlayerHero(final int playerIndex,
                                          final GameInput game) {
        if (playerIndex == 1) {
            return game.getStartGame().getPlayerOneHero();
        } else {
            return game.getStartGame().getPlayerTwoHero();
        }
    }

    /**
     * Get the current player's index
     * @param game the current game
     * @return the current player's index
     */
    public static int getPlayerTurn(final GameInput game) {
        return game.getCurrentPlayer().getIndex();
    }

    /**
     * Place a card on the board. The card will be deleted from the player's hand
     * and the corresponding mana will be subtracted.
     * @param game the current game
     * @param cardIndex the card's in hand index
     * @return an error String or empty String if no error
     */
    public static String placeCardOnBoard(final GameInput game,
                                          final int cardIndex) {
        Board board = game.getStartGame().getBoard();
        return board.placeCard(game.getCurrentPlayer(), cardIndex);
    }

    /**
     * Get the player cards in hand.
     * @param game the current game
     * @param playerIndex the corresponding player
     * @return an array corresponding to the player cards in hand
     */
    public static ArrayList<CardInput> getPlayerCards(final GameInput game,
                                                      final int playerIndex) {
        return game.getPlayer(playerIndex).getCardsInHand();
    }

    /**
     * Get the mana of a player.
     * @param game the current game
     * @param playerIndex the corresponding player
     * @return an integer corresponding to the given player's mana
     */
    public static int getPlayerMana(final GameInput game,
                                    final int playerIndex) {
        return game.getPlayer(playerIndex).getMana();
    }

    /**
     * This function displays all the card on the board as an Array Node.
     * @param game the current game
     * @return the cards on the tables as an Array Node
     */
    public static ArrayNode getCardsOnTable(final GameInput game) {
        Board board = game.getStartGame().getBoard();
        return board.convertToArrayNode();
    }

    /**
     * Card with coords1 attacks the card with coords2.
     * @param game the current game
     * @param coords1 coordinates of the attacker card
     * @param coords2 coordinates of the attacked card
     * @return error String or empty String if no error
     */
    public static String cardUsesAttack(final GameInput game,
                                        final Coordinates coords1,
                                        final Coordinates coords2) {
        Board board = game.getStartGame().getBoard();
        CardInput card1 = board.getCard(coords1);
        CardInput card2 = board.getCard(coords2);
        if (card1 == null || card2 == null) {
            return "Todo error: no card found";
        }
        if (board.getCardPlayerIndex(coords1) == board.getCardPlayerIndex(coords2)) {
            return "Attacked card does not belong to the enemy.";
        }
        if (card1.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }
        if (card1.isFrozen()) {
            return "Attacker card is frozen.";
        }
        if (board.enemyHasTank(game.getCurrentPlayer().getIndex())
            && !card2.isTankCard()) {
            return "Attacked card is not of type 'Tank'.";
        }

        card1.useAttack(card2);
        return "";
    }

    /**
     * Get the card on the table with the given coordinates.
     * @param game the current game
     * @param x coordinate
     * @param y coordinate
     * @return card with coordinates x and y
     */
    public static CardInput getCardAtPosition(final GameInput game,
                                              final int x,
                                              final int y) {
        Board board = game.getStartGame().getBoard();
        Coordinates coords = new Coordinates(x, y);
        return board.getCard(coords);
    }

    /**
     * Get the player's cards.
     * @param game the current game
     * @param playerIndex the corresponding player
     * @return an array of cards from the given player's hands
     */
    public static ArrayList<CardInput> getEnvironmentCardsInHand(final GameInput game,
                                                                 final int playerIndex) {
        return game.getPlayer(playerIndex).getEnvironmentCardsInHand();
    }

    /**
     * Get frozen cards from the board.
     * @param game the current game
     * @return an array of cards from the table that are frozen
     */
    public static ArrayNode getFrozenCardsOnTable(final GameInput game) {
        Board board = game.getStartGame().getBoard();
        return board.convertToArrayNodeIfFrozen();
    }

    /**
     * Applies environment card effect on an enemy row.
     * @param game the current game
     * @param cardIndex the hand index
     * @param rowIndex the row affected by the card
     * @return error String or empty String if no error
     */
    public static String useEnvironmentCard(final GameInput game,
                                            final int cardIndex,
                                            final int rowIndex) {
        Player player = game.getCurrentPlayer();
        Board board = game.getStartGame().getBoard();
        CardInput card = player.getCardsInHand().get(cardIndex);

        String msg = card.useEnvironmentAbility(board, rowIndex, player);
        if (msg.equals("")) {
            player.getCardsInHand().remove(cardIndex);
        }
        return msg;
    }

    /**
     * Applies cards ability on an enemy card.
     * @param game the current game
     * @param coords1 coordinates of the attacker card
     * @param coords2 coordinates of the attacked card
     * @return error String or empty String if no error
     */
    public static String cardUsesAbility(final GameInput game,
                                         final Coordinates coords1,
                                         final Coordinates coords2) {
        Board board = game.getStartGame().getBoard();
        CardInput card = board.getCard(coords1);

        if (card == null) {
            return "";
        }

        return card.useCardAbility(board, coords2, game.getCurrentPlayer());
    }

    /**
     * Card attacks the enemy hero.
     * @param game the current game
     * @param coords1 coordinates of the attacker card
     * @return error String or empty String if no error
     */
    public static String useAttackHero(final GameInput game,
                                       final Coordinates coords1) {
        Board board = game.getStartGame().getBoard();
        CardInput card = board.getCard(coords1);
        if (card == null) {
            return "ERROR: card is null.";
        }
        if (card.isFrozen()) {
            return "Attacker card is frozen.";
        }
        if (card.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }
        if (board.enemyHasTank(game.getCurrentPlayer().getIndex())) {
            return "Attacked card is not of type 'Tank'.";
        }
        HeroCard hero;
        if (game.getCurrentPlayer().getIndex() == 1) {
            hero = (HeroCard) game.getStartGame().getPlayerTwoHero();
        } else {
            hero = (HeroCard) game.getStartGame().getPlayerOneHero();
        }
        card.useAttack(hero);
        return "";
    }

    /**
     * Use the hero ability of the current player. The corresponding mana
     * will be subtracted.
     * @param game the current game
     * @param rowIndex the enemy row affected by the ability
     * @return an error String or an empty String if no error
     */
    public static String useHeroAbility(final GameInput game,
                                        final int rowIndex) {
        Board board = game.getStartGame().getBoard();
        Player player = game.getCurrentPlayer();
        HeroCard hero;
        if (player.getIndex() == 1) {
            hero = (HeroCard) game.getStartGame().getPlayerOneHero();
        } else {
            hero = (HeroCard) game.getStartGame().getPlayerTwoHero();
        }
        return hero.useAbility(board, rowIndex, player);
    }

    /**
     * Run the corresponding command and creates a corresponding output.
     * @param input the input data of all games
     * @param game the current game data
     * @param action the current game commands
     * @return an ObjectNode for the output
     */
    public static ObjectNode run(final Input input,
                                 final GameInput game,
                                 final ActionsInput action) {
        Output out;
        switch (action.getCommand()) {
            case "getPlayerDeck" -> {
                out = new ShowPlayerDeckOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getPlayerDeck(action.getPlayerIdx(), game));
                return out.convertToObjectNode();
            }
            case "getPlayerHero" -> {
                out = new ShowPlayerHeroOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getPlayerHero(action.getPlayerIdx(), game));
                return out.convertToObjectNode();
            }
            case "getPlayerTurn" -> {
                out = new ShowPlayerTurnOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getPlayerTurn(game));
                return out.convertToObjectNode();
            }
            case "endPlayerTurn" -> {
                game.getStartGame().getBoard()
                        .unfrozePlayerCards(game.getCurrentPlayer().getIndex());
                if (game.getCurrentPlayer().getIndex() == 1) {
                    game.setCurrentPlayer(2);
                } else {
                    game.setCurrentPlayer(1);
                }
                game.getStartGame().incNumberOfTurns();
            }
            case "placeCard" -> {
                out = new PlaceError(action.getCommand(), action.getHandIdx(),
                        CommandRun.placeCardOnBoard(game, action.getHandIdx()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "getCardsInHand" -> {
                out = new ShowPlayerCardsOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getPlayerCards(game, action.getPlayerIdx()));
                return out.convertToObjectNode();
            }
            case "getPlayerMana" -> {
                out = new ShowPlayerManaOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getPlayerMana(game, action.getPlayerIdx()));
                return out.convertToObjectNode();
            }
            case "getCardsOnTable" -> {
                out = new ShowCardsOnTableOutput(action.getCommand(),
                        CommandRun.getCardsOnTable(game));
                return out.convertToObjectNode();
            }
            case "cardUsesAttack" -> {
                out = new AttackCardError(action.getCommand(), action.getCardAttacker(),
                        action.getCardAttacked(),
                        CommandRun.cardUsesAttack(game, action.getCardAttacker(),
                                action.getCardAttacked()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "getCardAtPosition" -> {
                CardInput card = getCardAtPosition(game, action.getX(), action.getY());
                if (card == null) {
                    out = new GetCardError(action.getCommand(), "No card at that position.");
                } else {
                    out = new GetCardOutput(action.getCommand(), card);
                }
                return out.convertToObjectNode();
            }
            case "getEnvironmentCardsInHand" -> {
                out = new ShowPlayerCardsOutput(action.getCommand(), action.getPlayerIdx(),
                        CommandRun.getEnvironmentCardsInHand(game, action.getPlayerIdx()));
                return out.convertToObjectNode();
            }
            case "getFrozenCardsOnTable" -> {
                out = new ShowCardsOnTableOutput(action.getCommand(),
                        CommandRun.getFrozenCardsOnTable(game));
                return out.convertToObjectNode();
            }
            case "useEnvironmentCard" -> {
                out = new UseEnvironmentCardError(action.getCommand(),
                        action.getHandIdx(), action.getAffectedRow(),
                        CommandRun.useEnvironmentCard(game, action.getHandIdx(),
                                action.getAffectedRow()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "cardUsesAbility" -> {
                out = new AttackCardError(action.getCommand(),
                        action.getCardAttacker(), action.getCardAttacked(),
                        CommandRun.cardUsesAbility(game,
                                action.getCardAttacker(), action.getCardAttacked()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "useAttackHero" -> {
                out = new AttackHeroError(action.getCommand(), action.getCardAttacker(),
                        CommandRun.useAttackHero(game, action.getCardAttacker()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "useHeroAbility" -> {
                out = new UseHeroAbilityError(action.getCommand(), action.getAffectedRow(),
                        CommandRun.useHeroAbility(game, action.getAffectedRow()));
                if (out.getError().equals("")) {
                    break;
                }
                return out.convertToObjectNode();
            }
            case "getTotalGamesPlayed" -> {
                out = new TotalGamesOutput(action.getCommand(), input.getTotalGames());
                return out.convertToObjectNode();
            }
            case "getPlayerOneWins" -> {
                out = new TotalGamesOutput(action.getCommand(), input.getPlayerOneWins());
                return out.convertToObjectNode();
            }
            case "getPlayerTwoWins" -> {
                out = new TotalGamesOutput(action.getCommand(), input.getPlayerTowWins());
                return out.convertToObjectNode();
            }
            default -> { }
        }
        return null;
    }

}
