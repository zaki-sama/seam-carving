import java.util.*;

public class Model {
  Map<String, Image> images;
  Map<String, List<List<Pixel>>> backups;

  public Model() {
    this.images = new HashMap<>();
    this.backups = new HashMap<>();
  }

  public Image getImageCopy(String name) {
    if (this.images.containsKey(name)) {
      return this.images.get(name).getCopy();
    } else {
      throw new IllegalArgumentException("Image " + name + " does not exist");
    }
  }

  public void loadImage(List<List<Pixel>> pixels, String name) {
    this.images.put(name, new Image(pixels));
    this.backups.put(name, pixels);
  }

  public void setupSeam(String name, boolean vertical) {
    if(this.images.containsKey(name)) {
      this.images.get(name).setupSeam(vertical);
    }
  }

  public void carveSeam(String name, boolean vertical) {
    if(this.images.containsKey(name)) {
      this.images.get(name).removeSeam(vertical);
    }
  }

  public void drawSeam(String name) {
    if(this.images.containsKey(name)) {
      this.images.get(name).displaySeam();
    }
  }
}
