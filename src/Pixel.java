import java.awt.Color;
import java.util.*;

import javalib.worldimages.FromFileImage;

public class Pixel {
  private final Color color;
  private final double brightness;
  private double energy;
  private Pixel top;
  private Pixel bottom;
  private Pixel left;
  private Pixel right;
  private Pixel topLeft;
  private Pixel topRight;
  private Pixel bottomRight;
  private Pixel bottomLeft;
  private SeamInfo seam;

  public Pixel(FromFileImage image, int row, int col) {
    Color color = image.getColorAt(col, row);
    this.color = color;
    this.brightness = calcBrightness(color);
  }

  //PUBLIC METHODS ---------------------------------------------------
  public void setEnergy() {
    double topB = getNeighborBrightness(this.top);
    double bottomB = getNeighborBrightness(this.bottom);
    double leftB = getNeighborBrightness(this.left);
    double rightB = getNeighborBrightness(this.right);
    double topLeftB = getNeighborBrightness(this.topLeft);
    double topRightB = getNeighborBrightness(this.topRight);
    double bottomRightB = getNeighborBrightness(this.bottomRight);
    double bottomLeftB = getNeighborBrightness(this.bottomLeft);

    double horizontalEnergy = (topLeftB + (2 * leftB) + bottomLeftB) -
            (topRightB + (2 * rightB) + bottomRightB);
    double verticalEnergy = (topLeftB + (2 * topB) + topRightB) -
            (bottomLeftB + (2 * bottomB) + bottomRightB);

    this.energy = Math.sqrt(Math.pow(horizontalEnergy, 2) + Math.pow(verticalEnergy, 2));
  }

  public void connect(List<List<Pixel>> pixels, int row, int col) {
    this.top = Util.getPixelAt(pixels, row - 1, col);
    this.bottom = Util.getPixelAt(pixels, row + 1, col);
    this.left = Util.getPixelAt(pixels, row, col - 1);
    this.right = Util.getPixelAt(pixels, row, col + 1);
    this.topLeft = Util.getPixelAt(pixels, row - 1, col - 1);
    this.topRight = Util.getPixelAt(pixels, row - 1, col + 1);
    this.bottomLeft = Util.getPixelAt(pixels, row + 1, col - 1);
    this.bottomRight = Util.getPixelAt(pixels, row + 1, col + 1);
  }

  public boolean connected() {
    return (this.top == null || this.top.bottom == this)
            && (this.bottom == null || this.bottom.top == this)
            && (this.left == null || this.left.right == this)
            && (this.right == null || this.right.left == this)
            && (this.topLeft == null || this.topLeft.bottomRight == this)
            && (this.topRight == null || this.topRight.bottomLeft == this)
            && (this.bottomLeft == null || this.bottomLeft.topRight == this)
            && (this.bottomRight == null || this.bottomRight.topLeft == this);
  }

  public void setSeam(int r) {
    if(r == 0) {
      this.seam = new SeamInfo(this);
    } else {
      SeamInfo minimum = Util.getMinimumWeightSeam(this.upperNeighbors());
      this.seam = new SeamInfo(this, minimum);
    }
  }

  public double getEnergy() {
    //System.out.println(energy);
    return this.energy;
  }

  public SeamInfo getSeam() {
    return this.seam;
  }

  //PRIVATE METHODS ---------------------------------------------------
  private static double calcBrightness(Color color) {
    double average = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
    return average / 255.0;
  }

  private static double getNeighborBrightness(Pixel pixel) {
    if(pixel == null) {
      return 0.0;
    } else {
      return pixel.brightness;
    }
  }

  private List<SeamInfo> upperNeighbors() {
    List<SeamInfo> upperNeighbors = new ArrayList<>();
    if(this.topLeft != null) {
      upperNeighbors.add(topLeft.seam);
    } else if(this.top != null) {
      upperNeighbors.add(top.seam);
    } else if(this.topRight != null) {
      upperNeighbors.add(topRight.seam);
    }
    return upperNeighbors;
  }

  public Color getColor() {
    return this.color;
  }
}
