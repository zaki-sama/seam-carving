import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;

public class SeamCarver extends World {

  private Image image;
  public static int height;
  public static int width;
  private int count;
  private boolean vertical;

  public SeamCarver(String filename) {
    FromFileImage file = new FromFileImage(filename);
    this.image = new Image(file);
    this.height = this.image.getHeight();
    this.width = this.image.getWidth();
    this.count = 0;
    this.vertical = true;
  }

  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(width, height);
    ComputedPixelImage img = new ComputedPixelImage(image.getWidth(), image.getHeight());
    for (int r = 0; r < image.getHeight(); r++) {
      for (int c = 0; c < image.getWidth(); c++) {
        img.setColorAt(c, r, image.getColorAt(r, c));
      }
    }
    scene.placeImageXY(img, width/2, height/2);
    return scene;
  }

  @Override
  public void onTick() {
    if((image.getWidth() > width*0.5) && (image.getHeight() > height*0.1)) {
      image.setupSeam(vertical);
      if(count % 2 == 0) {
        image.removeSeam(vertical);
      } else {
        image.displaySeam();
      }
      count++;
    }
  }

  @Override
  public void onKeyEvent(String key) {
    if(key.equals(" ")) {
      image.removeSeam(vertical);
      this.vertical = !this.vertical;
    }
  }

  public static void main(String[] args) {
    SeamCarver carver = new SeamCarver("images/balloons.jpg");
    carver.bigBang(carver.width, carver.height, 1/28.0);
  }

}
