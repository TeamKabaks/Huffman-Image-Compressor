
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class GUI{
    private JPanel mainPanel, imagePanel, buttonPanel, buttonContainerPanel;
    private JLabel imageLabel; 
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    private ButtonOperations buttonOperations;
    private JFrame mainFrame;
    
    public GUI(ButtonOperations buttonOperations){
        this.buttonOperations =  buttonOperations;
        this.imageLabel = buttonOperations.imageLabel;
        this.newBtn = buttonOperations.getNewBtn();
        this.trainBtn = buttonOperations.getTrainBtn();
        this.compressBtn = buttonOperations.getCompressBtn();
        this.openBtn = buttonOperations.getOpenBtn();
    }

    public void createAndShowGUI(){
        // (Used methods to organize)
        createMainFrame();
        createMainPanel();
        createImagePanel();
        createButtonPanel();

        GridBagConstraints gbcPanels = new GridBagConstraints();
        gbcPanels.fill = GridBagConstraints.BOTH;
        gbcPanels.weighty = 1.0;

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
        imagePanel.setPreferredSize(new Dimension(500, 500));
        imagePanel.setBackground(new Color(40, 40, 40));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(77, 77, 77), 10));
        imagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        imagePanel.add(imageLabel, gbc); 
    }

    public final void createButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(77, 77, 77));
        buttonPanel.setLayout(new GridBagLayout());
        buttonContainerPanel = new JPanel();
        buttonContainerPanel.setLayout(new GridLayout(4, 1, 10,30));
        buttonContainerPanel.setOpaque(false);

        designButtons();
        
        buttonContainerPanel.add(newBtn);
        buttonContainerPanel.add(trainBtn);
        buttonContainerPanel.add(compressBtn);
        buttonContainerPanel.add(openBtn);

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonContainerPanel, gbcButtons);
    }

    public final void designButtons(){

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

        Color buttonBorderColor = new  Color(150, 150, 150);
        newBtn.setBorder( BorderFactory.createLineBorder(buttonBorderColor, 6));
        trainBtn.setBorder( BorderFactory.createLineBorder(buttonBorderColor, 6));
        compressBtn.setBorder( BorderFactory.createLineBorder(buttonBorderColor, 6));
        openBtn.setBorder( BorderFactory.createLineBorder(buttonBorderColor, 6));
    }
}
