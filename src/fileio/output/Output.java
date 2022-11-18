package fileio.output;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Output {
    /**
     * Convert the data of the class into an ObjectNode.
     * @return an ObjectNode for the output
     */
    ObjectNode convertToObjectNode();

    /**
     * Get the error value of the class.
     * @return an error String
     */
    String getError();
}
