package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TotalGamesOutput implements Output {
    private final String command;
    private final int output;

    public TotalGamesOutput(final String command,
                            final int output) {
        this.command = command;
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
        obj.put("output", output);
        return obj;
    }
}
