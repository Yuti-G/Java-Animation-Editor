import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.IColor;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for Oval class.
 */
public class OvalTest {
  private Oval o1;
  private Oval o2;

  @Before
  public void setUp() {
    o1 = new Oval("O1", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100);
    o2 = new Oval("O2", new Point2D(-100, 0),
            new IColor(0.0f, 0.0f, 0.0f), 10, 20);
  }

  @Test
  public void testGetName() {
    assertEquals("O1", o1.getName());
    assertEquals("O2", o2.getName());
  }

  @Test
  public void testGetPosition() {
    assertEquals("(200.0,200.0)", o1.getPosition().toString());
    assertEquals("(-100.0,0.0)", o2.getPosition().toString());
  }

  @Test
  public void testGetColor() {
    assertEquals("(1.0,0.0,0.0)", o1.getColor().toString());
    assertEquals("(0.0,0.0,0.0)", o2.getColor().toString());
  }

  @Test
  public void testGetWidth() {
    assertEquals(50, o1.getXRadius(), 0);
    assertEquals(10, o2.getXRadius(), 0);
  }

  @Test
  public void testGetHeight() {
    assertEquals(100, o1.getYRadius(), 0);
    assertEquals(20, o2.getYRadius(), 0);
  }

  @Test
  public void testToString() {
    assertEquals("Name: O1\n"
                    + "Type: oval\n"
                    + "Center: (200.0,200.0), X radius: 50.0, Y radius: 100.0, "
                    + "Color: (1.0,0.0,0.0)",
            o1.toString());
    assertEquals("Name: O2\n"
                    + "Type: oval\n"
                    + "Center: (-100.0,0.0), X radius: 10.0, Y radius: 20.0, Color: (0.0,0.0,0.0)",
            o2.toString());
  }
}
