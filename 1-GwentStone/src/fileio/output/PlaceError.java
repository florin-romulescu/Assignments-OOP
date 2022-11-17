package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlaceError implements Output{
    private String command;
    private int handIndex;
    private String error;

    public PlaceError(String command, int handIndex, String error) {
        this.command = command;
        this.handIndex = handIndex;
        this.error = error;
    }

    @Override
    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("handIdx", handIndex);
        obj.put("error", error);

        return obj;
    }
}
