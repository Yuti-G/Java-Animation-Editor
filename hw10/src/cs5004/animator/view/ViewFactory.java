package cs5004.animator.view;

/**
 * This class is a factory of views.
 */
public class ViewFactory {
  /**
   * Create view with given name of type.
   *
   * @param name the type of view
   * @return the view
   */
  public static View createView(String name) {
    switch (name.toLowerCase()) {
      case "svg":
        return new SVGView();
      case "text":
        return new TextView();
      case "visual":
        return new VisualView();
      case "playback":
        return new PlaybackView();
      default:
        throw new IllegalArgumentException("unknown name given to factory!");
    }
  }

}
