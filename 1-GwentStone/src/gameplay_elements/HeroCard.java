package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import gameplay_elements.cards.EmpressThorina;
import gameplay_elements.cards.GeneralKocioraw;
import gameplay_elements.cards.KingMudface;
import gameplay_elements.cards.LordRoyce;

public class HeroCard extends CardInput {
    private final int MAX_HEALTH = 30;
    public HeroCard(CardInput card) {
        super.setMana(card.getMana());
        super.setHealth(MAX_HEALTH);
        super.setColors(card.getColors());
        super.setAttackDamage(card.getAttackDamage());
        super.setName(card.getName());
        super.setDescription(card.getDescription());
    }

    public String useAbility(Board board, int rowIndex, Player player) {
        if (player.getMana() < this.getMana())
            return "Not enough mana to use hero's ability.";
        if (this.getHasAttacked())
            return "Hero has already attacked this turn.";
        switch (this.getName()) {
            case "Lord Royce" -> {
                if (board.getRowPlayerIndex(rowIndex) == player.getIndex())
                    return "Selected row does not belong to the enemy.";
                LordRoyce.useAbility(board, rowIndex);
            }
            case "Empress Thorina" -> {
                if (board.getRowPlayerIndex(rowIndex) == player.getIndex())
                    return "Selected row does not belong to the enemy.";
                EmpressThorina.useAbility(board, rowIndex);
            }
            case "General Kocioraw" -> {
                if (board.getRowPlayerIndex(rowIndex) != player.getIndex())
                    return "Selected row does not belong to the current player.";
                GeneralKocioraw.useAbility(board, rowIndex);
            }
            case "King Mudface" -> {
                if (board.getRowPlayerIndex(rowIndex) != player.getIndex())
                    return "Selected row does not belong to the current player.";
                KingMudface.useAbility(board, rowIndex);
            }
        }
        this.setHasAttacked(true);
        player.setMana(
                player.getMana() - this.getMana()
        );
        return "";
    }

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
