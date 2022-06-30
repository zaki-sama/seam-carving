import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class View extends JFrame {
  private JButton load;
  private JComboBox<String> images;
  private JButton vertical;
  private JButton horizontal;
  private JPanel selection;
  private JPanel display;
  private JLabel workingImage;
  private JPanel carving;
  private List<String> imageNames;

  public View() {
    super();
    setTitle("Seam Carving");
    //setSize(1200, 900);

    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());
    add(container);

    this.selection = new JPanel();
    selection.setLayout(new FlowLayout());

    this.carving = new JPanel();
    carving.setLayout(new FlowLayout());

    load = new JButton("Load image");
//    imageNames = new ArrayList<>();
//    images = new JComboBox(imageNames.toArray());
    vertical = new JButton("Carve vertically");
    horizontal = new JButton("Carve horizontally");

    load.setActionCommand("load");
    vertical.setActionCommand("vertical");
    horizontal.setActionCommand("horizontal");

    selection.add(load);
//    selection.add(images);
    carving.add(vertical);
    carving.add(horizontal);

    this.display = new JPanel();
    JLabel workingImage = new JLabel();
    workingImage.setPreferredSize(new Dimension(600, 400));
    display.add(workingImage);

    container.add(selection, BorderLayout.NORTH);
    container.add(display, BorderLayout.CENTER);
    container.add(carving, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    pack();
  }

  public void setListener(ActionListener listener) {
    load.addActionListener(listener);
    vertical.addActionListener(listener);
    horizontal.addActionListener(listener);
  }

  public void updateImage(Image image) {

  }

//  public boolean loadedFile() {
//
//  }
//
//  public String loadedFilePath() {
//
//  }

  public void display() {
    this.setVisible(true);
  }
}
