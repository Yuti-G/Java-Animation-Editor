package cs5004.animator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;
import cs5004.animator.model.ChangeColor;
import cs5004.animator.model.Event;
import cs5004.animator.model.IColor;
import cs5004.animator.model.Move;
import cs5004.animator.model.Oval;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeInWindow;

/**
 * This class constructs a svg view from input animation and output to another file.
 */
public class SVGView implements View {

  private StringBuilder sb = new StringBuilder();

  @Override
  public void showView(Animation model) {
    throw new UnsupportedOperationException("svg view does not support the operation");
  }

  @Override
  public void showView(Animation model, String outputFileName) {
    // Fixed header
    sb.append("<svg viewBox=\"" + (int) model.getWindowLocation().getX() + " "
            + (int) model.getWindowLocation().getY() + " " + model.getWindowWidth() + " "
            + model.getWindowHeight() + "\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n");
    // Get svg text for each shape
    for (ShapeInWindow s : model.getShapeList()) {
      drawShape(s, model);
    }
    // Close tag
    sb.append("</svg>\n");

    // Write to output file.
    this.writeToFile(outputFileName);
  }

  @Override
  public void showView(Animation model, int speed) {
    throw new UnsupportedOperationException("svg view does not support the operation");
  }

  @Override
  public void showView(Animation model, int speed, KeyboardController kc, MouseController mc) {
    throw new UnsupportedOperationException("svg view does not support the operation");
  }

  private void writeToFile(String outFile) {
    try {
      // New file
      File myObj = new File(outFile);
      // Ignore the return because we want to overwrite the file even it is there.
      myObj.createNewFile();
      try {
        // Write to file
        FileWriter myWriter = new FileWriter(outFile);
        myWriter.write(String.valueOf(this.sb));
        // Close file
        myWriter.close();
        System.out.println("Successfully wrote to the SVG File.");
      } catch (IOException e) {
        // Can't write to file
        throw new IllegalStateException("Can't write to file.");
      }
    } catch (IOException e) {
      // Can't create file
      throw new IllegalStateException("File creation failed SVG");
    }
  }

  private void drawShape(ShapeInWindow s, Animation model) {
    // draw shape on svg.
    Shape shape = s.getShape();
    switch (shape.getClass().getName()) {
      case "cs5004.animator.model.Rectangle":
        rectString(s, model);
        break;
      case "cs5004.animator.model.Oval":
        ovalString(s, model);
        break;
      default:
        throw new IllegalArgumentException("Shape not implemented");
    }
  }

  private void rectString(ShapeInWindow shape, Animation model) {
    // draw rectangle on svg.
    Shape s = shape.getShape();
    Rectangle rect = (Rectangle) s;
    sb.append("<rect id=\"" + rect.getName() + "\" x=\"" + rect.getPosition().getX() + "\" y=\""
            + rect.getPosition().getY() + "\" width=\"" + rect.getWidth() + "\" height=\""
            + rect.getHeight() + "\" fill=\"rgb(" + rect.getColor().getRed() + ","
            + rect.getColor().getGreen() + "," + rect.getColor().getBlue() + ")\" "
            + "visibility=\"hidden\">\n");
    sb.append("     <animate attributeType=\"xml\" begin=\"" + shape.getAppearTime() * 100
            + "ms\" dur=\"" + (shape.getDisappearTime() * 100 - shape.getAppearTime() * 100)
            + "ms\" " + "attributeName=\"visibility\" to=\"visible\" />\n");
    for (Event e : model.eventsForShape(s)) {
      animationString(s, e);
    }
    sb.append("</rect>\n");
  }

