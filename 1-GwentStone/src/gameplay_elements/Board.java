package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Board{
    ArrayList<Row> rows = new ArrayList<>();

    public Board() {
        for (int i = 0; i < 4; ++i)
            rows.add(new Row());
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public String placeCard(Player player, int cardIndex) {
        if (player.getCardsInHand().size() <= cardIndex) {
            return "Error";
        }
        if (player.getCardsInHand().get(cardIndex).isEnvironmentCard()) {
            return "Cannot place environment card on table.";
        }
        if (player.getCardsInHand().get(cardIndex).getMana() > player.getMana()) {
            return "Not enough mana to place card on table.";
        }
        if (player.getIndex() == 1) {
            if (player.getCardsInHand().get(cardIndex).isFrontRowCard()) {
                if (rows.get(2).getCells().size() == 5) {
                    return "Cannot place card on table since row is full.";
                }
                    rows.get(2).getCells().add(player.getCardsInHand().get(cardIndex));
                    player.setMana(
                            player.getMana() - player.getCardsInHand().get(cardIndex).getMana()
                    );
                    player.getCardsInHand().remove(cardIndex);
            } else {
                if (rows.get(3).getCells().size() == 5) {
                    return "Cannot place card on table since row is full.";
                }
                rows.get(3).getCells().add(player.getCardsInHand().get(cardIndex));
                player.setMana(
                        player.getMana() - player.getCardsInHand().get(cardIndex).getMana()
                );
                player.getCardsInHand().remove(cardIndex);
            }
        } else {
            if (player.getCardsInHand().get(cardIndex).isFrontRowCard()) {
                if (rows.get(1).getCells().size() == 5) {
                    return "Cannot place card on table since row is full.";
                }
                rows.get(1).getCells().add(player.getCardsInHand().get(cardIndex));
                player.setMana(
                        player.getMana() - player.getCardsInHand().get(cardIndex).getMana()
                );
                player.getCardsInHand().remove(cardIndex);
            } else {
                if (rows.get(0).getCells().size() == 5) {
                    return "Cannot place card on table since row is full.";
                }
                rows.get(0).getCells().add(player.getCardsInHand().get(cardIndex));
                player.setMana(
                        player.getMana() - player.getCardsInHand().get(cardIndex).getMana()
                );
                player.getCardsInHand().remove(cardIndex);
            }
        }
        return "";
    }

    public CardInput removeCard(Coordinates coords) {
        return rows.get(coords.getX())
                .getCells()
                .remove(coords.getY());
    }

    public CardInput getCard(Coordinates coords) {
        if (coords.getX() < 0 || coords.getX() >= this.getRows().size()) {
            return null;
        }
        if (coords.getY() < 0 || coords.getY() >= this.getRows().get(coords.getX()).getCells().size()) {
            return null;
        }
//        System.out.println(coords);
//        System.out.println(rows.get(coords.getX()).getCells().size());
        return this.getRows()
                .get(coords.getX())
                .getCells()
                .get(coords.getY());
    }

    /**
     * Verify if the enemy player has a tank card.
     * @param currentPlayerIndex the current player's index
     * @return true if a tank is on the enemy player's rows else false
     */
    public boolean enemyHasTank(int currentPlayerIndex) {
        if (currentPlayerIndex == 1) {
            for (int i = 0; i <= 1; ++i) {
                Row row = rows.get(i);
                for (int j = 0; j < row.getCells().size(); ++j) {
                    if (row.getCells().get(j).isTankCard()) {
                        return true;
                    }
                }
            }
        } else {
            for (int i = 2; i <= 3; ++i) {
                Row row = rows.get(i);
                for (int j = 0; j < row.getCells().size(); ++j) {
                    if (row.getCells().get(j).isTankCard()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verify if there are frozen cards.
     * @return true if there are frozen cards else false.
     */
    public boolean isFrozenCardOnTable() {
        for (Row row: rows) {
            for (CardInput card: row.getCells()) {
                if (card.isFrozen()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verify if the card with the given coordinates is frozen.
     * @param coords the card coordinates
     * @return true if the given card is frozen else false
     */
    public boolean isFrozen(Coordinates coords) {
        CardInput card = getCard(coords);
        if (card == null) {
            return false;
        }
        return card.isFrozen();
    }

    /**
     * Get cards owner based on the row.
     * @param coords the card coordinates
     * @return the owner's index
     */
    public int getCardPlayerIndex(Coordinates coords) {
        if (coords.getX() == 0 || coords.getX() == 1) {
            return 2;
        }
        if (coords.getX() == 2 || coords.getX() == 3) {
            return 1;
        }
        return 0; // TODO
    }

    /**
     * Get the row's owner.
     * @param row the row to verify
     * @return the owner's index
     */
    public int getRowPlayerIndex(int row) {
        if (row == 0 || row == 1) {
            return 2;
        }
        if (row == 2 || row == 3) {
            return 1;
        }
        return 0; // TODO
    }

    /**
     * Delete the cards with health <= 0 from the board.
     */
    public void removeEliminatedCards() {
        for (Row row: rows) {
            for (int i = 0; i < row.getCells().size(); ++i) {
                if (row.getCells().get(i).getHealth() <= 0) {
                    row.getCells().remove(i);
                    i -= 1;
                }
            }
        }
    }

    /**
     * Set hasAttacked = false for all cards.
     */
    public void initialiseNewRound() {
        for (Row row : rows) {
            for (CardInput card: row.getCells()) {
                card.setHasAttacked(false);
            }
        }
    }

    /**
     * Set frozen=false for all cards of the given player.
     * @param playerIndex the corresponding player
     */
    public void unfrozePlayerCards(int playerIndex) {
        if (playerIndex == 1) {
            for (int i = 2; i <= 3; ++i) {
                for (CardInput card: rows.get(i).getCells()) {
                    card.setFrozen(false);
                }
            }
        } else if (playerIndex == 2) {
            for (int i = 0; i <= 1; ++i) {
                for (CardInput card: rows.get(i).getCells()) {
                    card.setFrozen(false);
                }
            }
        }
    }

    /**
     * Convert the board to an ObjectNode for the output.
     * @return an ArrayNode with all the cards
     */
    public ArrayNode convertToArrayNode() {
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < rows.size(); ++i) {
                    arr.add(rows.get(i).convertToArrayNode());
        }
        return arr;
    }

    /**
     * Convert the board to an ObjectNode for the output.
     * @return an ArrayNode with all the cards if they are frozen
     */
    public ArrayNode convertToArrayNodeIfFrozen() {
        if (!isFrozenCardOnTable()) {
            return new ObjectMapper().createArrayNode();
        }
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < rows.size(); ++i) {
            arr.add(rows.get(i).convertToArrayNodeIfFrozen());
        }
        return arr;
    }
}
