package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class AttackHeroError implements Output{
    String command;
    Coordinates cardAttacker;
    String error;

    public AttackHeroError(String command, Coordinates cardAttacker, String error) {
        this.command = command;
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
        obj.put("error", error);
        return obj;
    }
}
