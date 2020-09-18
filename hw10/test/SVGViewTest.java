import org.junit.Test;

import java.io.StringReader;

import cs5004.animator.model.Animation;
import cs5004.animator.model.util.AnimationReader;
import cs5004.animator.view.SVGView;

import static org.junit.Assert.assertEquals;

/**
 * This junit test is for SVGView.
 */
public class SVGViewTest {
  private Readable actions;
  private Appendable svgView;

  @Test(expected = IllegalStateException.class)
  public void testInvalidAction() {
    actions = new StringReader("Shape R triangle\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test
  public void testSmallDemo() {
    actions = new StringReader("canvas 200 70 360 360\n"
            + "shape R rectangle\n"
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
    svgView = new SVGView().getSb(model);
    assertEquals("<svg viewBox=\"200 70 360 360\" version=\"1.1\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
                    + "     <animate attributeType=\"xml\" begin=\"100ms\" dur=\"9900ms\" "
                    + "attributeName=\"visibility\" to=\"visible\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" "
                    + "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" "
                    + "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"5100ms\" dur=\"1900ms\" "
                    + "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" "
                    + "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"7000ms\" dur=\"3000ms\" "
                    + "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
                    + "</rect>\n"
                    + "<ellipse id=\"C\" cx=\"440.0\" cy=\"70.0\" rx=\"60.0\" ry=\"30.0\" "
                    + "fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n"
                    + "     <animate attributeType=\"xml\" begin=\"600ms\" dur=\"9400ms\" "
                    + "attributeName=\"visibility\" to=\"visible\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"2000ms\" dur=\"3000ms\" "
                    + "attributeName=\"cy\" from=\"70.0\" to=\"250.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" "
                    + "attributeName=\"cy\" from=\"250.0\" to=\"370.0\" fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"2000ms\" "
                    + "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" "
                    + "fill=\"freeze\" />\n"
                    + "     <animate attributeType=\"xml\" begin=\"7000ms\" dur=\"1000ms\" "
                    + "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" "
                    + "fill=\"freeze\" />\n"
                    + "</ellipse>\n"
                    + "</svg>\n",
            svgView.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
            + "motion R 20 300 300 50 100 255 0  0    30  200 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 100  0    50  200 200 50 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 100 255 100  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleConsistence() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n"
            + "motion R 10 200 200 50 100 255 0  0    50  200 200 25 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 50 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testUnknownMotion1() {
    actions = new StringReader("shape R rectangle\n"
            + "motion C 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testUnknownMotion2() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0\n"
            + "motion R 20 200 200 50 100 255 0  0    30  200 200 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidTime() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0  0    5  300 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidColor() {
    actions = new StringReader("shape R rectangle\n"
            + "motion R 10 200 200 50 100 256 0  0    50  300 300 50 100 255 0  0\n");
    Animation.Builder reader = new Animation.Builder();
    Animation model = (Animation) AnimationReader.parseFile(actions, reader);
    svgView = new SVGView().getSb(model);
  }
}
