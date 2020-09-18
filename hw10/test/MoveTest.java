import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.IColor;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.ShapeInWindow;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for Move class.
 */
public class MoveTest {
  private Move m1;
  private Move m2;

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
    m1 = new Move(s1, new Point2D(200, 200), new Point2D(100, 100),
            10, 20);
    m2 = new Move(s2, new Point2D(200, 200), new Point2D(300, 500),
            80, 90);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullShape() {
    new Move(null, new Point2D(200, 200), new Point2D(100, 100),
            10, 20);
  }

  @Test
  public void testToString() {
    assertEquals("Shape R moves from (200.0,200.0) to (100.0,100.0) from t=10 to t=20\n",
            m1.toString());
    assertEquals("Shape O moves from (200.0,200.0) to (300.0,500.0) from t=80 to t=90\n",
            m2.toString());
  }
}
