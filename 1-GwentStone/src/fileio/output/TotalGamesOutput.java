package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TotalGamesOutput implements Output{
    private String command;
    private int output;

    public TotalGamesOutput(String command, int output) {
        this.command = command;
        this.output = output;
    }

    @Override
    public String getError() {
        return "";
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("output", output);
        return obj;
    }
}
