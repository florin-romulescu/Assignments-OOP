package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UseHeroAbilityError implements Output{
    private String command;
    private String error;
    private int rowIndex;

    public UseHeroAbilityError(String command, int rowIndex, String error) {
        this.command = command;
        this.error = error;
        this.rowIndex = rowIndex;
    }

    @Override
    public String getError() {
        return error;
    }

    public ObjectNode convertToObjectNode() {
        ObjectNode obj = new ObjectMapper().createObjectNode();
        obj.put("command", command);
        obj.put("affectedRow", rowIndex);
        obj.put("error", error);
        return obj;
    }
}
