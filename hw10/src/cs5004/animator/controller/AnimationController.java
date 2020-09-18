package cs5004.animator.controller;

import java.util.ArrayList;
import java.util.List;

import cs5004.animator.model.Animation;
import cs5004.animator.model.Event;
import cs5004.animator.model.ShapeInWindow;
import cs5004.animator.view.PlaybackView;

/**
 * This class represents all the operations of an animation controller and it implements Controller.
 */
public class AnimationController implements Controller {
  private PlaybackView view;
  private Animation model;
  private KeyboardController kc;
  private MouseController mc;

  /**
   * The constructor for the animation controller.
   *
   * @param view  the Playback view that loaded into the animation
   * @param model the model that loaded into the animation
   */
  public AnimationController(PlaybackView view, Animation model) {
    this.view = view;
    this.model = model;
    this.kc = new KeyboardController();
    this.mc = new MouseController();
  }

  @Override
  public void playView(int speed) {
    view.showView(model, speed, kc, mc);
    kc.setView(view);
    mc.setView(view);
    mc.setModel(model);
    view.addListener(this);
  }

  @sdldfk
  public void handleAddClick(Animation addModel) {
    for (ShapeInWindow shape : addModel.getShapeList()) {
      model.getShapeList().add(shape);
    }
    for (Event e : addModel.getEventList()) {
      model.getEventList().add(e);
    }
  }

  @Override
  public void handleDeleteClick(String shapeName) {
    for (ShapeInWindow shape : model.getShapeList()) {
      if (shape.getName().equals(shapeName)) {
        model.getShapeList().remove(shape);
        break;
      }
    }
    List<Event> removeEvent = new ArrayList<>();
    for (Event e : model.getEventList()) {
      if (e.getShape().getName().equals(shapeName)) {
        removeEvent.add(e);
      }
    }
    for (Event e : removeEvent) {
      model.getEventList().remove(e);
    }
  }

  @Override
  public void handleChangeModel(Animation newModel) {
    model.getShapeList().clear();
    for (ShapeInWindow shape : newModel.getShapeList()) {
      model.getShapeList().add(shape);
    }
    model.getEventList().clear();
    for (Event e : newModel.getEventList()) {
      model.getEventList().add(e);
    }
    model.setWindowLocation(newModel.getWindowLocation());
    model.setWindowWidth(newModel.getWindowWidth());
    model.setWindowHeight(newModel.getWindowHeight());
  }

}
