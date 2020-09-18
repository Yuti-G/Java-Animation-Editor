package cs5004.animator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;

/**
 * This class represents a text view.
 */
public class TextView implements View {

  @Override
  public void showView(Animation model) {
    System.out.println(model);
  }

  @Override
  public void showView(Animation model, String outputFileName) {
    try {
      // New file
      File myObj = new File(outputFileName);
      // Ignore the return because we want to overwrite the file even it is there.
      myObj.createNewFile();
      try {
        // Write to file
        FileWriter myWriter = new FileWriter(outputFileName);
        myWriter.write(String.valueOf(model));
        // Close file
        myWriter.close();
        System.out.println("Successfully wrote to the txt File.");
      } catch (IOException e) {
        // Can't write to file
        throw new IllegalStateException("Can't write to file.");
      }
    } catch (IOException e) {
      // Can't create file
      throw new IllegalStateException("File creation failed txt");
    }
  }

  @Override
  public void showView(Animation model, int speed) {
    throw new UnsupportedOperationException("text view does not support the operation");
  }

  @Override
  public void showView(Animation model, int speed, KeyboardController kc, MouseController mc) {
    throw new UnsupportedOperationException("text view does not support the operation");
  }
}
