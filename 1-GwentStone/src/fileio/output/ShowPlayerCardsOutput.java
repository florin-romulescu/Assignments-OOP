package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class ShowPlayerCardsOutput extends Output{
    private int playerIndex;
    private ArrayList<CardInput> output;
    public ShowPlayerCardsOutput(String command, int playerIndex) {
        super(command);
        this.playerIndex = playerIndex;
    }

    public void setOutput(ArrayList<CardInput> output) {
        this.output = output;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("command", getCommand());
        obj.put("playerIdx", playerIndex);

        for (int i = 0; i < output.size(); ++i) {
            arr.add(output.get(i).convertToObjectNode());
        }
        obj.put("output", arr);

        return obj;
    }
}
