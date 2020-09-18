package cs5004.animator.model;

/**
 * This class represents a scale event in an animation.
 */
public class Scale extends AbstractEvent {
  private double startX;
  private double startY;
  private double endX;
  private double endY;

  /**
   * This constructor of Scale takes in 7 parameters, with 3 inherited from abstract class.
   *
   * @param shape     the shape (in window) of the scale event
   * @param startX    the start width for rectangle(x radius for oval) of the scale event
   * @param startY    the start height for oval(y radius for oval) of the scale event
   * @param endX      the end width for rectangle(x radius for oval) of the scale event
   * @param endY      the end height for the rectangle(y radius for oval) of the scale event
   * @param startTime the start time of the scale event
   * @param endTime   the end time of the scale event
   */
  public Scale(ShapeInWindow shape, double startX, double startY, double endX, double endY,
               int startTime, int endTime) {
    super(shape, startTime, endTime);
    if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
      throw new IllegalArgumentException("scales cannot be negative");
    }
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
  }

  public double getEndX() {
    return endX;
  }

  public double getEndY() {
    return endY;
  }

  public double getStartX() {
    return startX;
  }

  public double getStartY() {
    return startY;
  }

  /**
   * The String representation of the scale event. A
   *
   * @return the String representation of the scale event
   */
  @Override
  public String toString() {
    if (shape.getShape() instanceof Rectangle) {
      return "Shape " + shape.getName() + " scales from "
              + "Width: " + String.format("%.1f", startX)
              + ", Height: " + String.format("%.1f", startY)
              + " to Width: " + String.format("%.1f", endX)
              + ", Height: " + String.format("%.1f", endY)
              + " from t=" + startTime + " to t=" + endTime + "\n";
    } else { //Oval
      return "Shape " + shape.getName() + " scales from "
              + "X radius: " + String.format("%.1f", startX)
              + ", Y radius: " + String.format("%.1f", startY)
              + " to X radius: " + String.format("%.1f", endX)
              + ", Y radius: " + String.format("%.1f", endY)
              + " from t=" + startTime + " to t=" + endTime + "\n";
    }
  }
}
