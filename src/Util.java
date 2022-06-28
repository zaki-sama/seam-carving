import java.util.*;

public class Util {
  public static Pixel getPixelAt(List<List<Pixel>> pixels, int row, int col) {
    boolean inBounds = (row >= 0 && col >= 0
            && row < pixels.size()) && col < pixels.get(0).size();

    if(inBounds) {
      return pixels.get(row).get(col);
    } else {
      return null;
    }
  }
}
