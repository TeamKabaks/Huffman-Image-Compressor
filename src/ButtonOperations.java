import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonOperations implements ActionListener {
    private String fileName;
    private JFrame mainFrame;
    private final HuffmanImageCompressor huff;
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    public JLabel imageLabelUncompressed, imageLabelCompressed, uncompressedFileSizeLabel, compressedFileSizeLabel, statusLabel;
    public File selectedFile, selectedFolder, innputDir, huffDir, compresseedDir, outputDir;
    private String basePath = new File("").getAbsolutePath() + File.separator + "assets" + File.separator;
    private String inputFilePath = basePath + "inputs";
    private String huffTreeFilePath = basePath + "huff_files";
    private String compressedFilePath = basePath + "compressed_files";
    private String outputFilePath = basePath + "outputs";


    public ButtonOperations() {
        imageLabelUncompressed = new JLabel();
        imageLabelCompressed = new JLabel();
        uncompressedFileSizeLabel = new JLabel();
        compressedFileSizeLabel = new JLabel();
        statusLabel = new JLabel("Status |");
        this.huff = new HuffmanImageCompressor();
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
        String fileName;
        JFileChooser fileChooser = new JFileChooser();
        System.out.println(inputFilePath);
        fileChooser.setCurrentDirectory(new File(inputFilePath));
        fileChooser.setDialogTitle("Open Image File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",  "tiff", "png", "gif", "bmp");
        fileChooser.addChoosableFileFilter(filter);

        setStatus("Choosing new image...");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(mainFrame, "Selected file: " + selectedFile.getAbsolutePath());
            renderImage(selectedFile, 1);
            labelFileSize(selectedFile, 1);
            System.out.println("File Size: " + selectedFile.length());
        } else {
            setStatus("Choosing new image cancelled");
            JOptionPane.showMessageDialog(mainFrame, "Open Cancelled");
        }

        // moves selected file to the input folder
        moveFile(selectedFile.getAbsolutePath(), inputFilePath);
        fileName = selectedFile.getName();
        selectedFile = new File(inputFilePath + "/" + fileName);
        clearStatus();
        clearImage(2);
        clearLabelFileSize(2);
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        return (lastIndex == -1) ? "" : name.substring(lastIndex + 1);
    }

    public void trainBtnPressed() {
        if (selectedFile == null) {
            setStatus("Error no selected image");
            JOptionPane.showMessageDialog(mainFrame, "Please select an image file first.", "Error", JOptionPane.ERROR_MESSAGE);
            clearStatus();
            return;
        }
        selectedFolder = new File(huffTreeFilePath);
        try {
            fileName = getFileNameWithoutExtension(selectedFile.getName());
            String input = inputFilePath + "/" + selectedFile.getName();
            String huffmanTreePath = huffTreeFilePath + "/" + fileName.trim() + ".huff";
            setStatus("Creating Huffman tree...");
            huff.compressImage(input, huffmanTreePath, huffmanTreePath);
            setStatus("Huffman tree created!");
            JOptionPane.showMessageDialog(mainFrame, "Huffman tree created and saved to: " + huffmanTreePath);
        } catch (IOException ex) {
            setStatus("Error during training");
            JOptionPane.showMessageDialog(mainFrame, "Error during training: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        clearStatus();
    }

    private static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        
        if (lastDotIndex > 0) { // Ensures the dot is not at the start (e.g., ".hiddenfile")
            return fileName.substring(0, lastDotIndex);
        }
        return fileName; // No extension found, return the original name
    }

    public void compressBtnPressed() {
        if (selectedFile == null || selectedFolder == null) {
            setStatus("Error no selected image"); 
            JOptionPane.showMessageDialog(mainFrame, "Please select an image file and output folder first.", "Error", JOptionPane.ERROR_MESSAGE);
            clearStatus();
            return;
        }
    
        // Prompt the user to select a Huffman file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(huffTreeFilePath));
        fileChooser.setDialogTitle("Choose a Huffman File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Huffman Files", "huff");
        fileChooser.addChoosableFileFilter(filter);
    
        int result = fileChooser.showOpenDialog(mainFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            setStatus("Choosing huffman file cancelled");
            JOptionPane.showMessageDialog(mainFrame, "Huffman file selection cancelled.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        File huffmanFile = fileChooser.getSelectedFile();
        String huffmanTreePath = huffmanFile.getAbsolutePath(); // Use the selected file instead of auto-generating the path
        
        String compressedPath = compressedFilePath + "/" + fileName.trim() + ".kabak";

        try {
            setStatus("Compressing image...");
            huff.compressImage(selectedFile.getAbsolutePath(), compressedPath, huffmanTreePath);
            setStatus("Image compressed and saved");
            JOptionPane.showMessageDialog(mainFrame, "Image compressed and saved to: " + compressedPath);
        } catch (IOException ex) {
            setStatus("Error compressing image");
            JOptionPane.showMessageDialog(mainFrame, "Error during compression: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        clearStatus();
    }
    
    public void openBtnPressed() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(compressedFilePath));
        fileChooser.setDialogTitle("Open Compressed Image File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Compressed Files", "kabak");
        fileChooser.addChoosableFileFilter(filter);

        setStatus("Choosing compressed image...");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            setStatus("File selected");
            File compressedFile = fileChooser.getSelectedFile();
            File inputDirectory = new File(inputFilePath);
            System.out.println(compressedFile.getName());
            String fileName = compressedFile.getName().replace(".kabak", "");
            String huffmanTreePath = huffTreeFilePath + "/" + fileName + ".huff";
            String compressedTreePath = compressedFilePath + "/" + fileName + ".kabak";
            String outputImagePath;
            String inputFileName, inputPath = "";

            // Getting the corresponding input file
            if (inputDirectory.exists() && inputDirectory.isDirectory()){   
                File files[] = inputDirectory.listFiles();
                if (files != null){
                    for (File file : files){
                        inputFileName = file.getName();
                        if (inputFileName.startsWith(fileName + ".")){
                            selectedFile = file;
                            inputPath = inputFilePath + "/" + inputFileName;
                        }
                    }
                }
            }            

            String fileFormat = getFileExtension(selectedFile);
            if (fileFormat.equalsIgnoreCase("tiff")) {
                outputImagePath = outputFilePath + "/" + fileName + ".tiff";
            }else if(fileFormat.equalsIgnoreCase("bmp")){
                outputImagePath = outputFilePath + "/" + fileName + ".bmp";
            }else if(fileFormat.equalsIgnoreCase("png")){
                outputImagePath = outputFilePath + "/" + fileName + ".png";
            }else {
                // Handle unsupported file format, for example:
                setStatus("Unsupported file format");
                JOptionPane.showMessageDialog(mainFrame, "Unsupported file format: " + fileFormat, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                setStatus("Decompressing image...");
                huff.decompressImage(compressedTreePath, huffmanTreePath, outputImagePath);
                setStatus("Image decompressed!");
                renderImage(new File(inputPath), 1);                
                labelFileSize(new File(inputPath), 1);
                renderImage(new File(outputImagePath), 2);
                labelFileSize(new File(outputImagePath), 2);
                JOptionPane.showMessageDialog(mainFrame, "Decompressed image saved to: " + outputImagePath);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error during decompression: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            setStatus("Choosing image cancelled");
            JOptionPane.showMessageDialog(mainFrame, "Open Cancelled");
        }
        clearStatus();
    }
    
    private void renderImage(File imageFile, int panelType) {
        setStatus("Rendering image...");
        int imagePanelHeight = 811;
        int imagePanelWidth = 597;
        int imageHeight, imageWidth;
        try {
            BufferedImage image = ImageIO.read(imageFile);
            imageHeight = image.getHeight();
            imageWidth = image.getWidth();
            if (imageHeight >= imagePanelHeight || imageWidth >= imagePanelWidth) {
                while (imageHeight >= imagePanelHeight || imageWidth >= imagePanelWidth) {
                    imageHeight = (int) (imageHeight * 0.9);
                    imageWidth = (int) (imageWidth * 0.9);
                }
            } else if (imageHeight < imagePanelHeight || imageWidth < imagePanelWidth) {
                while (imageHeight < imagePanelHeight && imageWidth < imagePanelWidth) {
                    imageHeight = (int) (imageHeight + 10);
                    imageWidth = (int) (imageWidth + 10);
                }
            }
            Image tmp = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            if (panelType == 1) {
                imageLabelUncompressed.setIcon(imageIcon);
                imageLabelUncompressed.revalidate();
                imageLabelUncompressed.repaint();
            } else if (panelType == 2) {
                imageLabelCompressed.setIcon(imageIcon);
                imageLabelCompressed.revalidate();
                imageLabelCompressed.repaint();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // moves files that are not originally in the inputs folder
    private static void moveFile(String sourcePath, String destinationFolder) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationFolder, source.getFileName().toString());

        // Check if file already exists in destination
        if (Files.exists(destination)) {
            System.out.println("File already exists in the inputs folder. No action taken.");
            return;
        }

        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move file: " + e.getMessage());
        }
    }

    private void clearImage(int panelType) {
        if (panelType == 1) {
            imageLabelUncompressed.setIcon(null);
            imageLabelUncompressed.revalidate();
            imageLabelUncompressed.repaint();
        } else if (panelType == 2) {
            imageLabelCompressed.setIcon(null);
            imageLabelCompressed.revalidate();
            imageLabelCompressed.repaint();
        } else {
            System.err.println("Unknown panel type: " + panelType);
        }
    }

    public void labelFileSize(File file, int imageType) {
        long bytes = file.length();
        final String[] units = {"B", "KB", "MB", "GB", "TB", "PB"};
        int unitIndex = 0;
        double size = bytes;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        String fileSizeLabel = String.format("%.2f %s", size, units[unitIndex]);
        if (imageType == 1) {
            uncompressedFileSizeLabel.setText(fileSizeLabel);
        } else if (imageType == 2) {
            compressedFileSizeLabel.setText(fileSizeLabel);
        }
    }

    private void clearLabelFileSize(int panelType) {
        if (panelType == 1) {
            uncompressedFileSizeLabel.setText("");
        } else if (panelType == 2) {
            compressedFileSizeLabel.setText("");
        } else {
            System.err.println("Unknown panel type: " + panelType);
        }
    }
    
    private void setStatus(String statusString){
        statusLabel.setText("Status | " + statusString);
    }

    private void clearStatus(){
        statusLabel.setText("Status | ");
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

    // Getters for buttons
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