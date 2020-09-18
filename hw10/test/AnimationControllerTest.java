import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.model.Animation;
import cs5004.animator.model.util.AnimationReader;
import cs5004.animator.view.PlaybackView;

import static org.junit.Assert.assertTrue;

/**
 * Test for animation controller.
 */
public class AnimationControllerTest {
  private Animation model;
  private AnimationController controller;

  @Before
  public void setUp() {
    Animation.Builder builder;
    PlaybackView playbackView;
    FileReader fileReader;
    try {
      fileReader = new FileReader(new File("buildings.txt"));
      builder = new Animation.Builder();
      model = (Animation) AnimationReader.parseFile(fileReader, builder);
      playbackView = new PlaybackView();
      controller = new AnimationController(playbackView, model);
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      throw new IllegalArgumentException("File path not found!");
    }
  }


  @Test
  public void testHandleAddClick() {
    // assert the soon to be added model does not exist in the shape list nor its motions exist in
    // the motion list
    assertTrue(model.getShapeList().stream().noneMatch(shape -> shape.getName().equals("RNew")));
    assertTrue(model.getShapeList()
            .stream().noneMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("RNew")));
    assertTrue(model.getEventList()
            .stream().noneMatch(event -> event.getStartTime() == 10
                    && event.getShape().getName().equals("RNew")));

    //create new shape and motions to be added
    Animation.Builder newBuilder = new Animation.Builder();
    newBuilder.declareShape("RNew", "rectangle");
    newBuilder.addMotion("RNew", 1, 200, 200, 50, 100, 255,
            0, 0, 10, 200, 200, 50, 100, 255, 0, 0);
    newBuilder.addMotion("RNew", 10, 200, 200, 50, 100, 255,
            0, 0, 50, 300, 300, 50, 100, 255, 0, 0);
    Animation newModel = (Animation) newBuilder.build();
    controller.handleAddClick(newModel);

    // assert the model and its motions are added successfully into the lists
    assertTrue(model.getShapeList().stream().anyMatch(shape -> shape.getName().equals("RNew")));
    assertTrue(model.getShapeList()
            .stream().anyMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("RNew")));
    assertTrue(model.getEventList()
            .stream().anyMatch(event -> event.getStartTime() == 10
                    && event.getShape().getName().equals("RNew")));
  }

  @Test(expected = IllegalStateException.class)
  public void testHandleAddClick2() {
    assertTrue(model.getShapeList().stream().noneMatch(shape -> shape.getName().equals("RNew")));
    assertTrue(model.getShapeList()
            .stream().noneMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("RNew")));
    assertTrue(model.getEventList()
            .stream().noneMatch(event -> event.getStartTime() == 10
                    && event.getShape().getName().equals("RNew")));

    Animation.Builder newBuilder = new Animation.Builder();
    newBuilder.declareShape("RNew", "Rectangle");

    //test adding two motions with time conflicts and expect to fail
    newBuilder.addMotion("RNew", 1, 200, 200, 50, 100, 255,
            0, 0, 10, 250, 250, 50, 100, 255, 0, 0);

    newBuilder.addMotion("RNew", 2, 300, 400, 50, 100, 255,
            0, 0, 11, 200, 200, 50, 100, 255, 0, 0);

    Animation newModel = (Animation) newBuilder.build();
    controller.handleAddClick(newModel);
  }

  @Test(expected = IllegalStateException.class)
  public void testHandleAddClick3() {
    //add the existing shape and motion to the list and expect to fail because of duplication
    Animation.Builder newBuilder = new Animation.Builder();
    newBuilder.addMotion("background", 1, 0, 0, 800, 800, 33, 94,
            248, 1, 0, 0, 800, 800, 33, 94, 248);
  }

  @Test(expected = IllegalStateException.class)
  public void testHandleAddClick4() {
    Animation.Builder newBuilder = new Animation.Builder();
    // input a different name of a shape and expect fail to add motion to the builder
    newBuilder.addMotion("Wrong", 1, 200, 200, 50, 100, 255,
            0, 0, 10, 200, 200, 50, 100, 255, 0, 0);
  }

  @Test
  public void testHandleDeleteClick() {
    // assert the soon to be deleted shape and its motions are in the file
    assertTrue(model.getShapeList().stream().anyMatch(shape -> shape.getName()
            .equals("window361")));
    assertTrue(model.getShapeList()
            .stream().anyMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("window361")));
    assertTrue(model.getEventList()
            .stream().anyMatch(event -> event.getStartTime() == 20
                    && event.getShape().getName().equals("window361")));

    controller.handleDeleteClick("window361");

    // assert the deleted shape and its motions no longer inside the lists
    assertTrue(model.getShapeList().stream().noneMatch(shape -> shape.getName()
            .equals("window361")));
    assertTrue(model.getShapeList()
            .stream().noneMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("window361")));
    assertTrue(model.getEventList()
            .stream().noneMatch(event -> event.getStartTime() == 20
                    && event.getShape().getName().equals("window361")));

  }

  @Test
  public void testHandleChangeModel() {
    //assert true that the original loaded txt (buildings.txt) exists
    assertTrue(model.getShapeList().stream().anyMatch(shape -> shape.getName()
            .equals("window361")));
    assertTrue(model.getShapeList()
            .stream().anyMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("window361")));
    assertTrue(model.getEventList()
            .stream().anyMatch(event -> event.getStartTime() == 20
                    && event.getShape().getName().equals("window361")));


    //load smalldemo.txt and construct a new model to be used to call controller handleChangeModel

    Animation newModel;
    Animation.Builder newBuilder;

    FileReader newModelFile;
    try {
      newModelFile = new FileReader(new File("smalldemo.txt"));
      newBuilder = new Animation.Builder();
      newModel = (Animation) AnimationReader.parseFile(newModelFile, newBuilder);
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      throw new IllegalArgumentException("File path not found!");
    }


    controller.handleChangeModel(newModel);

    //assert true that the original loaded txt (buildings.txt) is removed after changeModel
    assertTrue(model.getShapeList().stream().noneMatch(shape -> shape.getName()
            .equals("window361")));
    assertTrue(model.getShapeList()
            .stream().noneMatch(shape -> shape.getAppearTime() == 1
                    && shape.getName().equals("window361")));
    assertTrue(model.getEventList()
            .stream().noneMatch(event -> event.getStartTime() == 20
                    && event.getShape().getName().equals("window361")));


    //assert true that the new loaded file(smalldemo.txt) works
    assertTrue(model.getShapeList().stream().anyMatch(shape -> shape.getName().equals("C")));
    assertTrue(model.getShapeList()
            .stream().anyMatch(shape -> shape.getAppearTime() == 6
                    && shape.getName().equals("C")));
    assertTrue(model.getEventList()
            .stream().anyMatch(event -> event.getStartTime() == 20
                    && event.getShape().getName().equals("C")));

  }

}
