import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.IColor;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.ShapeInWindow;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for Scale class.
 */
public class ScaleTest {
  private Scale sc1;
  private Scale sc2;

  @Before
  public void setUp() {
    ShapeInWindow s1;
    ShapeInWindow s2;
    s1 = new ShapeInWindow(new Rectangle("R", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100),
            10, 80);
    s2 = new ShapeInWindow(new Oval("O", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100),
            50, 100);
    sc1 = new Scale(s1, 50, 100, 25, 50,
            10, 20);
    sc2 = new Scale(s2, 50, 100, 10, 50,
            80, 90);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullShape() {
    new Move(null, new Point2D(200, 200), new Point2D(100, 100),
            10, 20);
  }

  @Test
  public void testToString() {
    assertEquals("Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, "
            + "Height: 50.0 from " + "t=10 to t=20\n", sc1.toString());
    assertEquals("Shape O scales from X radius: 50.0, Y radius: 100.0 to X radius: 10.0, "
            + "Y radius: 50.0 from " + "t=80 to t=90\n", sc2.toString());
  }
}
