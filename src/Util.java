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

  public static SeamInfo getMinimumWeightInfo(SeamInfo topLeft, SeamInfo top, SeamInfo topRight) {
    double minimum = Math.min(topRight.getTotalWeight(),
            Math.min(topLeft.getTotalWeight(), top.getTotalWeight()));
    if(minimum == topLeft.getTotalWeight()) {
      return topLeft;
    } else if(minimum == top.getTotalWeight()) {
      return top;
    }
    return topRight;
  }

  public static SeamInfo getMinimumWeightSeam(List<SeamInfo> upperNeighbors) throws NoSuchElementException {
    Optional<SeamInfo> minimum = upperNeighbors.stream().min(
            (o1, o2) -> (int) (o1.getTotalWeight() - o2.getTotalWeight()));
    return minimum.get();
  }
}
