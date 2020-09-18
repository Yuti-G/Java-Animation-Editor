package cs5004.animator.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import cs5004.animator.controller.Controller;
import cs5004.animator.model.Animation;
import cs5004.animator.model.ChangeColor;
import cs5004.animator.model.Event;
import cs5004.animator.model.IColor;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeInWindow;

/**
 * This class represents the panel of the animation extends JPanel.
 */
public class VisualPanel extends JPanel {
  private List<Shape> shapes;
  private TickActionListener myListener;
  private Animation model;
  private int tick = 100;
  private Timer tm;
  private boolean allowLoop = false;
  private int endTime = 0;
  private int speed;
  private List<Shape> shapesInitial;

  /**
   * The VisualPanel class is constructed with 2 parameters.
   *
   * @param model the animation model of the visual view to display
   * @param speed the speed of the visual view, the number of ticks per second
   */
  public VisualPanel(Animation model, int speed) {
    super();
    this.speed = speed;
    myListener = new TickActionListener();
    this.model = model;
    if (speed != 0) {
      tm = new Timer(1000 / (this.speed * tick), myListener);
      tm.start();
    }
    setBounds((int) model.getWindowLocation().getX(), (int) model.getWindowLocation().getY(),
            model.getWindowWidth(), model.getWindowHeight());
    // find model end time
    for (ShapeInWindow s : model.getShapeList()) {
      if (s.getDisappearTime() > endTime) {
        endTime = s.getDisappearTime();
      }
    }
    // make a copy of shapeList
    shapesInitial = new ArrayList<>();
    for (ShapeInWindow s : model.getShapeList()) {
      if (s.getShape() instanceof Rectangle) {
        shapesInitial.add(new Rectangle(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Rectangle) s.getShape()).getWidth(), ((Rectangle) s.getShape()).getHeight()));
      } else {
        shapesInitial.add(new Oval(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Oval) s.getShape()).getXRadius(), ((Oval) s.getShape()).getYRadius()));
      }
    }
  }

  /**
   * Pause the timer.
   */
  public void pause() {
    tm.stop();
  }

  /**
   * Resume the timer.
   */
  public void resume() {
    tm.start();
  }

  /**
   * This method evaluates whether the timer is running.
   *
   * @return whether the timer is running
   */
  public boolean isRunning() {
    return tm.isRunning();
  }

  /**
   * Start the timer.
   *
   * @param speed the initial speed passed in
   */
  public void start(int speed) {
    if (this.speed == 0) {
      this.speed = speed;
      if (speed != 0) {
        tm = new Timer(1000 / (this.speed * tick), myListener);
        tm.start();
      }
    }
  }

  /**
   * restart the animation by initialize the shapes, currentTick.
   *
   * @param speed the speed to restart
   */
  public void restart(int speed) {
    recover();
    this.speed = speed;
    myListener.currentTick = 0;
    tm.start();
  }

  /**
   * Recover the model to the previous state.
   */
  public void recover() {
    for (int i = 0; i < shapesInitial.size(); i++) {
      model.getShapeList().get(i).getShape().setPosition(shapesInitial.get(i).getPosition());
      model.getShapeList().get(i).getShape().setColor(shapesInitial.get(i).getColor());
      if (model.getShapeList().get(i).getShape() instanceof Rectangle) {
        (model.getShapeList().get(i).getShape()).
                setWidth(((Rectangle) shapesInitial.get(i)).getWidth());
        (model.getShapeList().get(i).getShape()).
                setHeight(((Rectangle) shapesInitial.get(i)).getHeight());
      } else {
        (model.getShapeList().get(i).getShape()).
                setWidth(((Oval) shapesInitial.get(i)).getXRadius());
        (model.getShapeList().get(i).getShape()).
                setHeight(((Oval) shapesInitial.get(i)).getYRadius());
      }
    }
  }

  /**
   * set allowLoop to true.
   */
  public void allowLoop(boolean allow) {
    allowLoop = allow;
  }

  /**
   * set speed of animation display.
   *
   * @param speed the speed to set
   */
  public void setSpeed(int speed) {
    if (speed >= 0) {
      this.speed = speed;
      try {
        tm.setDelay(1000 / (this.speed * tick));
      } catch (NullPointerException np) {
        throw new IllegalArgumentException();
      }
    }
  }

  /**
   * Add new animation model to the current model.
   *
   * @param addModel the new animation model
   * @param c        the controller to handleAddClick
   */
  public void addAnimation(Animation addModel, Controller c) {
    for (ShapeInWindow s : addModel.getShapeList()) {
      if (s.getShape() instanceof Rectangle) {
        shapesInitial.add(new Rectangle(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Rectangle) s.getShape()).getWidth(), ((Rectangle) s.getShape()).getHeight()));
      } else {
        shapesInitial.add(new Oval(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Oval) s.getShape()).getXRadius(), ((Oval) s.getShape()).getYRadius()));
      }
    }
    c.handleAddClick(addModel);
  }

  /**
   * Delete a shape from the current model.
   *
   * @param shapeName the name of the shape to delete
   * @param c         the controller
   */
  public void deleteShape(String shapeName, Controller c) {
    for (Shape s : shapesInitial) {
      if (s.getName().equals(shapeName)) {
        shapesInitial.remove(s);
        break;
      }
    }
    c.handleDeleteClick(shapeName);
    drawShapes();
    repaint();
  }

  /**
   * Delete a shape in initial list.
   *
   * @param shape shape to be deleted.
   */
  public void deleteShape(Shape shape) {
    for (Shape s : shapesInitial) {
      if (s.getName().equals(shape.getName())) {
        shapesInitial.remove(s);
        break;
      }
    }
    drawShapes();
    repaint();
  }

  /**
   * Change the current model to new model.
   *
   * @param newModel the new Model to change
   * @param c        the controller
   */
  public void changeModel(Animation newModel, Controller c) {
    shapesInitial = new ArrayList<>();
    for (ShapeInWindow s : newModel.getShapeList()) {
      if (s.getShape() instanceof Rectangle) {
        shapesInitial.add(new Rectangle(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Rectangle) s.getShape()).getWidth(), ((Rectangle) s.getShape()).getHeight()));
      } else {
        shapesInitial.add(new Oval(s.getShape().getName(),
                s.getShape().getPosition(), s.getShape().getColor(),
                ((Oval) s.getShape()).getXRadius(), ((Oval) s.getShape()).getYRadius()));
      }
    }
    c.handleChangeModel(newModel);
  }

  /**
   * add shapes with current state of this tick into shapes array list.
   */
  public void drawShapes() {
    shapes = new ArrayList<>();
    for (Event event : model.getEventList()) {
      // Move Event
      if (event instanceof Move) {
        Move m = (Move) event;
        if (tick * m.getStartTime() <= myListener.currentTick
                && myListener.currentTick <= tick * m.getEndTime()) {
          m.getShape().getShape().setPosition(new Point2D(
                  m.getStartPosition().getX()
                          * (tick * m.getEndTime() - myListener.currentTick)
                          / (tick * m.getEndTime() - tick * m.getStartTime())
                          + m.getEndPosition().getX()
                          * (myListener.currentTick - tick * m.getStartTime())
                          / (tick * m.getEndTime() - tick * m.getStartTime()),
                  m.getStartPosition().getY()
                          * (tick * m.getEndTime() - myListener.currentTick)
                          / (tick * m.getEndTime() - tick * m.getStartTime())
                          + m.getEndPosition().getY()
                          * (myListener.currentTick - tick * m.getStartTime())
                          / (tick * m.getEndTime() - tick * m.getStartTime())));
        }
      }
      // Change Color Event
      else if (event instanceof ChangeColor) {
        ChangeColor c = (ChangeColor) event;
        if (tick * c.getStartTime() <= myListener.currentTick
                && myListener.currentTick <= tick * c.getEndTime()) {
          c.getShape().getShape().setColor(new IColor(
                  (float) c.getStartColor().getRed() / 255
                          * (tick * c.getEndTime() - myListener.currentTick)
                          / (tick * c.getEndTime() - tick * c.getStartTime())
                          + (float) c.getEndColor().getRed() / 255
                          * (myListener.currentTick - tick * c.getStartTime())
                          / (tick * c.getEndTime() - tick * c.getStartTime()),
                  (float) c.getStartColor().getGreen() / 255
                          * (tick * c.getEndTime() - myListener.currentTick)
                          / (tick * c.getEndTime() - tick * c.getStartTime())
                          + (float) c.getEndColor().getGreen() / 255
                          * (myListener.currentTick - tick * c.getStartTime())
                          / (tick * c.getEndTime() - tick * c.getStartTime()),
                  (float) c.getStartColor().getBlue() / 255
                          * (tick * c.getEndTime() - myListener.currentTick)
                          / (tick * c.getEndTime() - tick * c.getStartTime())
                          + (float) c.getEndColor().getBlue() / 255
                          * (myListener.currentTick - tick * c.getStartTime())
                          / (tick * c.getEndTime() - tick * c.getStartTime())));
        }
      }
      // Scale Event
      else if (event instanceof Scale) {
        Scale s = (Scale) event;
        if (tick * s.getStartTime() <= myListener.currentTick
                && myListener.currentTick <= tick * s.getEndTime()) {
          s.getShape().getShape().setWidth(s.getStartX()
                  * (tick * s.getEndTime() - myListener.currentTick)
                  / (tick * s.getEndTime() - tick * s.getStartTime())
                  + s.getEndX()
                  * (myListener.currentTick - tick * s.getStartTime())
                  / (tick * s.getEndTime() - tick * s.getStartTime()));
          s.getShape().getShape().setHeight(s.getStartY()
                  * (tick * s.getEndTime() - myListener.currentTick)
                  / (tick * s.getEndTime() - tick * s.getStartTime())
                  + s.getEndY()
                  * (myListener.currentTick - tick * s.getStartTime())
                  / (tick * s.getEndTime() - tick * s.getStartTime()));
        }
      }
    }
    for (ShapeInWindow shape : model.getShapeList()) {
      if (tick * shape.getAppearTime() <= myListener.currentTick
              && myListener.currentTick <= tick * shape.getDisappearTime()) {
        shapes.add(shape.getShape());
      }
    }
  }

  /**
   * Paint components(shapes) into this panel.
   *
   * @param g the graphics
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      for (Shape s : shapes) {
        g.setColor(s.getColor());
        if (s instanceof Rectangle) {
          Rectangle r = (Rectangle) s;
          g.fillRect((int) (r.getPosition().getX() - model.getWindowLocation().getX()),
                  (int) (r.getPosition().getY() - model.getWindowLocation().getY()),
                  (int) r.getWidth(), (int) r.getHeight());
        } else {
          Oval o = (Oval) s;
          g.fillOval((int) (o.getPosition().getX() - model.getWindowLocation().getX()),
                  (int) (o.getPosition().getY() - model.getWindowLocation().getY()),
                  (int) o.getXRadius(), (int) o.getYRadius());
        }
      }
    }
  }

  /**
   * This class represents a tick action listener.
   */
  private class TickActionListener implements ActionListener {
    private int currentTick;

    /**
     * Construct an object and initialize currentTick as 0.
     */
    public TickActionListener() {
      currentTick = 0;
    }

    /**
     * Perform action of each tick.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      drawShapes();
      repaint();
      currentTick += 1;
      if (allowLoop) {
        if (currentTick == tick * endTime) {
          restart(speed);
        }
      }
    }
  }
}
