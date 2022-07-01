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

  public static SeamInfo getMinimumWeightSeam(List<SeamInfo> upperNeighbors) throws NoSuchElementException {
    Optional<SeamInfo> minimum = upperNeighbors.stream().min(
            (o1, o2) -> (int) (o1.getTotalWeight() - o2.getTotalWeight()));
    return minimum.get();
  }

  public static List<List<Pixel>> toArray(String path) throws IllegalArgumentException {
    String[] splitPath = path.split("\\.");
    String extension = splitPath[splitPath.length - 1];
    try {
      return readImageAt(path);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read image");
    }
  }

  private static List<List<Pixel>> readImageAt(String path) throws IOException {
    BufferedImage im = ImageIO.read(new File(path));
    int width = im.getWidth();
    int height = im.getHeight();
    List<List<Pixel>> pixels = new ArrayList<>();
    for (int r = 0; r < height; r++) {
      List<Pixel> row = new ArrayList<>();
      for (int c = 0; c < width; c++) {
        Color color = new Color(im.getRGB(c, r));
        Pixel p = new Pixel(color);
        row.add(p);
      }
      pixels.add(row);
    }
    return pixels;
  }

  public static BufferedImage toBufferedImage(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Color c = image.getColorAt(row, col);
        im.setRGB(col, row, c.getRGB());
      }
    } 
    return im;
  }
}
