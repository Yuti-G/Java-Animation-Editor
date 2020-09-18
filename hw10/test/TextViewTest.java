import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import cs5004.animator.model.Animation;
import cs5004.animator.model.util.AnimationReader;
import cs5004.animator.view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for TextView.
 */
public class TextViewTest {
  private Readable actions;
  private ByteArrayOutputStream textView = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(textView));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAction() {
    actions = new StringReader("Shape R triangle\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test
  public void testSmallDemo() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
            + "motion R 50 300 300 50 100 255 0  0    51  300 300 50 100 255 0  0\n"
            + "motion R 51 300 300 50 100 255 0  0    70  300 300 25 100 255 0  0\n"
            + "motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0\n"
            + "shape C ellipse\n"
            + "motion C 6  440 70 120 60 0 0 255      20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255      50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85\n"
            + "motion C 70 440 370 120 60 0 170 85    80 440 370 120 60 0 255 0\n"
            + "motion C 80 440 370 120 60 0 255 0     100 440 370 120 60 0 255 0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
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
                    + "Center: (440.0,70.0), X radius: 120.0, Y radius: 60.0, "
                    + "Color: (0.0,0.0,1.0)\n"
                    + "Appears at t=6\n"
                    + "Disappears at t=100\n"
                    + "\n"
                    + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n"
                    + "Shape R scales from Width: 50.0, Height: 100.0 to "
                    + "Width: 25.0, Height: 100.0 "
                    + "from t=51 to t=70\n"
                    + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100\n"
                    + "Shape C moves from (440.0,70.0) to (440.0,250.0) from t=20 to t=50\n"
                    + "Shape C moves from (440.0,250.0) to (440.0,370.0) from t=50 to t=70\n"
                    + "Shape C changes color from (0.0,0.0,1.0) to (0.0,0.7,0.3) "
                    + "from t=50 to t=70\n"
                    + "Shape C changes color from (0.0,0.7,0.3) to (0.0,1.0,0.0) "
                    + "from t=70 to t=80\n",
            textView.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
            + "motion R 20 300 300 50 100 255 0  0    30  200 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 100  0    50  200 200 50 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 100 255 100  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 0  0    50  200 200 25 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 50 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testUnknownMotion1() {
    actions = new StringReader("shape R rectangle\n"
            + "motion C 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testUnknownMotion2() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidTime() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0  0    5  300 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidColor() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 256 0  0    50  300 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    new TextView().showView(model);
  }
}
