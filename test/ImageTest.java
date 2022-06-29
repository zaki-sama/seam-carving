import junit.framework.*;

import org.junit.Test;

import tester.*;

import static org.junit.Assert.assertEquals;

public class ImageTest extends TestCase {

  public void testMakeImage() {
    SeamCarver carver = new SeamCarver("images/balloons.jpg");
    carver.bigBang(carver.width, carver.height, 1/28.0);
  }

}
