package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

class Row {
    ArrayList<CardInput> cells = new ArrayList<>();

    public ArrayList<CardInput> getCells() {
        return cells;
    }

    public ArrayNode convertToArrayNode() {
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < cells.size(); ++i) {
            arr.add(cells.get(i).convertToObjectNode());
        }
        return arr;
    }
}
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

    public boolean isFrozen(Coordinates coords) {
        CardInput card = getCard(coords);
        if (card == null) {
            return false;
        }
        return card.isFrozen();
    }

    public int getCardPlayerIndex(Coordinates coords) {
        if (coords.getX() == 0 || coords.getX() == 1) {
            return 2;
        }
        if (coords.getX() == 2 || coords.getX() == 3) {
            return 1;
        }
        return 0;
    }

    public ArrayNode convertToArrayNode() {
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < rows.size(); ++i) {
                    arr.add(rows.get(i).convertToArrayNode());
        }
        return arr;
    }

}
