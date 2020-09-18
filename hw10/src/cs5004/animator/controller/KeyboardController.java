package cs5004.animator.controller;

import cs5004.animator.view.PlaybackView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard controller to listen keyboard events. Space for: start / resume / pause. Up / Down arrow
 * key for speed change.
 */
public class KeyboardController implements KeyListener {

  private PlaybackView view;

  /**
   * Constructor for the KeyboardController.
   */
  public KeyboardController() {
    // empty constructor.
  }

  /**
   * This method assigns input view to self.view.
   *
   * @param view new playbackView.
   */
  public void setView(PlaybackView view) {
    this.view = view;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //not used.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    // Space for start / pause / resume
    if (key == KeyEvent.VK_SPACE) {
      view.handleKeyBoardSpace();
    }
    // Up for speed ++
    if (key == KeyEvent.VK_UP) {
      view.handleKeyBoardSpeed(true);
    }
    // Down for speed --
    if (key == KeyEvent.VK_DOWN) {
      view.handleKeyBoardSpeed(false);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //not used
  }
}
