package cs5004.animator.model;

/**
 * This class represents a move event in an animation.
 */
public class Move extends AbstractEvent {
  private Point2D startPosition;
  private Point2D endPosition;

  /**
   * The constructor of Move takes in 5 parameters, with three inherited from abstract class.
   *
   * @param shape         the shape (in window) of the move event
   * @param startPosition the start position of the move
   * @param endPosition   the end position of the move
   * @param startTime     the start time of the move
   * @param endTime       the end time of the move
   */
  public Move(ShapeInWindow shape, Point2D startPosition, Point2D endPosition,
              int startTime, int endTime) {
    super(shape, startTime, endTime);
    if (startPosition == null || endPosition == null) {
      throw new IllegalArgumentException("position cannot be null");
    }
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  /**
   * Returns the string representation of the move.
   *
   * @return the string representation of the move
   */
  @Override
  public String toString() {
    return "Shape " + shape.getName() + " moves from "
            + startPosition + " to " + endPosition
            + " from t=" + startTime + " to t=" + endTime + "\n";
  }

  /**
   * This method gets the start position.
   *
   * @return the start position
   */
  public Point2D getStartPosition() {
    return startPosition;
  }

  /**
   * This method gets the end position.
   *
   * @return the end position
   */
  public Point2D getEndPosition() {
    return endPosition;
  }
}
