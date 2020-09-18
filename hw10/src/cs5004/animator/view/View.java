package cs5004.animator.view;

import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;

/**
 * This interface is a collection of methods of text view.
 */
public interface View {
  /**
   * Shows the text view of this animation in System.out.
   *
   * @param model the animation of the view
   */
  void showView(Animation model);

  /**
   * This method outputs SVG view to file.
   *
   * @param model          Animation model that contains parsed info from file.
   * @param outputFileName Path to output location.
   */
  void showView(Animation model, String outputFileName);


  /**
   * Show the visual view of the given animation model.
   *
   * @param model the animation model to display
   * @param speed the speed to display, the number of ticks per second
   */
  void showView(Animation model, int speed);

  void showView(Animation model, int speed, KeyboardController kc, MouseController mc);
}
