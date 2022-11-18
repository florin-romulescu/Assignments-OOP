package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Coordinates {
   private int x, y;

   public Coordinates() {
   }

   public Coordinates(final int x, final int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   @Override
   public String toString() {
      return "Coordinates{"
              + "x="
              + x
              + ", y="
              + y
              + '}';
   }

   /**
    * Convert the data of the class into an ObjectNode.
    * @return an ObjectNode for the output
    */
   public ObjectNode convertToObjectNode() {
      ObjectNode obj = new ObjectMapper().createObjectNode();
      obj.put("x", x);
      obj.put("y", y);
      return obj;
   }
}
