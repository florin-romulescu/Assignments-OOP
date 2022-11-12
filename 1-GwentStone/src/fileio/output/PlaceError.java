package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlaceError extends Output{
    private int handIndex;
    private String error;

    public PlaceError(String command, int handIndex) {
        super(command);
        this.handIndex = handIndex;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", getCommand());
        obj.put("handIdx", handIndex);
        obj.put("error", error);

        return obj;
    }
}
