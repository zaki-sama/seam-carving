import java.awt.event.*;

public class Controller implements ActionListener, ItemListener{

  private final Model model;
  private final View view;
  private boolean vertical = true;

  public Controller(Model model, View view) {
    this.model = model;
    this.view = view;
    this.view.display();
    view.setListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name;
    switch (e.getActionCommand()) {
      case "load":
        if (view.loadedFile()) {
          String path = view.loadedFilePath();
          String[] splitPath = path.split("/");
          name = splitPath[splitPath.length - 1];
          this.model.loadImage(Util.toArray(path), name);
          this.view.addImageOption(name);
          this.view.addOptionListener(this);
          this.view.updateImage(model.getImageCopy(name));
        }
        break;
      case "horizontal":
        name = this.view.getSelectedImage();
        this.carve(name, false);
        this.vertical = false;
        break;
      case "vertical":
        name = this.view.getSelectedImage();
        this.carve(name, true);
        this.vertical = true;
        break;
      case "reset":
        System.out.println("reset controller");
        name = this.view.getSelectedImage();
        view.updateImage(model.getImageCopy(name));
        break;
    }
  }

  private void carve(String name, boolean vertical) {
    this.model.carveSeam(name, this.vertical);
    this.view.updateImage(model.getImageCopy(name));

    this.model.setupSeam(name, vertical);
    this.model.drawSeam(name);

    this.view.updateImage(model.getImageCopy(name));
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    view.updateImage(model.getImageCopy(view.getSelectedImage()));
  }
}
