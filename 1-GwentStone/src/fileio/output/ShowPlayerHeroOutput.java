package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class ShowPlayerHeroOutput extends Output{
    private int playerIndex;
    private CardInput output;
    public ShowPlayerHeroOutput(String command, int playerIndex) {
        super(command);
        this.playerIndex = playerIndex;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();

        obj.put("command", super.getCommand());
        obj.put("playerIdx", playerIndex);
        obj.put("output", output.convertToObjectNode());

        return obj;
    }

    public void setOutput(CardInput output) {
        this.output = output;
    }
}