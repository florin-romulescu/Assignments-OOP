package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UseEnvironmentCardError implements Output{
    private String command;
    private String error;
    private int handIndex;
    private int rowIndex;

    public UseEnvironmentCardError(String command, int handIndex, int rowIndex, String error) {
        this.command = command;
        this.error = error;
        this.handIndex = handIndex;
        this.rowIndex = rowIndex;
    }

    @Override
    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("handIdx", handIndex);
        obj.put("affectedRow", rowIndex);
        obj.put("error", error);
        return obj;
    }
}
