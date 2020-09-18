package cs5004.animator.model;

/**
 * This is a collection of events of a shape, including Move, ChangeColor and Scale.
 */
public interface Event {
  /**
   * Returns the start time of this event.
   *
   * @return the start time of this event
   */
  int getStartTime();

  /**
   * Returns the end time of this event.
   *
   * @return the end time of this event
   */
  int getEndTime();

  /**
   * Returns the shape of this event.
   *
   * @return the shape of this event
   */
  ShapeInWindow getShape();
}
