package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class AttackCardError extends Output{
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;

    private String error;

    public AttackCardError(String command, Coordinates cardAttacker, Coordinates cardAttacked) {
        super(command);
        this.cardAttacked = cardAttacked;
        this.cardAttacker = cardAttacker;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", super.getCommand());
        obj.put("cardAttacker", cardAttacker.convertToObjectNode());
        obj.put("cardAttacked", cardAttacked.convertToObjectNode());
        return obj;
    }
}
