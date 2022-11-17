package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class ShowPlayerHeroOutput implements Output{
    private String command;
    private int playerIndex;
    private CardInput output;

    public ShowPlayerHeroOutput(String command, int playerIndex, CardInput output) {
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
        obj.put("output", output.convertToObjectNode());

        return obj;
    }
}