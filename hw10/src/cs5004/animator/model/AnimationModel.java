package cs5004.animator.model;

import java.util.List;

/**
 * This interface collects methods of a animation model.
 */
public interface AnimationModel {

  /**
   * Create a rectangle shape with 5 given parameters and return the rectangle.
   *
   * @param name     a given name of the rectangle
   * @param position a given minimum corner position of the rectangle
   * @param color    a given color of the rectangle
   * @param width    a given width of the rectangle
   * @param height   a given height of the rectangle
   * @return the rectangle
   */
  Shape createRectangle(String name, Point2D position, IColor color,
                        double width, double height);

  /**
   * Create an oval shape with 5 given parameters and return the oval.
   *
   * @param name     a given name of the oval
   * @param position a given center position of the oval
   * @param color    a given color of the oval
   * @param xRadius  a given x radius of the oval
   * @param yRadius  a given y radius of the oval
   * @return the oval
   */
  Shape createOval(String name, Point2D position, IColor color,
                   double xRadius, double yRadius);

  /**
   * Put the shape in window with given appear time and disappear time.
   *
   * @param s             the shape object to display
   * @param appearTime    the appear time of the shape
   * @param disappearTime the disappear time of the shape
   * @return the ShapeInWindow object
   */
  ShapeInWindow putShapeInWindow(Shape s, int appearTime, int disappearTime);

  /**
   * Move the shape with given start position, end position, start time and end time and add the
   * move event into event array list.
   *
   * @param shape         the shape(in window) object to move
   * @param startPosition the start position of the move event
   * @param endPosition   the end position of the move event
   * @param startTime     the start time of the move event
   * @param endTime       the end time of the move event
   */
  void move(ShapeInWindow shape, Point2D startPosition, Point2D endPosition,
            int startTime, int endTime);

  /**
   * Change color of the shape with given start color, end color, start time and end time and add
   * the move event into event array list.
   *
   * @param shape      the shape(in window) object to change color
   * @param startColor the start color of the change color event
   * @param endColor   the end color of the change color event
   * @param startTime  the start time of the change color event
   * @param endTime    the end time of the change color event
   */
  void changeColor(ShapeInWindow shape, IColor startColor, IColor endColor,
                   int startTime, int endTime);

  /**
   * Scale the shape with given start scale, end scale, start time and end time and add the scale
   * event into the event array list.
   *
   * @param shape     the shape(in window) object to scale
   * @param startX    the start x scale(width for rectangle, x radius for oval) of the scale event
   * @param startY    the start y scale(height for rectangle, y radius for oval) of the scale event
   * @param endX      the end x scale(width for rectangle, x radius for oval) of the scale event
   * @param endY      the end y scale(height for rectangle, y radius for oval) of the scale event
   * @param startTime the start time of the scale event
   * @param endTime   the end time of the scale event
   */
  void scale(ShapeInWindow shape, double startX, double startY, double endX, double endY,
             int startTime, int endTime);

  /**
   * Returns the list of ShapeInWindow.
   *
   * @return the list of shape in window
   */
  List<ShapeInWindow> getShapeList();

  /**
   * Returns the list of motion events.
   *
   * @return the list of motion events
   */
  List<Event> getEventList();

  /**
   * Returns a list of events belongs to a given shape.
   *
   * @param s the given shape
   * @return a list of events belongs to a given shape
   */
  List<Event> eventsForShape(Shape s);
}
