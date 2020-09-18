package cs5004.animator.model;

import cs5004.animator.model.util.AnimationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents an Animation, including a list of shapes and a list of events.
 */
public final class Animation implements AnimationModel {
  private List<ShapeInWindow> shapeList;
  private List<Event> eventList;
  private static Point2D windowLocation;
  private static int windowWidth;
  private static int windowHeight;

  /**
   * The constructor of the animation initialises two array lists.
   */
  public Animation() {
    shapeList = new ArrayList<>();
    eventList = new ArrayList<>();
  }

  /**
   * This constructs an Animation object with 2 given parameters.
   *
   * @param shapes the list of shapes in window
   * @param events the list of events
   */
  public Animation(List<ShapeInWindow> shapes, List<Event> events) {
    this.shapeList = shapes;
    this.eventList = events;
  }

  @Override
  public List<ShapeInWindow> getShapeList() {
    return shapeList;
  }

  @Override
  public List<Event> getEventList() {
    return eventList;
  }

  /**
   * Returns the window width of the model.
   *
   * @return the window width of the model
   */
  public static int getWindowWidth() {
    return windowWidth;
  }

  /**
   * Sets the windowWidth of the model.
   *
   * @param windowWidth the windowHeight
   */
  public void setWindowWidth(int windowWidth) {
    this.windowWidth = windowWidth;
  }

  /**
   * Returns the windowHeight of the model.
   *
   * @return the windowHeight
   */
  public static int getWindowHeight() {
    return windowHeight;
  }

  /**
   * Sets the windowHeight of the model.
   *
   * @param windowHeight the windowHeight
   */
  public void setWindowHeight(int windowHeight) {
    this.windowHeight = windowHeight;
  }

  /**
   * Returns the windowLocation of the model.
   *
   * @return the windowLocation
   */
  public static Point2D getWindowLocation() {
    return windowLocation;
  }

  /**
   * Sets the windowLocation of the model.
   *
   * @param windowLocation the windowLocation
   */
  public void setWindowLocation(Point2D windowLocation) {
    this.windowLocation = windowLocation;
  }

  @Override
  public List<Event> eventsForShape(Shape s) {
    return this.eventList.stream()
            .filter(event -> event.getShape().getName().equals(s.getName()))
            .collect(Collectors.toList());
  }

  @Override
  public Shape createRectangle(String name, Point2D position, IColor color,
                               double width, double height) {
    return new Rectangle(name, position, color, width, height);
  }

  @Override
  public Shape createOval(String name, Point2D position, IColor color,
                          double xRadius, double yRadius) {
    return new Oval(name, position, color, xRadius, yRadius);
  }

  @Override
  public ShapeInWindow putShapeInWindow(Shape s, int appearTime, int disappearTime) {
    ShapeInWindow shape = new ShapeInWindow(s, appearTime, disappearTime);
    shapeList.add(shape);
    return shape;
  }

  @Override
  public void move(ShapeInWindow shape, Point2D startPosition, Point2D endPosition,
                   int startTime, int endTime) {
    eventList.add(new Move(shape, startPosition, endPosition, startTime, endTime));
  }

  @Override
  public void changeColor(ShapeInWindow shape, IColor startColor, IColor endColor,
                          int startTime, int endTime) {
    eventList.add(new ChangeColor(shape, startColor, endColor, startTime, endTime));
  }

  @Override
  public void scale(ShapeInWindow shape, double startX, double startY, double endX, double endY,
                    int startTime, int endTime) {
    eventList.add(new Scale(shape, startX, startY, endX, endY, startTime, endTime));
  }

  /**
   * Returns the string representation of the animation.
   *
   * @return the string representation of the animation.
   */
  @Override
  public String toString() {
    String s = "Shapes:\n";
    for (ShapeInWindow shape : shapeList) {
      s = s + shape.toString() + "\n";
    }
    for (Event e : eventList) {
      s = s + e.toString();
    }
    return s.substring(0, s.length() - 1);
  }


  // File parser.

