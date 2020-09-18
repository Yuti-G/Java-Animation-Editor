package cs5004.animator;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.Controller;
import cs5004.animator.model.Animation;
import cs5004.animator.model.Animation.Builder;
import cs5004.animator.model.util.AnimationReader;
import cs5004.animator.view.PlaybackView;
import cs5004.animator.view.VisualView;
import cs5004.animator.view.SVGView;
import cs5004.animator.view.TextView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewFactory;

/**
 * This class runs the program.
 */
public final class EasyAnimator {
  /**
   * This method runs the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    String inputFilePath = "";
    String outputFilePath = "";
    String viewType = "";
    int speed = 1;

    // Loop through argument inputs. If failed, show popup window.
    for (int i = 0; i < args.length - 1; i++) {
      try {
        switch (args[i]) {
          case "-in":
            inputFilePath = args[i + 1];
            break;
          case "-out":
            outputFilePath = args[i + 1];
            break;
          case "-view":
            viewType = args[i + 1];
            break;
          case "-speed":
            speed = Integer.parseInt(args[i + 1]);
            break;
          default:
            break;
        }
      } catch (IndexOutOfBoundsException | NumberFormatException e) {
        drawErrorDialog();
        throw new IllegalArgumentException("Input argument malformed!");
      }
    }

    // Read input file and creates animation model and view accordingly.
    FileReader fileReader;
    try {
      fileReader = new FileReader(new File(inputFilePath));
      Builder reader = new Animation.Builder();
      Animation animation = (Animation) AnimationReader.parseFile(fileReader, reader);

      View newView = ViewFactory.createView(viewType);
      if (newView instanceof TextView) {
        TextView textView = (TextView) newView;
        textView.showView(animation);
      } else if (newView instanceof SVGView) {
        SVGView svgView = (SVGView) newView;
        svgView.showView(animation, outputFilePath);
      } else if (newView instanceof VisualView) {
        VisualView visualView = (VisualView) newView;
        visualView.showView(animation, speed);
      } else {
        PlaybackView playbackView = (PlaybackView) newView;
        Controller c  = new AnimationController(playbackView, animation);
        c.playView(speed);
      }
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      drawErrorDialog();
      throw new IllegalArgumentException("File path not found!");
    }
  }

  /**
   * This method pops up warning window.
   */
  public static void drawErrorDialog() {
    JFrame frame = new JFrame("JOptionPane MessageDialog");
    JOptionPane.showMessageDialog(frame,
            "Your input argument is wrong, double check and run it again.",
            "Argument malformed",
            JOptionPane.ERROR_MESSAGE);
    System.exit(0);
  }
}