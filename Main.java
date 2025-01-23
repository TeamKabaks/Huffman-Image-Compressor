public class Main {
  public static void main(String[] args) {
      // Create and launch the GUI
      javax.swing.SwingUtilities.invokeLater(() -> {
          new GUI().createAndShowGUI();
          //new GUI().createAndShowGUI();
      });
  }
}
          