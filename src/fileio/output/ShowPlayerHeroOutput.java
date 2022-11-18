package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class ShowPlayerHeroOutput implements Output {
    private final String command;
    private final int playerIndex;
    private final CardInput output;

    public ShowPlayerHeroOutput(final String command,
                                final int playerIndex,
                                final CardInput output) {
        this.command = command;
        this.playerIndex = playerIndex;
        this.output = output;
    }

    /**
     * Get the error value of the class.
     * @return an error String
     */
    @Override
    public String getError() {
        return "";
    }

    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();

        obj.put("command", command);
        obj.put("playerIdx", playerIndex);
        obj.put("output", output.convertToObjectNode());

        return obj;
    }
}
