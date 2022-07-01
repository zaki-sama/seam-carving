import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.*;

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
  private final JFileChooser fileChooser;
  private static final FileNameExtensionFilter FILTER = new FileNameExtensionFilter(
          "Supported formats (*.ppm , *.jpg, *.png, *.bmp)"
          , "ppm", "jpg", "jpeg", "png", "bmp");

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
    vertical = new JButton("Carve vertically");
    horizontal = new JButton("Carve horizontally");

    load.setActionCommand("load");
    vertical.setActionCommand("vertical");
    horizontal.setActionCommand("horizontal");

    selection.add(load);
    carving.add(vertical);
    carving.add(horizontal);

    this.display = new JPanel();
    this.workingImage = new JLabel();
    workingImage.setPreferredSize(new Dimension(600, 400));
    display.add(workingImage);

    this.imageNames = new ArrayList<>();

    container.add(selection, BorderLayout.NORTH);
    container.add(display, BorderLayout.CENTER);
    container.add(carving, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.fileChooser = new JFileChooser(".");
    fileChooser.setFileFilter(FILTER);

    pack();
  }

  public void setListener(ActionListener listener) {
    load.addActionListener(listener);
    vertical.addActionListener(listener);
    horizontal.addActionListener(listener);
  }

  public void updateImage(Image image) {
    BufferedImage im = Util.toBufferedImage(image);
    workingImage.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    workingImage.setIcon(new ImageIcon(im));
    pack();
  }

  public boolean loadedFile() {
    int select = fileChooser.showOpenDialog(this);
    return (select == JFileChooser.APPROVE_OPTION);
  }

  public String loadedFilePath() {
    File f = fileChooser.getSelectedFile();
    return f.getAbsolutePath();
  }

  public void display() {
    this.setVisible(true);
  }

  public void addImageOption(String path) {
    if(imageNames.size() > 0) {
      selection.remove(images);
    }
    imageNames.remove(path);
    this.imageNames.add(0, path);
    images = new JComboBox(imageNames.toArray());
    selection.add(images);
    this.repaint();
  }

  public void addOptionListener(ItemListener listener) {
    this.images.addItemListener(listener);
  }

  public String getSelectedImage() {
    return (String) this.images.getSelectedItem();
  }
}
