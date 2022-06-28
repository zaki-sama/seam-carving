import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageTest {

  @Test
  public void testMakeImage() {
    Image img = new SeamCarver().loadImage("images/mozaic.png");
  }
}
