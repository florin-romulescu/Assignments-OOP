package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gameplayelements.Board;
import gameplayelements.HeroCard;
import gameplayelements.Player;
import gameplayelements.cards.TheRipper;
import gameplayelements.cards.Disciple;
import gameplayelements.cards.HeartHound;
import gameplayelements.cards.Firestorm;
import gameplayelements.cards.Winterfell;
import gameplayelements.cards.Miraj;
import gameplayelements.cards.TheCursedOne;

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

    /**
     * Get the mana cost.
     * @return the cost to use the card
     */
    public int getMana() {
        return mana;
    }

    /**
     * Set the mana cost.
     * @param mana the new mana cost
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Get the attack damagae.
     * @return the attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Set the attack damage.
     * @param attackDamage the new attack damage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Get the card's health.
     * @return the card's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set card's health.
     * @param health the new card's health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for card description.
     * @return the card description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for card description.
     * @param description the new card description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for card's colors.
     * @return the cord's colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for card's colors.
     * @param colors the new card's colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for card's name.
     * @return the card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for card's name.
     * @param name the card's name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for hasAttacked field.
     * @return the hasAttacked field
     */
    public boolean getHasAttacked() {
        return hasAttacked;
    }

    /**
     * Setter for hasAttacked field.
     * @param hasAttacked the new hasAttacked field
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Getter for isFrozen field.
     * @return the isFrozen field value
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Setter for isFrozen field
     * @param frozen the new isFrozen value
     */
    public void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    /**
     * Verify if the card is environment.
     * @return true/false if the card is environment
     */
    public boolean isEnvironmentCard() {
        return name.equals("Firestorm")
            || name.equals("Winterfell")
            || name.equals("Heart Hound");
    }

    /**
     * Verify if the card is place in the front row.
     * @return true/false if card is placed in the front row
     */
    public boolean isFrontRowCard() {
        return  name.equals("The Ripper")
            || name.equals("Miraj")
            || name.equals("Goliath")
            || name.equals("Warden");
    }

    /**
     * Verify if the card is a tank.
     * @return true/false if the card is a tank
     */
    public boolean isTankCard() {
        return  name.equals("Goliath")
            || name.equals("Warden");
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
    public void useAttack(final CardInput cardAttacked) {
        hasAttacked = true;
        cardAttacked.setHealth(cardAttacked.getHealth() - attackDamage);
    }

    /**
     * Subtract from the hero's attacked health the corresponding
     * card attack damage.
     * @param hero the hero which is attacked
     */
    public void useAttack(final HeroCard hero) {
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
    public String useEnvironmentAbility(final Board board,
                                        final int row,
                                        final Player player) {
        if (!this.isEnvironmentCard()) {
            return "Chosen card is not of type environment.";
        }
        if (mana > player.getMana()) {
            return "Not enough mana to use environment card.";
        }
        if (board.getRowPlayerIndex(row) == player.getIndex()) {
            return "Chosen row does not belong to the enemy.";
        }
        switch (name) {
            case "Firestorm":
                Firestorm.useAbility(board, row);
                break;
            case "Winterfell":
                Winterfell.useAbility(board, row);
                break;
            case "Heart Hound":
                if (!HeartHound.useAbility(board, row, player)) {
                    return "Cannot steal enemy card since the player's row is full.";
                }
                break;
            default:
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
    public String useCardAbility(final Board board,
                                 final Coordinates affectedCardCoords,
                                 final Player player) {
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
                if (board.enemyHasTank(player.getIndex())
                        && !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                TheRipper.useAbility(affectedCard);
            }
            case "Miraj" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) == player.getIndex()) {
                    return "Attacked card does not belong to the enemy.";
                }
                if (board.enemyHasTank(player.getIndex())
                        && !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                Miraj.useAbility(this, affectedCard);
            }
            case "The Cursed One" -> {
                if (board.getCardPlayerIndex(affectedCardCoords) == player.getIndex()) {
                    return "Attacked card does not belong to the enemy.";
                }
                if (board.enemyHasTank(player.getIndex())
                        && !affectedCard.isTankCard()) {
                    return "Attacked card is not of type 'Tank'.";
                }
                TheCursedOne.useAbility(this, affectedCard);
            }
            default -> { }
        }
        this.setHasAttacked(true);
        return "";
    }
}
