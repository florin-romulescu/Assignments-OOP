package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class GetCardOutput implements Output{
    private  String command;
    private CardInput output;

    public GetCardOutput(String command, CardInput output) {
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
        obj.put("output", output.convertToObjectNode());
        return obj;
    }
}
