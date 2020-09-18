package cs5004.animator.view;

import javax.swing.JFrame;

import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;

/**
 * This class represents a visual view.
 */
public class VisualView implements View {

  @Override
  public void showView(Animation model) {
    throw new UnsupportedOperationException("visual view does not support the operation");
  }

  @Override
  public void showView(Animation model, String outputFileName) {
    throw new UnsupportedOperationException("visual view does not support the operation");
  }

  @Override
  public void showView(Animation model, int speed) {
    VisualPanel panel = new VisualPanel(model, speed);
    JFrame jf;
    jf = new JFrame();

    jf.add(panel);

    jf.setTitle("Visual View");
    jf.setSize(600, 600);
    jf.setBounds((int) model.getWindowLocation().getX(), (int) model.getWindowLocation().getY(),
            model.getWindowWidth(), model.getWindowHeight());
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setVisible(true);
  }

  @Override
  public void showView(Animation model, int speed, KeyboardController kc, MouseController mc) {
    throw new UnsupportedOperationException("visual view does not support the operation");
  }
}
