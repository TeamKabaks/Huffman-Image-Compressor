import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JLabel imageLabel = new JLabel();
        ButtonOperations buttonOperations = new ButtonOperations(imageLabel);
        GUI gui = new GUI(buttonOperations);

        gui.createAndShowGUI();
    }
}