
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ButtonOperations implements ActionListener {
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    public JLabel imageLabel;
    public File selectedFile;
    private HashMap<Integer, Double> rgbFrequency = new HashMap<>();

    public ButtonOperations(JLabel imageLabel) {
        this.imageLabel = imageLabel;
        initializeButtons();
        addButtonListeners();
    }

    private void initializeButtons() {
        newBtn = new JButton("NEW");
        trainBtn = new JButton("TRAIN");
        compressBtn = new JButton("COMPRESS");
        openBtn = new JButton("OPEN");
    }

    private void addButtonListeners() {
        newBtn.addActionListener(this);
        trainBtn.addActionListener(this);
        compressBtn.addActionListener(this);
        openBtn.addActionListener(this);
    }
    
    public void newBtnPressed() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            renderImage(selectedFile);
        } else {
            System.out.println("Open command canceled");
        }

    }

    public void trainBtnPressed() {
        try {
            BufferedImage image = ImageIO.read(selectedFile);

            // extract RGB values and calculate frequencies
            rgbFrequency.clear(); // Clear any previous data
            int totalPixels = image.getWidth() * image.getHeight();
            HashMap<Integer, Integer> rgbCount = new HashMap<>();

            StringBuilder allRgbBuilder = new StringBuilder();

        // collect RGB values and count frequencies\
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    rgbCount.put(rgb, rgbCount.getOrDefault(rgb, 0) + 1);

                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    allRgbBuilder.append(String.format("RGB(%d, %d, %d)\n", red, green, blue));
                }
            }

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    rgbCount.put(rgb, rgbCount.getOrDefault(rgb, 0) + 1);
                }
            }

            // convert counts to frequencies
            for (Map.Entry<Integer, Integer> entry : rgbCount.entrySet()) {
                int rgb = entry.getKey();
                int count = entry.getValue();
                double frequency = (double) count / totalPixels;
                rgbFrequency.put(rgb, frequency);
            }

            // save all RGB values to a file
        File allRgbFile = new File("all_rgb_values.txt");
        try (FileWriter writer = new FileWriter(allRgbFile)) {
            writer.write(allRgbBuilder.toString());
        }
        System.out.println("All RGB values saved to: " + allRgbFile.getAbsolutePath());

        // save unique RGB info to another file
        File uniqueRgbFile = new File("unique_rgb_info.txt");
        try (FileWriter writer = new FileWriter(uniqueRgbFile)) {
        // Write the number of unique RGB values
            writer.write(String.format("Unique RGB Values: %d\n", rgbFrequency.size()));
            writer.write(String.format("Total Pixels: %d\n\n", totalPixels));

            // Write details of each unique RGB value
            for (Map.Entry<Integer, Double> entry : rgbFrequency.entrySet()) {
                int rgb = entry.getKey();
                double frequency = entry.getValue();

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int count = (int) (frequency * totalPixels); // Back-calculate count for the output

                writer.write(String.format("RGB(%d, %d, %d): Count = %d, Frequency = %.6f\n",
                        red, green, blue, count, frequency));
            }
        }
        System.out.println("Unique RGB info saved to: " + uniqueRgbFile.getAbsolutePath());

            /* 
            // check for keys and values
            for (Map.Entry<Integer, Double> entry : rgbFrequency.entrySet()) {
                int rgb = entry.getKey();
                double frequency = entry.getValue();
    
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
    
                System.out.printf("RGB(%d, %d, %d): Frequency = %.6f\n", red, green, blue, frequency);
            }
            */
            System.out.println("RGB frequency data generated.");

        } catch (IOException e) {
            System.err.println("Failed to process the selected image.");
            e.printStackTrace();
        }
    }

    public void compressBtnPressed() {
        System.out.println("COMPRESS BUTTON PRESSED");
        // placeholder for compression logic
    }

    public void openBtnPressed() {
        System.out.println("OPEN BUTTON PRESSED");
        // placeholder for open logic
    }

    private void renderImage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
            imageLabel.revalidate();
            imageLabel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Double> getHashmapData() {
        return rgbFrequency;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newBtn) {
            newBtnPressed();
        } else if (e.getSource() == trainBtn) {
            trainBtnPressed();
        } else if (e.getSource() == compressBtn) {
            compressBtnPressed();
        } else if (e.getSource() == openBtn) {
            openBtnPressed();
        }
    }

    // getters for buttons
    public JButton getNewBtn() {
        return newBtn;
    }

    public JButton getTrainBtn() {
        return trainBtn;
    }


    public JButton getCompressBtn() {
        return compressBtn;
    }

    public JButton getOpenBtn() {
        return openBtn;
    }
}