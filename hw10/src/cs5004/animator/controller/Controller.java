package cs5004.animator.controller;

import cs5004.animator.model.Animation;

/**
 * This interface represents all operations of the controller.
 */
public interface Controller {
  /**
   * This method takes in a speed and plays the view.
   *
   * @param speed the initial speed to play the view
   */
  void playView(int speed);

  /**
   * This method handles adding model and its motions to the animation.
   *
   * @param addModel the model passed in
   */
  void handleAddClick(Animation addModel);

  /**
   * This method handles deleting shape and motions in the animation.
   *
   * @param shapeName the name of the shape.=
   */
  void handleDeleteClick(String shapeName);

  /**
   * This method handles the change of the model, reloading a new txt file for instance.
   *
   * @param newModel the newModel that passed into the animation
   */
  void handleChangeModel(Animation newModel);
}
