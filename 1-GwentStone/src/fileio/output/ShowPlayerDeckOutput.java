package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class ShowPlayerDeckOutput extends Output{
    private int playerIndex;
    private ArrayList<CardInput> output;

    public ShowPlayerDeckOutput(String command, int playerIndex) {
        super(command);
        this.playerIndex = playerIndex;
    }

    public void setOutput(ArrayList<CardInput> output) {
        this.output = output;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public ArrayList<CardInput> getOutput() {
        return output;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        ArrayNode arr = new ObjectMapper().createArrayNode();
        obj.put("command", super.getCommand());
        obj.put("playerIdx", playerIndex);
        for (int i = 0; i < output.size(); ++i) {
            arr.add(output.get(i).convertToObjectNode());
        }
        obj.put("output", arr);
        return obj;
    }

    @Override
    public String toString() {
        return "{" +
                "\"command:\"" + super.getCommand() +
                ",\"playerIndex:\"" + playerIndex +
                ", \"output:\"" + output +
                '}';
    }
}
