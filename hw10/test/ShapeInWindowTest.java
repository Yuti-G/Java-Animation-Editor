import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.IColor;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.ShapeInWindow;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for ShapeInWindow class.
 */
public class ShapeInWindowTest {
  private Rectangle r;
  private Oval o;
  private ShapeInWindow s1;
  private ShapeInWindow s2;

  @Before
  public void setUp() {
    r = new Rectangle("R", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100);
    o = new Oval("O", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100);
    s1 = new ShapeInWindow(r, 10, 80);
    s2 = new ShapeInWindow(o, 50, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullShape() {
    new ShapeInWindow(null, 10, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTime() {
    new ShapeInWindow(r, -10, 20);
  }

  @Test
  public void testGetShape() {
    assertEquals(r, s1.getShape());
    assertEquals(o, s2.getShape());
  }

  @Test
  public void testGetName() {
    assertEquals("R", s1.getName());
    assertEquals("O", s2.getName());
  }

  @Test
  public void testGetAppearTime() {
    assertEquals(10, s1.getAppearTime());
    assertEquals(50, s2.getAppearTime());
  }

  @Test
  public void testGetDisappearTime() {
    assertEquals(80, s1.getDisappearTime());
    assertEquals(100, s2.getDisappearTime());
  }

  @Test
  public void testToString() {
    assertEquals("Name: R\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=10\n"
            + "Disappears at t=80\n", s1.toString());
    assertEquals("Name: O\n"
            + "Type: oval\n"
            + "Center: (200.0,200.0), X radius: 50.0, Y radius: 100.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=50\n"
            + "Disappears at t=100\n", s2.toString());
  }
}
