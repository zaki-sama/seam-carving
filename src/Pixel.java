import java.awt.Color;
import java.util.*;

import javalib.worldimages.FromFileImage;

public class Pixel {
  private Color color;
  private double brightness;
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

  public Pixel(Color color) {
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

  public void setSeam(boolean vertical, int row, int col) {
    if(vertical) {
      this.updateSeam(row, this.notNullNeighbors(this.topLeft, this.top, this.topRight));
    } else {
      this.updateSeam(col, this.notNullNeighbors(this.topLeft, this.left, this.bottomLeft));
    }
  }

  private void updateSeam(int index, List<SeamInfo> neighbors) {
    if(index == 0) {
      this.seam = new SeamInfo(this);
    } else {
      SeamInfo minimum = Util.getMinimumWeightSeam(neighbors);
      this.seam = new SeamInfo(this, minimum);
    }
  }

  private List<SeamInfo> notNullNeighbors(Pixel first, Pixel second, Pixel third) {
    List<SeamInfo> neighbors = new ArrayList<>();
    if (first != null) {
      neighbors.add(first.seam);
    }
    if (second != null) {
      neighbors.add(second.seam);
    }
    if (third != null) {
      neighbors.add(third.seam);
    }
    return neighbors;
  }

  public double getEnergy() {
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
    }
    if(this.top != null) {
      upperNeighbors.add(top.seam);
    }
    if(this.topRight != null) {
      upperNeighbors.add(topRight.seam);
    }
    return upperNeighbors;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color red) {
    this.color = red;
  }
}
