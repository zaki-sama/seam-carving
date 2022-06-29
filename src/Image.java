import java.awt.*;
import java.util.*;
import java.util.List;

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

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void seamReset() {
    this.connectPixels();
    this.checkConnections();
    this.setEnergies();
    this.findSeams();
    this.minimumSeam = this.calcMinimum();
  }

  public void displaySeam() {
    List<Pixel> seamPixels = this.minimumSeam.trace();
    for(Pixel p:seamPixels) {
      p.setColor(Color.RED);
    }
  }

  public void removeSeam() {
    List<List<Pixel>> newImage = this.copyPixels();
    List<Pixel> seamPixels = this.minimumSeam.trace();
    for(int r = 0; r < height; r++) {
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        if(seamPixels.contains(pixel)) {
          newImage.get(r).remove(pixel);
        }
      }
    }
    this.pixels = newImage;
    this.recalculateDimensions();
  }

  private void recalculateDimensions() {
    this.height = pixels.size();
    this.width = pixels.get(0).size();
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

  private SeamInfo calcMinimum() {
    List<Pixel> lastRow = this.pixels.get(height - 1);
    Optional<Pixel> min = lastRow.stream().min(
            (o1, o2) -> (int) (o1.getSeam().getTotalWeight() - o2.getSeam().getTotalWeight()));
    return min.get().getSeam();
  }

  private void findSeams() {
    for(int r = 0; r < height; r++) {
      for(int c = 0; c < width; c++) {
        Pixel pixel = pixels.get(r).get(c);
        pixel.setSeam(r);
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
          throw new IllegalStateException("Connection incorrect");
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
    List<List<Pixel>> pixels = new LinkedList<>();
    for(int r = 0; r < height; r++) {
      List<Pixel> row = new LinkedList<>();
      for(int c = 0; c < width; c++) {
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
