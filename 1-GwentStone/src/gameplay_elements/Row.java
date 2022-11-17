package gameplay_elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Row {
    ArrayList<CardInput> cells = new ArrayList<>();

    public ArrayList<CardInput> getCells() {
        return cells;
    }

    /**
     * Convert to an ArrayNode for the output.
     * @return an ArrayNode with all cards
     */
    public ArrayNode convertToArrayNode() {
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < cells.size(); ++i) {
            arr.add(cells.get(i).convertToObjectNode());
        }
        return arr;
    }

    /**
     * Convert to an ArrayNode for the output.
     * @return an ArrayNode with all cards if frozen
     */
    public ArrayNode convertToArrayNodeIfFrozen() {
        ArrayNode arr = new ObjectMapper().createArrayNode();
        for (int i = 0; i < cells.size(); ++i) {
            if (cells.get(i).isFrozen())
                arr.add(cells.get(i).convertToObjectNode());
        }
        return arr;
    }

}