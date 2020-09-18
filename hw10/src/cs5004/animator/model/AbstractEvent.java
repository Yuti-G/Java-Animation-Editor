package cs5004.animator.model;

/**
 * This is an abstract class of event.
 */
public abstract class AbstractEvent implements Event {
  ShapeInWindow shape;
  int startTime;
  int endTime;

  /**
   * This constructor of AbstractEvent takes in 3 parameters.
   *
   * @param shape     the shape (in window) of the event
   * @param startTime the start time of the event
   * @param endTime   the end time of the event
   */
  public AbstractEvent(ShapeInWindow shape, int startTime, int endTime) {
    if (shape == null) {
      throw new IllegalArgumentException("shape cannot be null");
    }
    if (startTime < 0 || endTime < 0 || startTime > endTime) {
      throw new IllegalArgumentException("illegal time");
    }
    this.shape = shape;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public ShapeInWindow getShape() {
    return shape;
  }
}
