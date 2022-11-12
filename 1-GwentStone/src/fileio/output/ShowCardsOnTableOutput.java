package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class ShowCardsOnTableOutput extends Output{
    private ArrayNode output;
    public ShowCardsOnTableOutput(String command) {
        super(command);
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
//        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("command", super.getCommand());
//        for (int i = 0; i < output.size(); ++i) {
//            arr.add(output.get(i).convertToObjectNode());
//        }
        obj.put("output", output);
        return obj;
    }
}
