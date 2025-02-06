import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GUI{
    private JPanel mainPanel, imagePanel, buttonPanel, buttonContainerPanel;
    private JLabel uncompressedFileSizeLabel, compressedFileSizeLabel, imageLabelCompressed, imageLabelUncompressed, statusLabel;
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    private ButtonOperations buttonOperations;
    private JFrame mainFrame;
    
    public GUI(ButtonOperations buttonOperations){
        this.buttonOperations =  buttonOperations;
        this.imageLabelUncompressed = buttonOperations.imageLabelUncompressed;
        this.imageLabelCompressed = buttonOperations.imageLabelCompressed;
        this.uncompressedFileSizeLabel = buttonOperations.uncompressedFileSizeLabel;
        this.compressedFileSizeLabel = buttonOperations.compressedFileSizeLabel;
        this.statusLabel = buttonOperations.statusLabel;
        this.newBtn = buttonOperations.getNewBtn();
        this.trainBtn = buttonOperations.getTrainBtn();
        this.compressBtn = buttonOperations.getCompressBtn();
        this.openBtn = buttonOperations.getOpenBtn();
    }

    public void createAndShowGUI(){
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
        imagePanel.setLayout(new BorderLayout());

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

        JPanel imagePanelContainer = new JPanel();
        imagePanelContainer.setOpaque(false);
        GridBagConstraints gbcImagePanelContainer = new GridBagConstraints();
    
        gbcImagePanelContainer.fill = GridBagConstraints.BOTH;
        gbcImagePanelContainer.gridx = 0;
        gbcImagePanelContainer.ipadx = 0;
        imagePanelContainer.add(uncompressedImgPanel, gbcImagePanelContainer);
        gbcImagePanelContainer.gridx = 1;
        gbcImagePanelContainer.ipadx = 0;
        imagePanelContainer.add(paddingPanel, gbcImagePanelContainer);
        gbcImagePanelContainer.gridx = 2;
        gbcImagePanelContainer.ipadx = 0;
        imagePanelContainer.add(compressedImgPanel, gbcImagePanelContainer);

        statusLabel.setForeground(Color.white);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD,16));

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(40, 42, 47));
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        JPanel displayPadPanel = new JPanel();
        displayPadPanel.setBackground(Color.BLUE);
        displayPadPanel.setLayout(new GridBagLayout());
        displayPadPanel.setOpaque(false);
        displayPadPanel.add(imagePanelContainer);

        imagePanel.add(statusPanel, BorderLayout.SOUTH);
        imagePanel.add(displayPadPanel, BorderLayout.CENTER);
         
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

        Color buttonColor = new  Color(170, 170, 170);
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

        addGUIButtonListeners(newBtn);
        addGUIButtonListeners(trainBtn);
        addGUIButtonListeners(compressBtn);
        addGUIButtonListeners(openBtn);
    }

    private void addGUIButtonListeners(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new  Color(250, 250, 250));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new  Color(170, 170, 170));
            }
        });
    }
}