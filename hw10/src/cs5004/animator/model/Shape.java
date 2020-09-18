package cs5004.animator.model;

/**
 * The interface contains all operation that all types of shape should support.
 */
public interface Shape {
  /**
   * This method gets the name of the shape.
   *
   * @return the name of the shape in string format.
   */
  String getName();

  /**
   * This method gets the position of the shape.
   *
   * @return the position of the shape.
   */
  Point2D getPosition();

  /**
   * This method gets the color of the shape.
   *
   * @return the color of the shape.
   */
  IColor getColor();

  /**
   * This method sets the position of the shape.
   *
   * @param position of the shape
   */
  void setPosition(Point2D position);

  /**
   * This method sets the color of the shape.
   *
   * @param color of the shape
   */
  void setColor(IColor color);

  /**
   * This method sets the width of the shape.
   *
   * @param width of the shape
   */
  void setWidth(double width);

  /**
   * This method sets the height of the shape.
   *
   * @param height of the shape
   */
  void setHeight(double height);

  /**
   * This method gets the width of the shape.
   *
   * @return the width of the shape
   */
  double getWidth();

  /**
   * This method gets the height of the shape.
   *
   * @return the height of the shape
   */
  double getHeight();

  /**
   * This method gets the x of shape for SVG usage.
   *
   * @return the x of the shape in string
   */
  String getXNameSVG();

  /**
   * This method gets the y of shape for SVG usage.
   *
   * @return the y of the shape in string
   */
  String getYNameSVG();

  /**
   * This method gets the width of shape for SVG usage.
   *
   * @return the width of the shape in string
   */
  String getWidthNameSVG();

  /**
   * This method gets the height of shape for SVG usage.
   *
   * @return the height of the shape in string
   */
  String getHeightNameSVG();

}
