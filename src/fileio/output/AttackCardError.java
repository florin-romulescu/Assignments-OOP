package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class AttackCardError implements Output {
    private final String command;
    private final Coordinates cardAttacker;
    private final Coordinates cardAttacked;

    private final String error;

    public AttackCardError(final String command, final Coordinates cardAttacker,
                           final Coordinates cardAttacked, final String error) {
        this.command = command;
        this.cardAttacked = cardAttacked;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    @Override
    public String getError() {
        return error;
    }

    /**
     * Get the error value of the class.
     * @return an error String
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("cardAttacker", cardAttacker.convertToObjectNode());
        obj.put("cardAttacked", cardAttacked.convertToObjectNode());
        obj.put("error", error);
        return obj;
    }
}
