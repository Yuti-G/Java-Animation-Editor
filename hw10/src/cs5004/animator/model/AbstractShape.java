package cs5004.animator.model;

/**
 * This abstract class represents shape.
 */
public abstract class AbstractShape implements Shape {
  String name;
  Point2D position; // corner for rectangle, center for oval
  IColor color;

  /**
   * This abstract shape class is constructed with 3 parameters.
   *
   * @param name     the name of the shape
   * @param position the position of the shape(min corner for rectangle, center for oval)
   * @param color    the color of the shape
   */
  public AbstractShape(String name, Point2D position, IColor color) {
    if (name == null || position == null || color == null) {
      throw new IllegalArgumentException("input null object");
    }
    this.name = name;
    this.position = position;
    this.color = color;
  }

  /**
   * The empty constructor of the abstract shape.
   *
   * @param name the name of the shape
   */
  public AbstractShape(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Point2D getPosition() {
    return position;
  }

  @Override
  public void setPosition(Point2D position) {
    this.position = position;
  }

  @Override
  public IColor getColor() {
    return color;
  }

  @Override
  public void setColor(IColor color) {
    this.color = color;
  }
}
