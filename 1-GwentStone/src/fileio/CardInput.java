package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gameplay_elements.Board;
import gameplay_elements.HeroCard;
import gameplay_elements.Player;
import gameplay_elements.cards.*;

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

    /**
     * Verify if the card is environment.
     * @return true/false if the card is environment
     */
    public boolean isEnvironmentCard() {
        return name.equals("Firestorm") ||
                name.equals("Winterfell") ||
                name.equals("Heart Hound");
    }

    /**
     * Verify if the card is place in the front row.
     * @return true/false if card is placed in the front row
     */
    public boolean isFrontRowCard() {
        return  name.equals("The Ripper") ||
                name.equals("Miraj") ||
                name.equals("Goliath") ||
                name.equals("Warden");
    }

    /**
     * Verify if the card is a tank.
     * @return true/false if the card is a tank
     */
    public boolean isTankCard() {
        return  name.equals("Goliath") ||
                name.equals("Warden");
    }

    /**
     * Convert tha card data into an ObjectNode.
     * @return an ObjectNode for the output
     */
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

    /**
     * Subtract from the card's attacked health the corresponding
     * card attack damage.
     * @param cardAttacked the card which is attacked
     */
    public void useAttack(CardInput cardAttacked) {
        hasAttacked = true;
        cardAttacked.setHealth(cardAttacked.getHealth() - attackDamage);
    }

    /**
     * Subtract from the hero's attacked health the corresponding
     * card attack damage.
     * @param hero the hero which is attacked
     */
    public void useAttack(HeroCard hero) {
        hasAttacked = true;
        hero.setHealth(hero.getHealth() - attackDamage);
    }

    /**
     * Use environment card ability on the given row.
     * If the ability can be activated, it will subtract
     * the corresponding amount of mana from the player.
     * @param board the current game board
     * @param row the affected enemy row
     * @param player the current player
     * @return an error String or empty String if no error
     */
    public String useEnvironmentAbility(Board board, int row, Player player) {
        if (!this.isEnvironmentCard())
            return "Chosen card is not of type environment.";
        if (mana > player.getMana())
            return "Not enough mana to use environment card.";
        if (board.getRowPlayerIndex(row) == player.getIndex())
            return "Chosen row does not belong to the enemy.";
        switch (name) {
            case "Firestorm":
                Firestorm.useAbility(board, row);
                break;
            case "Winterfell":
                Winterfell.useAbility(board, row);
                break;
            case "Heart Hound":
                if (!HeartHound.useAbility(board, row, player))
                    return "Cannot steal enemy card since the player's row is full.";
                break;
        }
        player.setMana(player.getMana() - mana);
        return "";
    }

    /**
     * Use card ability on the given card.
     * If the ability can be activated, it will subtract
     * the corresponding amount of mana from the player.
     * @param board the current game board
     * @param affectedCardCoords the affected enemy row
     * @param player the current player
     * @return an error String or empty String if no error
     */
    public String useCardAbility(Board board, Coordinates affectedCardCoords, Player player) {
        if (this.isFrozen()) {
            return "Attacker card is frozen.";
        }
        if (this.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }
        CardInput affectedCard = board.getCard(affectedCardCoords);

        switch (name) {
            case "Disciple" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) != player.getIndex()) {
                    return "Attacked card does not belong to the current player.";
                }
                Disciple.useAbility(affectedCard);
            }
            case "The Ripper" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) == player.getIndex()) {
                    return "Attacked card does not belong to the enemy.";
                }
                if (board.enemyHasTank(player.getIndex()) &&
                        !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                TheRipper.useAbility(affectedCard);
            }
            case "Miraj" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) == player.getIndex()) {
                    return "Attacked card does not belong to the enemy.";
                }
                if (board.enemyHasTank(player.getIndex()) &&
                        !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                Miraj.useAbility(this, affectedCard);
            }
            case "The Cursed One" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) == player.getIndex()) {
                    return "Attacked card does not belong to the enemy.";
                }
                if (board.enemyHasTank(player.getIndex()) &&
                        !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                TheCursedOne.useAbility(this, affectedCard);
            }
        }
        this.setHasAttacked(true);
        return "";
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
