package cs5004.animator.model;

/**
 * This class represents a shape in window. That is a shape with appear time and disappear time.
 */
public class ShapeInWindow {
  private int appearTime;
  private int disappearTime;
  private Shape s;

  /**
   * The constructor of ShapeInWindow takes in 3 parameters.
   *
   * @param s             the shape object to be displayed
   * @param appearTime    the appear time of the shape
   * @param disappearTime the disappear time of the shape
   */
  public ShapeInWindow(Shape s, int appearTime, int disappearTime) {
    if (s == null) {
      throw new IllegalArgumentException("shape cannot be null");
    }
    if (appearTime < 0 || disappearTime < 0 || appearTime > disappearTime) {
      throw new IllegalArgumentException("illegal time");
    }
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.s = s;
  }

  /**
   * Returns the shape object.
   *
   * @return the shape object
   */
  public Shape getShape() {
    return s;
  }

  /**
   * Returns the name of the shape.
   *
   * @return the name of the shape
   */
  public String getName() {
    return s.getName();
  }

  /**
   * Returns the appear time of the shape.
   *
   * @return the appear time of the shape
   */
  public int getAppearTime() {
    return appearTime;
  }

  /**
   * Returns the disappear time of the shape.
   *
   * @return the disappear time of the shape
   */
  public int getDisappearTime() {
    return disappearTime;
  }

  /**
   * Returns the String representation of the object.
   *
   * @return the String representation of the object
   */
  @Override
  public String toString() {
    return s.toString() + "\n"
            + "Appears at t=" + appearTime + "\n"
            + "Disappears at t=" + disappearTime + "\n";
  }
}
