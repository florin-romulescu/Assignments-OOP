package gameplayelements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import gameplayelements.cards.EmpressThorina;
import gameplayelements.cards.GeneralKocioraw;
import gameplayelements.cards.KingMudface;
import gameplayelements.cards.LordRoyce;

public class HeroCard extends CardInput {
    public HeroCard(final CardInput card) {
        final int maxHealth = 30;
        super.setMana(card.getMana());
        super.setHealth(maxHealth);
        super.setColors(card.getColors());
        super.setAttackDamage(card.getAttackDamage());
        super.setName(card.getName());
        super.setDescription(card.getDescription());
    }

    /**
     * Use hero ability.
     * @param board the current board
     * @param rowIndex the affected row
     * @param player the current player
     * @return error String or empty String if no error
     */
    public String useAbility(final Board board,
                             final int rowIndex,
                             final Player player) {
        if (player.getMana() < this.getMana()) {
            return "Not enough mana to use hero's ability.";
        }
        if (this.getHasAttacked()) {
            return "Hero has already attacked this turn.";
        }
        switch (this.getName()) {
            case "Lord Royce" -> {
                if (board.getRowPlayerIndex(rowIndex) == player.getIndex()) {
                    return "Selected row does not belong to the enemy.";
                }
                LordRoyce.useAbility(board, rowIndex);
            }
            case "Empress Thorina" -> {
                if (board.getRowPlayerIndex(rowIndex) == player.getIndex()) {
                    return "Selected row does not belong to the enemy.";
                }
                EmpressThorina.useAbility(board, rowIndex);
            }
            case "General Kocioraw" -> {
                if (board.getRowPlayerIndex(rowIndex) != player.getIndex()) {
                    return "Selected row does not belong to the current player.";
                }
                GeneralKocioraw.useAbility(board, rowIndex);
            }
            case "King Mudface" -> {
                if (board.getRowPlayerIndex(rowIndex) != player.getIndex()) {
                    return "Selected row does not belong to the current player.";
                }
                KingMudface.useAbility(board, rowIndex);
            }
            default -> { }
        }
        this.setHasAttacked(true);
        player.setMana(
                player.getMana() - this.getMana()
        );
        return "";
    }

    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("mana", super.getMana());
        obj.put("description", super.getDescription());
        for (int i = 0; i < super.getColors().size(); ++i) {
            arr.add(super.getColors().get(i));
        }
        obj.put("colors", arr);
        obj.put("name", super.getName());
        obj.put("health", super.getHealth());
        return obj;
    }
}
