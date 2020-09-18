package cs5004.animator.model;

/**
 * This class represents a change color event in an animation.
 */
public class ChangeColor extends AbstractEvent {
  private IColor startColor;
  private IColor endColor;

  /**
   * This constructor of ChangeColor takes in 5 parameters with 3 inherited from abstract class.
   *
   * @param shape      the shape (in window) of the change color event
   * @param startColor the start color of the change color event
   * @param endColor   the end color of the change color event
   * @param startTime  the start time of the change color event
   * @param endTime    the end time of the change color event
   */
  public ChangeColor(ShapeInWindow shape, IColor startColor, IColor endColor,
                     int startTime, int endTime) {
    super(shape, startTime, endTime);
    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("color cannot be null");
    }
    this.startColor = startColor;
    this.endColor = endColor;
  }

  /**
   * This method gets the end color.
   *
   * @return the end color.
   */
  public IColor getEndColor() {
    return endColor;
  }

  /**
   * This method gets the start color.
   *
   * @return the start color.
   */
  public IColor getStartColor() {
    return startColor;
  }

  /**
   * Return the string representation of the change color event.
   *
   * @return the string representation of the change color event
   */
  @Override
  public String toString() {
    return "Shape " + shape.getName() + " changes color from "
            + startColor.toString() + " to " + endColor.toString()
            + " from t=" + startTime + " to t=" + endTime + "\n";
  }
}
