package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShowPlayerManaOutput implements Output{
    private String command;
    private int playerIndex;
    private int output;

    public ShowPlayerManaOutput(String command, int playerIndex, int output) {
        this.command = command;
        this.playerIndex = playerIndex;
        this.output = output;
    }

    @Override
    public String getError() {
        return "";
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("playerIdx", playerIndex);
        obj.put("output", output);

        return obj;
    }
}
