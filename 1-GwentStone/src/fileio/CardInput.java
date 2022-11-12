package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class CardInput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean isFrozen;
    private boolean hasAttacked = false;

    public CardInput() {
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean getHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean isEnvironmentCard() {
        return name.equals("Firestorm") ||
                name.equals("Winterfell") ||
                name.equals("Heart Hound");
    }

    public boolean isFrontRowCard() {
        return  name.equals("The Ripper") ||
                name.equals("Miraj") ||
                name.equals("Goliath") ||
                name.equals("Warden");
    }

    public boolean isBackRowCard() {
        return  name.equals("Sentinel") ||
                name.equals("Berserker") ||
                name.equals("The Cursed One") ||
                name.equals("Disciple");
    }

    public boolean isTankCard() {
        return  name.equals("Goliath") ||
                name.equals("Warden");
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("mana", mana);
        if (!isEnvironmentCard()) {
            obj.put("attackDamage", attackDamage);
            obj.put("health", health);
        }
        obj.put("description", description);
        for (int i = 0; i < colors.size(); ++i) {
            arr.add(colors.get(i));
        }
        obj.put("colors", arr);
        obj.put("name", name);
        return obj;
    }

    public void useAttack(CardInput cardAttacked) {
        hasAttacked = false;
        cardAttacked.setHealth(cardAttacked.getHealth() - attackDamage);
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
