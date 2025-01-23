/*
import java.awt.*;
import javax.swing.*;

public class GUI {
    private Operations opr; // Declare Operations instance as a class member
    private JLabel imageLabel; // Label to display the image

    public void createAndShowGUI(){
        imageLabel = new JLabel(); // Initialize the image label
        opr = new Operations(imageLabel); // Initialize Operations instance with imageLabel

        JFrame frame = new JFrame("Image Compressor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel viewerPanel = new JPanel();
        viewerPanel.setPreferredSize(new Dimension(200, 200)); // Ensure it's square
        viewerPanel.setBackground(Color.LIGHT_GRAY); // Example background color
        viewerPanel.add(imageLabel); // Add the image label to the viewer panel

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE); // Example background color
        buttonPanel.setPreferredSize(new Dimension(400, 200)); // Rectangular lengthwise

        mainPanel.add(viewerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        JButton openImageButton = new JButton("Open Image");
        JButton trainImageButton = new JButton("Train Image");
        JButton compressImageButton = new JButton("Compress Image");
        JButton decompressImageButton = new JButton("Show Compressed Image");

        openImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        compressImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decompressImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners for the buttons
        openImageButton.addActionListener(e -> openImage());
        trainImageButton.addActionListener(e -> trainImage());
        compressImageButton.addActionListener(e -> compressImage());
        decompressImageButton.addActionListener(e -> decompressImage());

        // Add buttons to the panel
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(openImageButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Add space between buttons
        buttonPanel.add(trainImageButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(compressImageButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(decompressImageButton);
        buttonPanel.add(Box.createVerticalGlue()); // Push buttons upwards if extra space

        // Add panel to the frame
        frame.add(mainPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }


    private void openImage() {
        opr.openImage();
    }

    private void trainImage() {
        opr.trainImage();
    }

    private void compressImage() {
        opr.compressImage();
    }

    private void decompressImage() {
        opr.showCompressedImage();
    }
}
*/




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GUI {
    private Operations opr; // Declare Operations instance as a class member
    private JLabel imageLabel; // Label to display the image
    private JFrame mainFrame;
    private JPanel mainPanel, imagePanel, buttonPanel, buttonContainerPanel;
    private JButton newBtn, trainBtn, compressBtn, openBtn;

    public void createAndShowGUI(){
        imageLabel = new JLabel(); // Initialize the image label
        opr = new Operations(imageLabel); // Initialize Operations instance with imageLabel
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
        imagePanel.add(imageLabel); // Add the image label to the viewer panel
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
        newBtn = new JButton("NEW IMAGE");
        trainBtn = new JButton("TRAIN");
        compressBtn = new JButton("COMPRESS");
        openBtn = new JButton("OPEN COMPRESSED IMAGE");

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

        newBtn.addActionListener(e -> newImage());
        trainBtn.addActionListener(e -> trainImage());
        compressBtn.addActionListener(e -> compressImage());
        openBtn.addActionListener(e -> openImage());
    }

    private void newImage() {
        opr.newBtn();
    }

    private void trainImage() {
        opr.trainBtn();
    }

    private void compressImage() {
        opr.compressBtn();
    }

    private void openImage() {
        opr.openBtn();
    }
}