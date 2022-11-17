package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class AttackCardError implements Output{
    private String command;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;

    private String error;

    public AttackCardError(String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
        this.command = command;
        this.cardAttacked = cardAttacked;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    @Override
    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("cardAttacker", cardAttacker.convertToObjectNode());
        obj.put("cardAttacked", cardAttacked.convertToObjectNode());
        obj.put("error", error);
        return obj;
    }
}
