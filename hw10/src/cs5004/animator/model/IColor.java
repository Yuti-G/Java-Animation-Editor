package cs5004.animator.model;

/**
 * This class represents for a color.
 */
public class IColor extends java.awt.Color {
  /**
   * Creates an opaque sRGB color with the specified red, green, and blue values in the range (0.0 -
   * 1.0).  Alpha is defaulted to 1.0.  The actual color used in rendering depends on finding the
   * best match given the color space available for a particular output device.
   *
   * @param r the red component
   * @param g the green component
   * @param b the blue component
   * @throws IllegalArgumentException if <code>r</code>, <code>g</code> or <code>b</code> are
   *                                  outside of the range 0.0 to 1.0, inclusive
   * @see #getRed
   * @see #getGreen
   * @see #getBlue
   * @see #getRGB
   */
  public IColor(float r, float g, float b) {
    super(r, g, b);
  }

  /**
   * Creates an opaque sRGB color with the specified red, green, and blue values.
   *
   * @param r the red component
   * @param g the green component
   * @param b the blue component
   */
  public IColor(int r, int g, int b) {
    super(r, g, b);
  }

  @Override
  public String toString() {
    float r = (float) getRed() / 255;
    float g = (float) getGreen() / 255;
    float b = (float) getBlue() / 255;
    return String.format("(%.1f,%.1f,%.1f)", r, g, b);
  }
}
