import java.awt.*;
import javax.swing.*;

public class GUI {
    public void createAndShowGUI(){
      
      JFrame frame = new JFrame("Image Compressor");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1200, 800);
      frame.setLocationRelativeTo(null);

      JPanel mainPanel = new JPanel(new BorderLayout());

      JPanel viewerPanel = new JPanel();
      viewerPanel.setPreferredSize(new Dimension(200, 200)); // Ensure it's square
      viewerPanel.setBackground(Color.LIGHT_GRAY); // Example background color

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(Color.WHITE); // Example background color
      buttonPanel.setPreferredSize(new Dimension(400, 200)); // Rectangular lengthwise

      mainPanel.add(viewerPanel, BorderLayout.CENTER);
      mainPanel.add(buttonPanel, BorderLayout.EAST);

      JButton openImageButton = new JButton("Open Image");
      JButton trainImageButton = new JButton("Train Image");
      JButton compressImageButton = new JButton("Compress Image");
      JButton decompressImageButton = new JButton("Decompress Image");

      openImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      trainImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      compressImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      decompressImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);


      /* 
        // Add action listeners for the buttons
      openImageButton.addActionListener(e -> openImage());
      trainImageButton.addActionListener(e -> trainImage());
      compressImageButton.addActionListener(e -> compressImage());
      decompressImageButton.addActionListener(e -> decompressImage());
      */

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

    /* 
    private void openImage() {
        Operations.openImage();
    }

    private void trainImage() {
        Operations.trainImage();
    }

    private void compressImage() {
        Operations.compressImage();
    }

    private void decompressImage() {
        Operations.decompressImage();
    }
    */
  }
