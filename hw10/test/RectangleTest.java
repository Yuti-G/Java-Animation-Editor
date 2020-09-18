import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.IColor;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for Rectangle class.
 */
public class RectangleTest {
  private Rectangle r1;
  private Rectangle r2;

  @Before
  public void setUp() {
    r1 = new Rectangle("R1", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100);
    r2 = new Rectangle("R2", new Point2D(-100, 0),
            new IColor(0.0f, 0.0f, 0.0f), 10, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParams() {
    new Rectangle(null, null, null, 10, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    new Rectangle(null, null, null, -10, 100);
  }

  @Test
  public void testGetName() {
    assertEquals("R1", r1.getName());
    assertEquals("R2", r2.getName());
  }

  @Test
  public void testGetPosition() {
    assertEquals("(200.0,200.0)", r1.getPosition().toString());
    assertEquals("(-100.0,0.0)", r2.getPosition().toString());
  }

  @Test
  public void testGetColor() {
    assertEquals("(1.0,0.0,0.0)", r1.getColor().toString());
    assertEquals("(0.0,0.0,0.0)", r2.getColor().toString());
  }

  @Test
  public void testGetWidth() {
    assertEquals(50, r1.getWidth(), 0);
    assertEquals(10, r2.getWidth(), 0);
  }

  @Test
  public void testGetHeight() {
    assertEquals(100, r1.getHeight(), 0);
    assertEquals(20, r2.getHeight(), 0);
  }

  @Test
  public void testToString() {
    assertEquals("Name: R1\n"
                    + "Type: rectangle\n"
                    + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)",
            r1.toString());
    assertEquals("Name: R2\n"
                    + "Type: rectangle\n"
                    + "Min corner: (-100.0,0.0), Width: 10.0, Height: 20.0, Color: (0.0,0.0,0.0)",
            r2.toString());
  }
}
