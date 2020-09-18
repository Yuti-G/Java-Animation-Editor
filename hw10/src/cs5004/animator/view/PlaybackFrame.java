package cs5004.animator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

//import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs5004.animator.controller.Controller;
import cs5004.animator.controller.KeyboardController;
import cs5004.animator.controller.MouseController;
import cs5004.animator.model.Animation;
import cs5004.animator.model.Event;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeInWindow;
import cs5004.animator.model.util.AnimationReader;

/**
 * This class represents a playback view frame to hold components.
 */
public class PlaybackFrame extends JFrame implements ActionListener, ItemListener {
  private VisualPanel visualPanel;
  private JTextField speedText;
  private JLabel speedDisplay;
  private JTextArea addText;
  JComboBox<String> shapesComboBox;

  private int speed;
  private Animation model;

  private Controller c;

  private boolean hasStarted;

  /**
   * A PlaybackFrame object is constructed with 2 parameters.
   *
   * @param model the animation model to display
   * @param speed the initial speed to display
   */
  public PlaybackFrame(Animation model, int speed, KeyboardController kc, MouseController mc) {
    super();
    this.speed = speed;
    this.model = model;
    this.hasStarted = false;

    JPanel mainPanel;
    JScrollPane mainScrollPane;

    setTitle("Playback View");
    int width = model.getWindowWidth() + 525;
    int height = Math.max(775, model.getWindowHeight() + 50);
    setSize(width, height);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // to display animation visual view
    visualPanel = new VisualPanel(this.model, 0);
    visualPanel.setBorder(BorderFactory.createTitledBorder("visual view"));
    visualPanel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    mainPanel.add(visualPanel);

    // control Panel
    JPanel controlPanel = new JPanel();
    controlPanel.setPreferredSize(new Dimension(500, 725));
    controlPanel.setBorder(BorderFactory.createTitledBorder("control"));
    controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

    // button Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    // start button
    JButton startButton = new JButton("start");
    startButton.addActionListener(this);
    startButton.setActionCommand("start");
    buttonPanel.add(startButton);
    // pause button
    JButton pauseButton = new JButton("pause");
    pauseButton.addActionListener(this);
    pauseButton.setActionCommand("pause");
    buttonPanel.add(pauseButton);
    // resume button
    JButton resumeButton = new JButton("resume");
    resumeButton.addActionListener(this);
    resumeButton.setActionCommand("resume");
    buttonPanel.add(resumeButton);
    // restart button
    JButton restartButton = new JButton("restart");
    restartButton.addActionListener(this);
    restartButton.setActionCommand("restart");
    buttonPanel.add(restartButton);
    controlPanel.add(buttonPanel);

    // loop panel
    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
    // checkbox for loop
    JCheckBox loopCheckBox = new JCheckBox("Enable looping");
    loopCheckBox.setSelected(false);
    loopCheckBox.setActionCommand("loop");
    loopCheckBox.addItemListener(this);
    checkBoxPanel.add(loopCheckBox);
    controlPanel.add(checkBoxPanel);
    // keyboard label
    JLabel spaceLabel = new JLabel(" You can also press [space] to start/pause/resume.");
    controlPanel.add(spaceLabel);

    // speed panel
    JPanel speedPanel = new JPanel();
    speedPanel.setBorder(BorderFactory.createTitledBorder("speed"));
    speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.Y_AXIS));
    speedPanel.setPreferredSize(new Dimension(482, 80));
    // speed panel 1 to contain buttons
    JPanel speedPanel1 = new JPanel();
    speedPanel1.setLayout(new BoxLayout(speedPanel1, BoxLayout.X_AXIS));
    // increase speed button
    JButton increaseButton = new JButton("increase");
    increaseButton.addActionListener(this);
    increaseButton.setActionCommand("increase");
    speedPanel1.add(increaseButton);
    // decrease speed button
    JButton decreaseButton = new JButton("decrease");
    decreaseButton.addActionListener(this);
    decreaseButton.setActionCommand("decrease");
    speedPanel1.add(decreaseButton);
    // set speed text field
    JLabel textLabel = new JLabel("Set speed:");
    speedPanel1.add(textLabel);
    speedText = new JTextField(10);
    speedPanel1.add(speedText);
    JButton speedButton = new JButton("set");
    speedButton.addActionListener(this);
    speedButton.setActionCommand("speed");
    speedPanel1.add(speedButton);
    speedPanel.add(speedPanel1);
    // speed panel 2 to contain display label
    JPanel speedPanel2 = new JPanel();
    String display = "current speed: " + speed + "ticks per second";
    speedDisplay = new JLabel(display);
    speedPanel2.add(speedDisplay);
    speedPanel.add(speedPanel2);
    controlPanel.add(speedPanel);

    // add a new shape
    JPanel addPanel = new JPanel();
    addPanel.setBorder(BorderFactory.createTitledBorder("add shapes and motions"));
    addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));
    JPanel addPanel1 = new JPanel();
    JLabel addLabel = new JLabel();
    addLabel.setText("<html><body>You can input shapes and motions as following format and"
            + " click add<br>button to add new shapes and motions.<br>"
            + "# declares a rectangle shape named R<br>"
            + "shape R rectangle<br>(Note: the type of shape can only be rectangle and ellipse)<br>"
            + "# describes the motions of shape R, between two moments of animation:<br>"
            + "# t == tick<br>"
            + "# (x,y) == position<br>"
            + "# (w,h) == dimensions<br>"
            + "# (r,g,b) == color (with values between 0 and 255)<br>"
            + "# ----------------start--------------------end-------------<br>"
            + "# &emsp; &emsp; &emsp; t &emsp; x &emsp;y &emsp;w &emsp;h &emsp;r &emsp; g &ensp;b"
            + " &emsp; &emsp; t &emsp;x &emsp; y &emsp;w &emsp;h &emsp;r &emsp;g &ensp;b<br>"
            + "motion R 1 &ensp;200 200 50 100 255&ensp;0 &ensp;0 &emsp; &emsp;10&ensp;200 200 50 "
            + "100 255 0&ensp;0<br>"
            + "motion R 10 200 200 50 100 255&ensp;0 &ensp;0 &emsp; &emsp;50&ensp;300 300 50 100 "
            + "255 0&ensp;0<br>"
            + "</body></html>");
    addPanel1.add(addLabel);
    addPanel.add(addPanel1);
    // add text area (scrollable)
    addText = new JTextArea(5, 30);
    JScrollPane scrollPane = new JScrollPane(addText);
    addText.setLineWrap(true);
    addPanel.add(scrollPane);
    // add button
    JPanel addButtonPanel = new JPanel();
    JButton addButton = new JButton("add");
    addButton.addActionListener(this);
    addButton.setActionCommand("add");
    addButtonPanel.add(addButton);
    addPanel.add(addButtonPanel);
    controlPanel.add(addPanel);

    // delete panel
    JPanel deletePanel = new JPanel();
    deletePanel.setBorder(BorderFactory.createTitledBorder("delete shapes"));
    deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));
    deletePanel.setPreferredSize(new Dimension(482, 100));
    // delete label
    JPanel deleteLabelPanel = new JPanel();
    JLabel deleteLabel = new JLabel(" Choose a shape (Name):");
    deleteLabelPanel.setLayout(new BorderLayout());
    deleteLabelPanel.add(deleteLabel, BorderLayout.WEST);
    deletePanel.add(deleteLabelPanel);
    // delete combobox
    JPanel deleteButtonPanel = new JPanel();
    deleteButtonPanel.setLayout(new BoxLayout(deleteButtonPanel, BoxLayout.X_AXIS));
    String[] shapesName = new String[model.getShapeList().size()];
    for (int i = 0; i < model.getShapeList().size(); i++) {
      if (model.getShapeList().get(i).getShape() instanceof Rectangle) {
        shapesName[i] = "Rectangle " + model.getShapeList().get(i).getName();
      } else {
        shapesName[i] = "Oval " + model.getShapeList().get(i).getName();
      }
    }
    shapesComboBox = new JComboBox<>(shapesName);
    deleteButtonPanel.add(shapesComboBox);
    // view button
    JButton viewButton = new JButton("view info");
    viewButton.setActionCommand("view");
    viewButton.addActionListener(this);
    deleteButtonPanel.add(viewButton);
    // delete button
    JButton deleteButton = new JButton("delete");
    deleteButton.setActionCommand("delete");
    deleteButton.addActionListener(this);
    deleteButtonPanel.add(deleteButton);
    deletePanel.add(deleteButtonPanel);
    // mouse Label
    JPanel mousePanel = new JPanel();
    JLabel mouseLabel = new JLabel();
    mouseLabel.setText("Shape can also be deleted if you click on it in the left visual view.");
    mousePanel.add(mouseLabel);
    deletePanel.add(mousePanel);
    controlPanel.add(deletePanel);

    // file panel
    JPanel filePanel = new JPanel();
    filePanel.setBorder(BorderFactory.createTitledBorder("file"));
    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
    filePanel.setPreferredSize(new Dimension(482, 55));
    // save button
    JButton saveButton1 = new JButton("save to .txt");
    saveButton1.addActionListener(this);
    saveButton1.setActionCommand("save to .txt");
    filePanel.add(saveButton1);
    JButton saveButton2 = new JButton("save to .svg");
    saveButton2.addActionListener(this);
    saveButton2.setActionCommand("save to .svg");
    filePanel.add(saveButton2);
    // load button
    JButton loadButton = new JButton("load a file");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(this);
    filePanel.add(loadButton);
    controlPanel.add(filePanel);

    mainPanel.add(controlPanel);
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(kc);
    visualPanel.addMouseListener(mc);
  }

  /**
   * Add controller to the frame.
   *
   * @param c the controller to add
   */
  public void addListener(Controller c) {
    this.c = c;
  }

  /**
   * Handle space key pressed event in view.
   */
  public void handleKeyBoardSpace() {
    if (this.hasStarted) {
      if (visualPanel.isRunning()) {
        visualPanel.pause();
      } else {
        visualPanel.resume();
      }
    } else {
      visualPanel.start(speed);
      this.hasStarted = true;
    }
    this.repaint();
  }


  /**
   * handle arrow key pressed event in view.
   *
   * @param increase whether we are increasing the speed.
   */
  public void changeSpeed(boolean increase) {
    if (increase) {
      speed++;
    } else {
      speed--;
    }
    String display = "current speed: " + speed + "ticks per second";
    speedDisplay.setText(display);
    visualPanel.setSpeed(speed);
  }

  /**
   * Handle mouse clicks on screen to delete shape event.
   *
   * @param s Shape to delete
   */
  public void handleMouseClick(Shape s) {
    visualPanel.deleteShape(s);
    if (s instanceof Rectangle) {
      shapesComboBox.removeItem("Rectangle " + s.getName());
    } else {
      shapesComboBox.removeItem("Oval " + s.getName());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        visualPanel.start(speed);
        this.repaint();
        break;
      case "pause":
        visualPanel.pause();
        this.repaint();
        break;
      case "resume":
        visualPanel.resume();
        this.repaint();
        break;
      case "restart":
        visualPanel.restart(speed);
        this.repaint();
        break;
      case "increase":
        changeSpeed(true);
        break;
      case "decrease":
        changeSpeed(false);
        break;
      case "speed":
        try {
          speed = Integer.parseInt(speedText.getText());
          String display = "current speed: " + speed + "ticks per second";
          speedDisplay.setText(display);
          visualPanel.setSpeed(speed);
        } catch (NumberFormatException n) {
          JFrame frame = new JFrame("JOptionPane MessageDialog");
          JOptionPane.showMessageDialog(frame,
                  "Your speed input is not an integer, double check and run it again.",
                  "Argument malformed",
                  JOptionPane.ERROR_MESSAGE);
        }
        break;
      case "add":
        try {
          Readable addShape = new StringReader(addText.getText());
          Animation.Builder reader = new Animation.Builder();
          Animation addModel = (Animation) AnimationReader.parseFile(addShape, reader);
          visualPanel.addAnimation(addModel, c);
          // message
          JFrame frame = new JFrame("JOptionPane MessageDialog");
          JOptionPane.showMessageDialog(frame,
                  "Add new shapes and motion SUCCESS!!!\n\n" + addModel,
                  "Success message",
                  JOptionPane.INFORMATION_MESSAGE);
          addText.setText("");
          // refresh combobox
          for (int i = 0; i < addModel.getShapeList().size(); i++) {
            if (model.getShapeList().get(i).getShape() instanceof Rectangle) {
              shapesComboBox.addItem("Rectangle " + addModel.getShapeList().get(i).getName());
            } else {
              shapesComboBox.addItem("Oval " + addModel.getShapeList().get(i).getName());
            }
          }
        } catch (IllegalArgumentException | IllegalStateException i) {
          JFrame frame = new JFrame("JOptionPane MessageDialog");
          JOptionPane.showMessageDialog(frame,
                  "Your input is not in required format, double check and run it again.",
                  "Argument malformed",
                  JOptionPane.ERROR_MESSAGE);
        }
        break;
      case "view":
        String shapeName = (String) shapesComboBox.getSelectedItem();
        String[] name = shapeName.split("\\s+");
        shapeName = name[name.length - 1];
        ShapeInWindow selectedShape = null;
        for (ShapeInWindow s : model.getShapeList()) {
          if (shapeName.equals(s.getName())) {
            selectedShape = s;
          }
        }
        // find event related to the shape
        String eventInfo = "";
        for (Event event : model.getEventList()) {
          if (event.getShape().getName().equals(shapeName)) {
            eventInfo += event;
          }
        }
        JFrame frame = new JFrame("JOptionPane MessageDialog");
        JOptionPane.showMessageDialog(frame, selectedShape + "\n" + eventInfo,
                "Infomation of the Shape",
                JOptionPane.INFORMATION_MESSAGE);
        break;
      case "delete":
        shapeName = (String) shapesComboBox.getSelectedItem();
        name = shapeName.split("\\s+");
        String shapeName1 = name[name.length - 1];
        visualPanel.deleteShape(shapeName1, c);
        // message
        frame = new JFrame("JOptionPane MessageDialog");
        JOptionPane.showMessageDialog(frame,
                "Delete the shape" + shapeName + "and motions apply to it SUCCESS!!!",
                "Success message",
                JOptionPane.INFORMATION_MESSAGE);
        // refresh combobox
        shapesComboBox.removeItem(shapeName);
        break;
      case "save to .txt":
        // prompt the file chooser dialog
        JFileChooser fileChooser = new JFileChooser(".");

        // filter by file extension (.txt)
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
                "Text file(.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String fileName = fileChooser.getName(file);

          // add extension to user input if needed
          if (fileName.indexOf(".txt") == -1) {
            file = new File(fileChooser.getCurrentDirectory(), fileName + ".txt");
          }

          // write to txt file
          TextView text = new TextView();
          text.showView(model, file.getName());
          // message
          frame = new JFrame("JOptionPane MessageDialog");
          JOptionPane.showMessageDialog(frame,
                  "Save to" + file.getName() + " SUCCESS!!!",
                  "Success message",
                  JOptionPane.INFORMATION_MESSAGE);
        }
        break;
      case "save to .svg":
        // prompt the file chooser dialog
        fileChooser = new JFileChooser(".");

        // filter by file extension
        FileNameExtensionFilter svgFilter = new FileNameExtensionFilter(
                "SVG file(.svg)", "svg");
        fileChooser.setFileFilter(svgFilter);

        option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String fileName = fileChooser.getName(file);

          // add extension to user input if needed
          if (fileName.indexOf(".svg") == -1) {
            file = new File(fileChooser.getCurrentDirectory(), fileName + ".svg");
          }
          // write to txt file
          SVGView svg = new SVGView();
          svg.showView(model, file.getName());
          // message
          frame = new JFrame("JOptionPane MessageDialog");
          JOptionPane.showMessageDialog(frame,
                  "Save to" + file.getName() + " SUCCESS!!!",
                  "Success message",
                  JOptionPane.INFORMATION_MESSAGE);
        }
        break;
      case "load":
        fileChooser = new JFileChooser(".");

        // filter by file extension (.txt)
        txtFilter = new FileNameExtensionFilter(
                "Text file(.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);

        option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String fileName = fileChooser.getName(file);
          try {
            FileReader fileReader = new FileReader(new File(fileName));
            Animation.Builder reader = new Animation.Builder();
            Animation loadModel = (Animation) AnimationReader.parseFile(fileReader, reader);
            visualPanel.changeModel(loadModel, c);
            // message
            frame = new JFrame("JOptionPane MessageDialog");
            JOptionPane.showMessageDialog(frame,
                    "Load " + fileName + " SUCCESS!!!",
                    "Success message",
                    JOptionPane.INFORMATION_MESSAGE);
            // resize visual panel and window size
            int width = model.getWindowWidth() + 525;
            int height = Math.max(700, model.getWindowHeight() + 50);
            setSize(width, height);
            visualPanel.setPreferredSize(new Dimension(
                    model.getWindowWidth(), model.getWindowHeight()));
            // refresh combobox
            shapesComboBox.removeAllItems();
            for (int i = 0; i < model.getShapeList().size(); i++) {
              if (model.getShapeList().get(i).getShape() instanceof Rectangle) {
                shapesComboBox.addItem("Rectangle " + model.getShapeList().get(i).getName());
              } else {
                shapesComboBox.addItem("Oval " + model.getShapeList().get(i).getName());
              }
            }
          } catch (IllegalArgumentException | IllegalStateException | FileNotFoundException i) {
            frame = new JFrame("JOptionPane MessageDialog");
            JOptionPane.showMessageDialog(frame,
                    "Your txt file is not in required format, double check and run it again.",
                    "Argument malformed",
                    JOptionPane.ERROR_MESSAGE);
          }
        }
        break;
      default:
        break;
    }
    this.requestFocus();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (((JCheckBox) e.getItemSelectable()).getActionCommand().equals("loop")) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        visualPanel.allowLoop(true);
      } else {
        visualPanel.allowLoop(false);
      }
    }
    this.requestFocus();
  }
}
