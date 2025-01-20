import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GUI2 {
    private JFrame mainFrame;
    private JPanel mainPanel, imagePanel, buttonPanel, buttonContainerPanel;
    private JButton newBtn, trainBtn, compressBtn, openBtn;

    public void createAndShowGUI2(){
        // (Used methods to organize)
        createMainFrame();
        createMainPanel();
        createImagePanel();
        createButtonPanel();

        GridBagConstraints gbcPanels = new GridBagConstraints();
        gbcPanels.fill = GridBagConstraints.BOTH;
        gbcPanels.weighty = 1.0;

        // Add imagePanel to mainPanel
        gbcPanels.gridx = 0;
        gbcPanels.weightx = 0.9; // 90% of the main panel width
        mainPanel.add(imagePanel, gbcPanels);

        // Add buttonPanel to mainPanel
        gbcPanels.gridx = 1;
        gbcPanels.weightx = 0.1; // 10% of the main panel width
        mainPanel.add(buttonPanel, gbcPanels);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    public final void createMainFrame(){
        mainFrame = new JFrame("Huffman Coding Image Compression");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1600, 900);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
    }

    public final void createMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
    }

    public final void createImagePanel(){
        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(40, 40, 40));
        imagePanel.setLayout(new BorderLayout());
    }

    public final void createButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(77, 77, 77));
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        buttonContainerPanel = new JPanel();
        buttonContainerPanel.setLayout(new GridLayout(4, 1, 10,30));
        buttonContainerPanel.setOpaque(false);

        createButtons();
        
        // Add buttons to buttonContainerPanel
        buttonContainerPanel.add(newBtn);
        buttonContainerPanel.add(trainBtn);
        buttonContainerPanel.add(compressBtn);
        buttonContainerPanel.add(openBtn);

        // Add buttonContainerPanel to buttonPanel
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonContainerPanel, gbcButtons);
    }

    public final void createButtons(){
        newBtn = new JButton("NEW");
        trainBtn = new JButton("TRAIN");
        compressBtn = new JButton("COMPRESS");
        openBtn = new JButton("OPEN");

        Dimension buttonSize = new Dimension(200, 70);
        newBtn.setPreferredSize(new Dimension(buttonSize));
        trainBtn.setPreferredSize(new Dimension(buttonSize));
        compressBtn.setPreferredSize(new Dimension(buttonSize));
        openBtn.setPreferredSize(new Dimension(buttonSize));

        Color buttonColor = new  Color(200, 200, 200);
        newBtn.setBackground(buttonColor);
        trainBtn.setBackground(buttonColor);
        compressBtn.setBackground(buttonColor);
        openBtn.setBackground(buttonColor);

        newBtn.setBorderPainted(false);
        trainBtn.setBorderPainted(false);
        compressBtn.setBorderPainted(false);
        openBtn.setBorderPainted(false);
    }
}

