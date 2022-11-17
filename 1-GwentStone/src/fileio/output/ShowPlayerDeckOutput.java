package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class ShowPlayerDeckOutput implements Output{
    private String command;
    private int playerIndex;
    private ArrayList<CardInput> output;

    public ShowPlayerDeckOutput(String command, int playerIndex, ArrayList<CardInput> output) {
        this.command = command;
        this.playerIndex = playerIndex;
        this.output = output;
    }

    @Override
    public String getError() {
        return "";
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("command", command);
        obj.put("playerIdx", playerIndex);
        for (int i = 0; i < output.size(); ++i) {
            arr.add(output.get(i).convertToObjectNode());
        }
        obj.put("output", arr);
        return obj;
    }
}
