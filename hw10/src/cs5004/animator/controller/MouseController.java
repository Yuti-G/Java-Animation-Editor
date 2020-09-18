package cs5004.animator.controller;

import cs5004.animator.model.Animation;
import cs5004.animator.model.Event;
import cs5004.animator.model.Point2D;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeInWindow;
import cs5004.animator.view.PlaybackView;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Mouse controller to capture mouse click events.
 */
public class MouseController extends MouseAdapter {

  private PlaybackView view;
  private Animation model;

  /**
   * Constructor for the MouseController.
   */
  public MouseController() {
    //not used
  }


  /**
   * This method assigns input view to self.view.
   *
   * @param view new playbackView.
   */
  public void setView(PlaybackView view) {
    this.view = view;
  }

  /**
   * This method assigns input model to self.model.
   *
   * @param model new Animation model.
   */
  public void setModel(Animation model) {
    this.model = model;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    deleteShapeInPosition(e.getPoint());
  }

  /**
   * Delete shape in window if mouse clicks on a shape.
   *
   * @param pressLocation the point location of the object the mouse clicks on
   */
  public void deleteShapeInPosition(Point pressLocation) {
    ShapeInWindow removedShape = null;

    Collections.reverse(model.getShapeList());
    for (ShapeInWindow siw : model.getShapeList()) {
      Shape s = siw.getShape();
      Point2D upperLeft = s.getPosition();
      Point2D lowerRight = new Point2D(upperLeft.getX() + s.getWidth(),
              upperLeft.getY() + s.getHeight());


      if (pressLocation.x >= upperLeft.getX() && pressLocation.y >= upperLeft.getY()
              && pressLocation.x <= lowerRight.getX()
              && pressLocation.y <= lowerRight.getY()) {
        removedShape = siw;
        break;
      }
    }
    Collections.reverse(model.getShapeList());
    model.getShapeList().remove(removedShape);
    ArrayList<Event> eventsToDelete = new ArrayList<>();

    if (removedShape != null) {
      for (Event event : model.getEventList()) {
        if (event.getShape().getName().equals(removedShape.getName())) {
          eventsToDelete.add(event);
        }
      }
      for (Event e : eventsToDelete) {
        model.getEventList().remove(e);
      }
      view.handleMouseClick(removedShape.getShape());
    }
  }
}
