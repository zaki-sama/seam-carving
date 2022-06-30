import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javalib.worldimages.*;

public class Image {
  private int height;
  private int width;
  private List<List<Pixel>> pixels;
  private SeamInfo minimumSeam;

  public Image(FromFileImage fileImage) {
    this.height = (int) fileImage.getHeight();
    this.width = (int) fileImage.getWidth();
    this.pixels = this.makeImage(fileImage);
  }

  public Image(List<List<Pixel>> pixels) {
    this.height = pixels.size();
    this.width = pixels.get(0).size();
    this.pixels = pixels;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setupSeam(boolean vertical) {
    this.connectPixels();
    this.checkConnections();
    this.setEnergies();
    this.findSeams(vertical);
    if (vertical) {
      this.minimumSeam = this.calcMinimum(this.pixels.get(height - 1));
    } else {
      this.minimumSeam = this.calcMinimum(this.lastCol());
    }
  }

  private ArrayList<Pixel> lastCol() {
    ArrayList<Pixel>lastCol = new ArrayList<>();
    int cols = this.width - 1;
    for(List<Pixel> row : this.pixels) {
      lastCol.add(row.get(cols));
    }
    return lastCol;
  }

  public void displaySeam() {
    List<Pixel> seamPixels = this.minimumSeam.trace();
    for(Pixel p:seamPixels) {
      p.setColor(Color.RED);
    }
  }

  public void removeSeam(boolean vertical) {
    List<List<Pixel>> newImage = this.copyPixels();
    List<Pixel> seamPixels = this.minimumSeam.trace();

    for (int c = 0; c < width; c++) {
      for (int r = 0; r < height; r++) {
        Pixel pixel = pixels.get(r).get(c);
        if (seamPixels.contains(pixel)) {
          if (vertical) {
            newImage.get(r).remove(pixel);
          } else {
            for (int row = r + 1; row < height; row++) {
              newImage.get(row - 1).set(c, pixels.get(row).get(c));
            }
          }
        }
      }
    }

    this.pixels = newImage;
    if(!vertical) {
      this.deleteLastRow();
    }
    this.recalculateDimensions();
  }

  private void deleteLastRow() {
    this.pixels.remove(this.pixels.size() - 1);
  }

  private void recalculateDimensions() {
    this.width = this.pixels.get(0).size();
    this.height = this.pixels.size();
  }

  private List<List<Pixel>> copyPixels() {
    List<List<Pixel>> newImage = new ArrayList<>();
    for(int r = 0; r < height; r++) {
      List<Pixel> row = new ArrayList<>();
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        row.add(pixel);
      }
      newImage.add(row);
    }
    return newImage;
  }

  private SeamInfo calcMinimum(List<Pixel> lastLine) {
    Optional<Pixel> min = lastLine.stream().min(
            (o1, o2) -> (int) (o1.getSeam().getTotalWeight() - o2.getSeam().getTotalWeight()));
    return min.get().getSeam();
  }

  private void findSeams(boolean vertical) {
    if (vertical) {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          Pixel pixel = pixels.get(r).get(c);
          pixel.setSeam(true, r, c);
        }
      }
    } else {
      for (int c = 0; c < width; c++) {
        for (int r = 0; r < height; r++) {
          Pixel pixel = pixels.get(r).get(c);
          pixel.setSeam(false, r, c);
        }
      }
    }
  }

  private void setEnergies() {
    for(int r = 0; r < height; r++) {
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        pixel.setEnergy();
      }
    }
  }

  private void checkConnections() {
    for(int r = 0; r < height; r++) {
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        if(!pixel.connected()) {
          throw new IllegalStateException("Connection incorrect at " + r + ", " + c);
        }
      }
    }
  }

  private void connectPixels() {
    for(int r = 0; r < height; r++) {
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        pixel.connect(pixels, r, c);
      }
    }
  }

  private List<List<Pixel>> makeImage(FromFileImage fileImage) {
    List<List<Pixel>> pixels = new ArrayList<>();
    for (int r = 0; r < height; r++) {
      List<Pixel> row = new ArrayList<>();
      for (int c = 0; c < width; c++) {
        row.add(new Pixel(fileImage, r, c));
      }
      pixels.add(row);
    }
    return pixels;
  }

  public Color getColorAt(int r, int c) {
    return this.pixels.get(r).get(c).getColor();
  }

}
