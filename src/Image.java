import java.util.*;
import java.util.function.*;

import javalib.worldimages.*;

public class Image {
  private final int height;
  private final int width;
  private final List<List<Pixel>> pixels;

  public Image(FromFileImage fileImage) {
    this.height = (int) fileImage.getHeight();
    this.width = (int) fileImage.getWidth();
    this.pixels = this.makeImage(fileImage);
    this.connectPixels();
    this.checkConnections();
    this.setEnergies();
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
}
