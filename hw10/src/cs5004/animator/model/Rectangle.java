package cs5004.animator.model;

/**
 * This class represents a Rectangle. It offers all the operations mandated by the Shape interface.
 */
public class Rectangle extends AbstractShape {
  private double width;
  private double height;


  /**
   * The constructor of the Rectangle class.
   *
   * @param name     the name of the oval
   * @param position the current position of the oval
   * @param color    the current color of the oval
   * @param width    the width of the rectangle
   * @param height   the height of the rectangle
   */
  public Rectangle(String name, Point2D position, IColor color, double width, double height) {
    super(name, position, color);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Input cannot be negative numbers");
    }
    this.width = width;
    this.height = height;
  }

  /**
   * The second constructor of the rectangle.
   *
   * @param name the name of the rectangle
   */
  public Rectangle(String name) {
    super(name);
  }

  /**
   * This method gets the width of the rectangle.
   *
   * @return the width of the rectangle
   */
  @Override
  public double getWidth() {
    return width;
  }

  /**
   * This method gets the height of the rectangle.
   *
   * @return the height of the rectangle
   */
  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public void setWidth(double width) {
    this.width = width;
  }

  @Override
  public void setHeight(double height) {
    this.height = height;
  }

  @Override
  public String getXNameSVG() {
    return "x";
  }

  @Override
  public String getYNameSVG() {
    return "y";
  }

  @Override
  public String getWidthNameSVG() {
    return "width";
  }

  @Override
  public String getHeightNameSVG() {
    return "height";
  }

  /**
   * The String representation of the rectangle.
   *
   * @return the String representation of the rectangle
   */
  @Override
  public String toString() {
    return "Name: " + name + "\n"
            + "Type: rectangle\n" + "Min corner: " + position.toString() + ", "
            + "Width: " + String.format("%.1f", width) + ", "
            + "Height: " + String.format("%.1f", height) + ", "
            + "Color: " + color.toString();
  }
}
