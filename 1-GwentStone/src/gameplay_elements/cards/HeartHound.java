package gameplay_elements.cards;

import fileio.CardInput;
import fileio.Coordinates;
import gameplay_elements.Board;
import gameplay_elements.Player;

public class HeartHound {
    private HeartHound() {}

    /**
     * Apply the special ability of this card.
     * @param board the current board
     * @param row the affected row
     * @param player the current player
     * @return true/false if successfully used
     */
    public static boolean useAbility(Board board, int row, Player player) {
        Coordinates coords = new Coordinates(row, 0);

        int nr = 0;
        CardInput maxHealthCard = null;

        for (CardInput card: board.getRows().get(row).getCells()) {
            if (maxHealthCard == null) {
                maxHealthCard = card;
            } else if (maxHealthCard.getHealth() < card.getHealth()) {
                maxHealthCard = card;
                coords.setY(0);
            }
            nr += 1;
        }

        player.getCardsInHand().add(maxHealthCard);
        String msg = board.placeCard(player, player.getCardsInHand().size() - 1);
        if (msg.equals("")) {
            board.removeCard(coords);
        } else {
            player.getCardsInHand().remove(player.getCardsInHand().size() - 1);
            return false;
        }
        return true;
    }
}
