package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class EnvironmentCard extends CardInput {
    public EnvironmentCard(CardInput card) {
        super.setMana(card.getMana());
        super.setHealth(0);
        super.setColors(card.getColors());
        super.setAttackDamage(card.getAttackDamage());
        super.setName(card.getName());
        super.setDescription(card.getDescription());
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
        return obj;
    }
}
