import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Image {
//  private int height;
//  private int width;
  private List<List<Pixel>> pixels;
  private SeamInfo minimumSeam;

  public Image(List<List<Pixel>> pixels) {
//    this.height = pixels.size();
//    this.width = pixels.get(0).size();
    this.pixels = pixels;
  }

  public int getHeight() {
    return pixels.size();
  }

  public int getWidth() {
    return pixels.get(0).size();
  }

  public void setupSeam(boolean vertical) {
    this.connectPixels();
    this.checkConnections();
    this.setEnergies();
    this.findSeams(vertical);
    if (vertical) {
      this.minimumSeam = this.calcMinimum(this.pixels.get(this.getHeight() - 1));
    } else {
      this.minimumSeam = this.calcMinimum(this.lastCol());
    }
  }

  private ArrayList<Pixel> lastCol() {
    ArrayList<Pixel>lastCol = new ArrayList<>();
    int cols = this.getWidth() - 1;
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

    if(this.minimumSeam != null) {
      List<Pixel> seamPixels = this.minimumSeam.trace();

      for (int c = 0; c < getWidth(); c++) {
        for (int r = 0; r < getHeight(); r++) {
          Pixel pixel = pixels.get(r).get(c);
          if (seamPixels.contains(pixel)) {
            if (vertical) {
              newImage.get(r).remove(pixel);
            } else {
              for (int row = r + 1; row < getHeight(); row++) {
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
    }
  }

  private void deleteLastRow() {
    this.pixels.remove(this.pixels.size() - 1);
  }

  private List<List<Pixel>> copyPixels() {

    List<List<Pixel>> newImage = new ArrayList<>();
    for(List<Pixel> list : this.pixels) {
      List<Pixel> row = new ArrayList<>();
      for(Pixel p : list) {
        row.add(p);
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
      for (int r = 0; r < getHeight(); r++) {
        for (int c = 0; c < getWidth(); c++) {
          Pixel pixel = pixels.get(r).get(c);
          pixel.setSeam(true, r, c);
        }
      }
    } else {
      for (int c = 0; c < getWidth(); c++) {
        for (int r = 0; r < getHeight(); r++) {
          Pixel pixel = pixels.get(r).get(c);
          pixel.setSeam(false, r, c);
        }
      }
    }
  }

  private void setEnergies() {
    for(int r = 0; r < getHeight(); r++) {
      for(int c = 0; c < getWidth(); c++) {
        Pixel pixel = pixels.get(r).get(c);
        pixel.setEnergy();
      }
    }
  }

  private void checkConnections() {
    for(int r = 0; r < getHeight(); r++) {
      for(int c = 0; c < getWidth(); c++) {
        Pixel pixel = pixels.get(r).get(c);
        if(!pixel.connected()) {
          throw new IllegalStateException("Connection incorrect at " + r + ", " + c);
        }
      }
    }
  }

  private void connectPixels() {
    for(int r = 0; r < getHeight(); r++) {
      for(int c = 0; c < getWidth(); c++) {
        Pixel pixel = pixels.get(r).get(c);
        pixel.connect(pixels, r, c);
      }
    }
  }

  public Color getColorAt(int r, int c) {
    return this.pixels.get(r).get(c).getColor();
  }

  public Image getCopy() {
    return new Image(this.copyPixels());
  }


  public boolean containsRed() {
    for(List<Pixel> row : this.pixels) {
      for(Pixel p : row) {
        if(p.getColor() == Color.red) {
          return true;
        }
      }
    }
    return false;
  }
}