  private void ovalString(ShapeInWindow shape, Animation model) {
    // draw oval on svg.
    Shape s = shape.getShape();
    Oval rect = (Oval) s;
    sb.append("<ellipse id=\"" + rect.getName() + "\" cx=\"" + rect.getPosition().getX() + "\" "
            + "cy=\"" + rect.getPosition().getY() + "\" rx=\"" + rect.getXRadius() / 2 + "\" ry=\""
            + rect.getYRadius() / 2 + "\" fill=\"rgb(" + rect.getColor().getRed() + ","
            + rect.getColor().getGreen() + "," + rect.getColor().getBlue() + ")\" "
            + "visibility=\"hidden\">\n");
    sb.append("     <animate attributeType=\"xml\" begin=\"" + shape.getAppearTime() * 100
            + "ms\" dur=\"" + (shape.getDisappearTime() * 100 - shape.getAppearTime() * 100)
            + "ms\" " + "attributeName=\"visibility\" to=\"visible\" />\n");
    for (Event e : model.eventsForShape(s)) {
      animationString(s, e);
    }
    sb.append("</ellipse>\n");
  }

  private void animationString(Shape s, Event e) {
    // draw animations on svg.
    String beginString = "";
    String fixedHeader = "     <animate attributeType=\"xml\" begin=\"" + e.getStartTime() * 100
            + "ms\" dur=\"" + (e.getEndTime() * 100 - e.getStartTime() * 100) + "ms\" ";
    switch (e.getClass().getName()) {
      // Move event svg text.
      case "cs5004.animator.model.Move":
        Move move = (Move) e;
        if (move.getStartPosition().getX() != move.getEndPosition().getX()) {
          beginString += fixedHeader + "attributeName=\"" + s.getXNameSVG() + "\" from=\""
                  + move.getStartPosition().getX()
                  + "\" to=\"" + move.getEndPosition().getX() + "\" fill=\"freeze\" />\n";
        }
        if (move.getStartPosition().getY() != move.getEndPosition().getY()) {
          beginString += fixedHeader + "attributeName=\"" + s.getYNameSVG() + "\" from=\""
                  + move.getStartPosition().getY()
                  + "\" to=\"" + move.getEndPosition().getY() + "\" fill=\"freeze\" />\n";
        }
        break;
      // Scale event svg text.
      case "cs5004.animator.model.Scale":
        Scale scale = (Scale) e;
        if (scale.getStartX() != scale.getEndX()) {
          beginString += fixedHeader + "attributeName=\"" + s.getWidthNameSVG() + "\" from=\""
                  + scale.getStartX()
                  + "\" to=\"" + scale.getEndX() + "\" fill=\"freeze\" />\n";
        }
        if (scale.getStartY() != scale.getEndY()) {
          beginString += fixedHeader + "attributeName=\"" + s.getHeightNameSVG() + "\" from=\""
                  + scale.getStartY()
                  + "\" to=\"" + scale.getEndY() + "\" fill=\"freeze\" />\n";
        }
        break;
      // ChangeColor event svg text.
      case "cs5004.animator.model.ChangeColor":
        ChangeColor cg = (ChangeColor) e;
        IColor startColor = cg.getStartColor();
        IColor endColor = cg.getEndColor();
        beginString += fixedHeader + "attributeName=\"fill\" from=\"rgb(" + startColor.getRed()
                + "," + startColor.getGreen() + "," + startColor.getBlue() + ")\" to=\"rgb("
                + endColor.getRed() + "," + endColor.getGreen() + "," + endColor.getBlue()
                + ")\" fill=\"freeze\" />\n";
        break;
      default:
        throw new IllegalArgumentException("Event not implemented");
    }
    sb.append(beginString);
  }

  /**
   * Returns the text in svg format for unit testing.
   *
   * @param model the model to translate
   * @return the text in svg format
   */
  public Appendable getSb(Animation model) {
    sb.append("<svg viewBox=\"" + (int) model.getWindowLocation().getX() + " "
            + (int) model.getWindowLocation().getY() + " " + model.getWindowWidth() + " "
            + model.getWindowHeight() + "\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n");
    for (ShapeInWindow s : model.getShapeList()) {
      drawShape(s, model);
    }
    sb.append("</svg>\n");
    return sb;
  }
}