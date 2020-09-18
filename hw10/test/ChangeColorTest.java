import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.ChangeColor;
import cs5004.animator.model.IColor;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.ShapeInWindow;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for ChangeColor class.
 */
public class ChangeColorTest {
  private ChangeColor c1;
  private ChangeColor c2;

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
    c1 = new ChangeColor(s1, new IColor(1.0f, 0.0f, 0.0f),
            new IColor(0.0f, 1.0f, 0.0f), 10, 20);
    c2 = new ChangeColor(s2, new IColor(1.0f, 0.0f, 0.0f),
            new IColor(1.0f, 1.0f, 0.0f), 80, 90);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullShape() {
    new Move(null, new Point2D(200, 200), new Point2D(100, 100),
            10, 20);
  }

  @Test
  public void testToString() {
    assertEquals("Shape R changes color from (1.0,0.0,0.0) to (0.0,1.0,0.0) "
            + "from t=10 to t=20\n", c1.toString());
    assertEquals("Shape O changes color from (1.0,0.0,0.0) to (1.0,1.0,0.0) "
            + "from t=80 to t=90\n", c2.toString());
  }
}
