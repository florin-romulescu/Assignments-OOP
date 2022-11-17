package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetCardError implements Output{
    private  String command;
    private String error;

    public GetCardError(String command, String error) {
        this.command = command;
        this.error = error;
    }

    @Override
    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("output", error);
        return obj;
    }
}
