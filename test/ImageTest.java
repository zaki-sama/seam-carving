import junit.framework.*;

import org.junit.Test;

import java.util.*;

import tester.*;

import static org.junit.Assert.assertEquals;

public class ImageTest extends TestCase {

  public void testMakeImage() {
    SeamCarver carver = new SeamCarver("images/balloons.jpg");
    carver.bigBang(carver.width, carver.height, 1/28.0);
  }

//  @Test
//  public void test() {
//    Pixel p1 = new Pixel(5.0);
//    Pixel p2 = new Pixel(6.0);
//    Pixel p3 = new Pixel(3.0);
//    Pixel p4 = new Pixel(8.0);
//
//    Pixel p5 = new Pixel(4.0);
//    Pixel p6 = new Pixel(1.0);
//    Pixel p7 = new Pixel(6.0);
//    Pixel p8 = new Pixel(4.0);
//
//    Pixel p9 = new Pixel(3.0);
//    Pixel p10 = new Pixel(2.0);
//    Pixel p11 = new Pixel(1.0);
//    Pixel p12 = new Pixel(3.0);
//
//    Pixel p13 = new Pixel(8.0);
//    Pixel p14 = new Pixel(6.0);
//    Pixel p15 = new Pixel(5.0);
//    Pixel p16 = new Pixel(2.0);
//
//    List<Pixel> row1 = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
//    List<Pixel> row2 = new ArrayList<>(Arrays.asList(p5, p6, p7, p8));
//    List<Pixel> row3 = new ArrayList<>(Arrays.asList(p9, p10, p11, p12));
//    List<Pixel> row4 = new ArrayList<>(Arrays.asList(p13, p14, p15, p16));
//
//    List<List<Pixel>> pixels = new ArrayList<>(Arrays.asList(row1, row2, row3, row4));
//
//    Image image = new Image(pixels);
//    image.setupSeam(true);
//
//    for(Pixel p : image.minimumSeam.trace()) {
//      System.out.println(p.getEnergy());
//    }
//  }
}
