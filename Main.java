
public class Main {
    public static void main(String[] args) {
        ButtonOperations buttonOperations = new ButtonOperations();
        GUI gui = new GUI(buttonOperations);

        gui.createAndShowGUI();
    }
}