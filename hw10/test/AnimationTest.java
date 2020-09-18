import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.Animation;
import cs5004.animator.model.IColor;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeInWindow;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for Animation class.
 */
public class AnimationTest {
  private Animation a;

  @Before
  public void setUp() {
    Shape r;
    Shape c;
    ShapeInWindow rInWindow;
    ShapeInWindow cInWindow;
    a = new Animation();
    // create shapes
    r = a.createRectangle("R", new Point2D(200, 200),
            new IColor(1.0f, 0.0f, 0.0f), 50, 100);
    c = a.createOval("C", new Point2D(500, 100), new IColor(0.0f, 0.0f, 1.0f),
            60, 30);
    // put shapes in window
    rInWindow = a.putShapeInWindow(r, 1, 100);
    cInWindow = a.putShapeInWindow(c, 6, 100);
    // set events
    a.move(rInWindow, new Point2D(200, 200), new Point2D(300, 300),
            10, 50);
    a.move(cInWindow, new Point2D(500, 100), new Point2D(500, 400),
            20, 70);
    a.changeColor(cInWindow, new IColor(0.0f, 0.0f, 1.0f),
            new IColor(0.0f, 1.0f, 0.0f), 50, 80);
    a.scale(rInWindow, 50, 100, 25, 100,
            51, 70);
    a.move(rInWindow, new Point2D(300, 300), new Point2D(200, 200),
            70, 100);
  }

  @Test
  public void testText() {
    assertEquals("Shapes:\n"
                    + "Name: R\n"
                    + "Type: rectangle\n"
                    + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, "
                    + "Color: (1.0,0.0,0.0)\n"
                    + "Appears at t=1\n"
                    + "Disappears at t=100\n"
                    + "\n"
                    + "Name: C\n"
                    + "Type: oval\n"
                    + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, "
                    + "Color: (0.0,0.0,1.0)\n"
                    + "Appears at t=6\n"
                    + "Disappears at t=100\n"
                    + "\n"
                    + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n"
                    + "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20 to t=70\n"
                    + "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) "
                    + "from t=50 to t=80\n"
                    + "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, "
                    + "Height: 100.0 from"
                    + " t=51 to t=70\n"
                    + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100",
            a.toString());
  }
}
