package cs5004.animator.view;

import javax.swing.JFrame;

import cs5004.animator.controller.Controller;
import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;
import cs5004.animator.model.Shape;

/**
 * This class represents a playback view.
 */
public class PlaybackView implements View {
  private PlaybackFrame jf;

  @Override
  public void showView(Animation model) {
    throw new UnsupportedOperationException("new view does not support the operation");
  }

  @Override
  public void showView(Animation model, String outputFileName) {
    throw new UnsupportedOperationException("new view does not support the operation");
  }

  @Override
  public void showView(Animation model, int speed) {
    throw new UnsupportedOperationException("new view does not support the operation");
  }

  @Override
  public void showView(Animation model, int speed, KeyboardController kc, MouseController mc) {
    jf = new PlaybackFrame(model, speed, kc, mc);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setVisible(true);
  }

  /**
   * Add controller to handle add animation model.
   *
   * @param c the controller
   */
  public void addListener(Controller c) {
    jf.addListener(c);
  }

  /**
   * Handle keyboard space key pressed event.
   */
  public void handleKeyBoardSpace() {
    jf.handleKeyBoardSpace();
  }

  /**
   * Handle arrow key pressed event.
   *
   * @param upKeyPressed whether up key pressed or down key pressed.
   */
  public void handleKeyBoardSpeed(boolean upKeyPressed) {
    jf.changeSpeed(upKeyPressed);
  }

  /**
   * Handle mouse click on shape event.
   *
   * @param s shape that the mouse clicks on
   */
  public void handleMouseClick(Shape s) {
    jf.handleMouseClick(s);
  }
}
