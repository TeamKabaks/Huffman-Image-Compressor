
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class GUI{
    private JPanel mainPanel, imagePanel, buttonPanel, buttonContainerPanel;
    private JLabel uncompressedFileSizeLabel, compressedFileSizeLabel, imageLabelCompressed, imageLabelUncompressed;
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    private ButtonOperations buttonOperations;
    private JFrame mainFrame;
    
    public GUI(ButtonOperations buttonOperations){
        this.buttonOperations =  buttonOperations;
        this.imageLabelUncompressed = buttonOperations.imageLabelUncompressed;
        this.imageLabelCompressed = buttonOperations.imageLabelCompressed;
        this.uncompressedFileSizeLabel = buttonOperations.uncompressedFileSizeLabel;
        this.compressedFileSizeLabel = buttonOperations.compressedFileSizeLabel;
        this.newBtn = buttonOperations.getNewBtn();
        this.trainBtn = buttonOperations.getTrainBtn();
        this.compressBtn = buttonOperations.getCompressBtn();
        this.openBtn = buttonOperations.getOpenBtn();
    }

    public void createAndShowGUI(){
        // (Used methods to organize)
        createMainFrame();
        createImagePanel();
        createButtonPanel();
        createMainPanel();
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
        mainPanel.setBackground(new Color(30, 31, 34));
        GridBagConstraints gbcPanels = new GridBagConstraints();

        gbcPanels.fill = GridBagConstraints.BOTH;
        gbcPanels.weighty = 1.0;
        gbcPanels.gridx = 1;
        gbcPanels.gridy = 1;
        gbcPanels.weightx = 0.9; // 90% of the main panel width
        mainPanel.add(imagePanel, gbcPanels);
        
        // Add buttonPanel to mainPanel
        gbcPanels.fill = GridBagConstraints.BOTH;
        gbcPanels.weighty = 1.0;
        gbcPanels.gridx = 3;
        gbcPanels.gridy = 1;
        gbcPanels.weightx = 0.1; // 10% of the main panel width
        mainPanel.add(buttonPanel, gbcPanels);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    public final void createImagePanel(){
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(500, 500));
        imagePanel.setBackground(new Color(30, 31, 34));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(49, 51, 56), 15));
        imagePanel.setLayout(new GridBagLayout());

        JPanel uncompressedImgPanel = new JPanel();
        JPanel compressedImgPanel = new JPanel();
        uncompressedImgPanel.setLayout(new GridBagLayout());
        uncompressedImgPanel.setOpaque(false);
        compressedImgPanel.setLayout(new GridBagLayout());
        compressedImgPanel.setOpaque(false);

        uncompressedFileSizeLabel.setForeground(Color.white);
        uncompressedFileSizeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        compressedFileSizeLabel.setForeground(Color.white);
        compressedFileSizeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel paddingPanel = new JPanel();
        paddingPanel.setOpaque(false);

        GridBagConstraints gbcSubImagePanels = new GridBagConstraints();
        gbcSubImagePanels.gridx = 0;
        gbcSubImagePanels.gridy = 0;
        uncompressedImgPanel.add(imageLabelUncompressed, gbcSubImagePanels);
        compressedImgPanel.add(imageLabelCompressed, gbcSubImagePanels);
        gbcSubImagePanels.gridx = 0;
        gbcSubImagePanels.gridy = 1;
        uncompressedImgPanel.add(uncompressedFileSizeLabel, gbcSubImagePanels);
        compressedImgPanel.add(compressedFileSizeLabel, gbcSubImagePanels);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        imagePanel.add(uncompressedImgPanel, gbc);
        gbc.gridx = 1;
        gbc.ipadx = 0;
        imagePanel.add(paddingPanel, gbc);
        gbc.gridx = 2;
        gbc.ipadx = 0;
        imagePanel.add(compressedImgPanel, gbc);
    }

    public final void createButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(49, 51, 56));
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

        newBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        trainBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        compressBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        openBtn.setFont(new Font("SansSerif", Font.BOLD, 15));

        newBtn.setFocusPainted(false);
        trainBtn.setFocusPainted(false);
        compressBtn.setFocusPainted(false);
        openBtn.setFocusPainted(false);
    }
}
