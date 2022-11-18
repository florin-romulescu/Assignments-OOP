
package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class ShowPlayerDeckOutput implements Output {
    private final String command;
    private final int playerIndex;
    private final ArrayList<CardInput> output;

    public ShowPlayerDeckOutput(final String command,
                                final int playerIndex,
                                final ArrayList<CardInput> output) {
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
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("command", command);
        obj.put("playerIdx", playerIndex);
        for (int i = 0; i < output.size(); ++i) {
            arr.add(output.get(i).convertToObjectNode());
        }
        obj.put("output", arr);
        return obj;
    }
}
