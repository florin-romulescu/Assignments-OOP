package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShowPlayerTurnOutput extends Output{
    private int playerIndex;
    private int output;
    public ShowPlayerTurnOutput(String command, int playerIndex) {
        super(command);
        this.playerIndex = playerIndex;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", super.getCommand());
        obj.put("output", output);
        return obj;
    }
}
