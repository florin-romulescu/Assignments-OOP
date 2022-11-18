package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlaceError implements Output {
    private final String command;
    private final int handIndex;
    private final String error;

    public PlaceError(final String command,
                      final int handIndex, final String error) {
        this.command = command;
        this.handIndex = handIndex;
        this.error = error;
    }

    /**
     * Get the error value of the class.
     * @return an error String
     */
    @Override
    public String getError() {
        return error;
    }

    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("handIdx", handIndex);
        obj.put("error", error);

        return obj;
    }
}
