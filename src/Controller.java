import java.awt.event.*;

public class Controller implements ActionListener {

  private Model model;
  private View view;

  public Controller(Model model, View view) {
    this.model = model;
    this.view = view;
    this.view.display();
    view.setListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
