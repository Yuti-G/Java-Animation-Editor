package cs5004.animator.model;

/**
 * This class represents an Oval. It offers all the operations mandated by the Shape interface.
 */
public class Oval extends AbstractShape {
  private double xRadius;
  private double yRadius;

  /**
   * The constructor of the Oval class.
   *
   * @param name     the name of the oval
   * @param position the current position of the oval
   * @param color    the current color of the oval
   * @param xRadius  the x radius of the oval
   * @param yRadius  the y radius of the oval
   */
  public Oval(String name, Point2D position, IColor color, double xRadius, double yRadius) {
    super(name, position, color);
    if (xRadius <= 0 || yRadius <= 0) {
      throw new IllegalArgumentException("Input cannot be negative numbers");
    }
    this.xRadius = xRadius;
    this.yRadius = yRadius;
  }

  /**
   * The second constructor of the oval.
   *
   * @param name the name of the oval
   */
  public Oval(String name) {
    super(name);
  }

  /**
   * This method gets the x radius of the oval.
   *
   * @return the x radius of the oval
   */
  public double getXRadius() {
    return xRadius;
  }

  /**
   * This method gets the y radius of the oval.
   *
   * @return the y radius of the oval
   */
  public double getYRadius() {
    return yRadius;
  }

  @Override
  public void setWidth(double width) {
    this.xRadius = width;
  }

  @Override
  public void setHeight(double height) {
    this.yRadius = height;
  }

  @Override
  public double getWidth() {
    return xRadius;
  }

  @Override
  public double getHeight() {
    return yRadius;
  }

  @Override
  public String getXNameSVG() {
    return "cx";
  }

  @Override
  public String getYNameSVG() {
    return "cy";
  }

  @Override
  public String getWidthNameSVG() {
    return "rx";
  }

  @Override
  public String getHeightNameSVG() {
    return "ry";
  }

  /**
   * The String representation of the oval.
   *
   * @return the string representation of the oval
   */
  @Override
  public String toString() {
    return "Name: " + name + "\n"
            + "Type: oval\n" + "Center: " + position.toString() + ", "
            + "X radius: " + String.format("%.1f", xRadius) + ", "
            + "Y radius: " + String.format("%.1f", yRadius) + ", "
            + "Color: " + color.toString();
  }
}
