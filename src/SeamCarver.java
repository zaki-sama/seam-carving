import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

public class SeamCarver {

  public Image loadImage(String filename) {
    FromFileImage fileImage = new FromFileImage(filename);
    return new Image(fileImage);
  }
}