  /**
   * This class is a builder to build animation model from file.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    private List<Shape> shapeList = new ArrayList<>();
    private List<ShapeInWindow> shapeInWindowList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();

    @Override
    public AnimationModel build() {
      return new Animation(shapeInWindowList, eventList);
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      windowLocation = new Point2D(x, y);
      windowWidth = width;
      windowHeight = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      // Add more Shapes depends on the animation.
      switch (type.toLowerCase()) {
        case "rectangle":
          this.shapeList.add(new Rectangle(name));
          break;
        case "ellipse":
          this.shapeList.add(new Oval(name));
          break;
        default:
          throw new IllegalArgumentException("No such class " + type);
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                      int h1, int r1, int g1, int b1, int t2,
                                                      int x2, int y2, int w2, int h2, int r2,
                                                      int g2, int b2) {
      // if every parameter is the same, must be an appear or a disappear event.
      Shape foundShape = findNameInShapeList(name);
      if (foundShape == null) {
        throw new IllegalStateException("Shape has not been read from file but have an motion.");
      }

      ShapeInWindow foundSIW = findNameInShapeInWindowList(foundShape.getName());
      if (foundSIW == null) {
        if (x1 == x2 && y1 == y2 && w1 == w2 && h1 == h2 && r1 == r2 && b1 == b2 && g1 == g2) {
          IColor startColor = new IColor(r1, g1, b1);
          Point2D startPosition = new Point2D(x1, y1);
          foundShape.setColor(startColor);
          foundShape.setPosition(startPosition);
          foundShape.setHeight(h1);
          foundShape.setWidth(w1);
          shapeInWindowList.add(new ShapeInWindow(foundShape, t1, t2));
        } else {
          throw new IllegalStateException("Shape has not been read from file but have an motion.");
        }
      } else {
        if (t1 < foundSIW.getAppearTime()) {
          shapeInWindowList.remove(foundSIW);
          shapeInWindowList.add(new ShapeInWindow(foundShape, t1, foundSIW.getDisappearTime()));
        }
        if (t2 > foundSIW.getDisappearTime()) {
          shapeInWindowList.remove(foundSIW);
          shapeInWindowList.add(new ShapeInWindow(foundShape, foundSIW.getAppearTime(), t2));
        }
      }

      if (x1 != x2 || y1 != y2) {
        // Check existence of same motion during overlapping time intervals
        for (Event e : eventList) {
          if (e instanceof Move && e.getShape().getName().equals(name)) {
            // overlap
            if (!(e.getEndTime() <= t1 || t2 <= e.getStartTime())) {
              throw new IllegalArgumentException("Exist different Move "
                      + "event during overlapping time");
            }
          }
        }
        eventList.add(new Move(foundSIW, new Point2D(x1, y1), new Point2D(x2, y2), t1, t2));
      }
      if (w1 != w2 || h1 != h2) {
        // Check existence of same motion during overlapping time intervals
        for (Event e : eventList) {
          if (e instanceof Scale && e.getShape().getName().equals(name)) {
            // overlap
            if (!(e.getEndTime() <= t1 || t2 <= e.getStartTime())) {
              throw new IllegalArgumentException("Exist different Scale "
                      + "event during overlapping time");
            }
          }
        }
        eventList.add(new Scale(foundSIW, w1, h1, w2, h2, t1, t2));
      }
      if (g1 != g2 || b1 != b2 || r1 != r2) {
        // Check existence of same motion during overlapping time intervals
        for (Event e : eventList) {
          if (e instanceof ChangeColor && e.getShape().getName().equals(name)) {
            // overlap
            if (!(e.getEndTime() <= t1 || t2 <= e.getStartTime())) {
              throw new IllegalArgumentException("Exist different ChangeColor "
                      + "event during overlapping time");
            }
          }
        }
        eventList.add(new ChangeColor(foundSIW, new IColor(r1, g1, b1), new IColor(r2, g2, b2), t1,
                t2));
      }
      return this;
    }

    /**
     * Find ShapeInWindow objects in list via name.
     *
     * @param name the name of the shape
     * @return the object if found, otherwise return null
     */
    public ShapeInWindow findNameInShapeInWindowList(String name) {
      return this.shapeInWindowList.stream().filter(sip -> sip.getName().equals(name)).findFirst()
              .orElse(null);
    }

    /**
     * Find Shape objects in list via name.
     *
     * @param name the name of the shape
     * @return the object if found, otherwise return null
     */
    public Shape findNameInShapeList(String name) {
      return this.shapeList.stream().filter(s -> s.getName().equals(name)).findFirst()
              .orElse(null);
    }
  }
}
