package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GameEndedOutput {
    private final int winner;
    public GameEndedOutput(final int winner) {
        this.winner = winner;
    }

    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        if (winner == 1) {
            obj.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            obj.put("gameEnded", "Player two killed the enemy hero.");
        }
        return obj;
    }
}
