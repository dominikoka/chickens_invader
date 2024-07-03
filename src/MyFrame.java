import javax.swing.*;
import java.io.IOException;

public class MyFrame extends JFrame {
  private Graphic panel;


  public Graphic getPanel() {
    return panel;
  }

  public MyFrame() throws IOException {
    super("CHICKEN INVADER");
    panel = new Graphic();
    add(panel);

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    addMouseListener(panel);
    addKeyListener(panel);

  }
  public static void main(String[] args) throws IOException, InterruptedException {

    new MyFrame().getPanel().processor();

  }
}