import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ButtonOperations implements ActionListener{
    private JFrame mainFrame;
    private final HuffmanImageCompressor huff;
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    public JLabel imageLabelUncompressed, imageLabelCompressed, uncompressedFileSizeLabel, compressedFileSizeLabel;
    public File selectedFile, selectedFolder;

    public ButtonOperations(){
        imageLabelUncompressed = new JLabel();
        imageLabelCompressed = new JLabel();
        uncompressedFileSizeLabel = new JLabel();
        compressedFileSizeLabel = new JLabel();
        this.huff = new HuffmanImageCompressor();
        initializeButtons();
        addButtonListeners();
    }

    private void initializeButtons(){
        newBtn = new JButton("NEW");
        trainBtn = new JButton("TRAIN");
        compressBtn = new JButton("COMPRESS");
        openBtn = new JButton("OPEN");
    }

    private void addButtonListeners(){
        newBtn.addActionListener(this);
        trainBtn.addActionListener(this);
        compressBtn.addActionListener(this);
        openBtn.addActionListener(this);
    }
    
    public void newBtnPressed(){
        JFileChooser fileChooser = new JFileChooser();
        JFileChooser folderChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image File");
        folderChooser.setDialogTitle("Select Folder to Save Compressed Image");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(mainFrame, "Selected file: " + selectedFile.getAbsolutePath());
            renderImage(selectedFile, 1);
            labelFileSize(selectedFile, 1);
            System.out.println("File Size: " + selectedFile.length());
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int choice = folderChooser.showSaveDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION){
                selectedFolder = folderChooser.getSelectedFile();
                JOptionPane.showMessageDialog(mainFrame, "Selected Output Folder: " + selectedFolder.getAbsolutePath());
            }else JOptionPane.showMessageDialog(mainFrame, "No Chosen Directory");
        }else JOptionPane.showMessageDialog(mainFrame, "Open Cancelled");
    }

    public void trainBtnPressed(){
        try{
            huff.compressImage(selectedFile, selectedFolder.getAbsolutePath());
        }catch(IOException ex){}
    }

    public void compressBtnPressed(){
        System.out.println("COMPRESS BUTTON PRESSED");
        //Placeholder for compression logic
    }

    public void openBtnPressed(){
        try{
            System.out.println("Pressed");
            HuffmanImageCompressor.decompressImage("assets/compressed_files/kabaks.kabak", "assets/outputs/kabaks.png");
            System.out.println("Saved compression as: " + selectedFolder.getAbsolutePath() + "\\kabaks.kabak" + " ; " + selectedFolder.getAbsolutePath() + "\\new.png");
        }catch(IOException ex){}
        
            JFileChooser fileChooser = new JFileChooser();
            JFileChooser folderChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Image File");
            folderChooser.setDialogTitle("Select Folder to Save Compressed Image");
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
            fileChooser.addChoosableFileFilter(filter);
    
            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(mainFrame, "Selected file: " + selectedFile.getAbsolutePath());
                renderImage(selectedFile, 2);
                labelFileSize(selectedFile, 2);
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int choice = folderChooser.showSaveDialog(null);
                if(choice == JFileChooser.APPROVE_OPTION){
                    selectedFolder = folderChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(mainFrame, "Selected Output Folder: " + selectedFolder.getAbsolutePath());
                }else JOptionPane.showMessageDialog(mainFrame, "No Chosen Directory");
            }else JOptionPane.showMessageDialog(mainFrame, "Open Cancelled");
        
    }

    private void renderImage(File imageFile, int panelType){
        //ImagePanel height and width (idk pa pano ig kuha OOP wise)
        //Whole imagePanel is 861x1295, Each subImagePanel is 841x637
        int imagePanelHeight = 811;
        int imagePanelWidth = 607;
        int imageHeight, imageWidth;
        try{
            BufferedImage image = ImageIO.read(imageFile);
            imageHeight = image.getHeight();
            imageWidth = image.getWidth();
            if(imageHeight>= imagePanelHeight || imageWidth>= imagePanelWidth){
                while(imageHeight>= imagePanelHeight || imageWidth>= imagePanelWidth){ 
                    imageHeight = (int)(imageHeight * 0.9);
                    imageWidth = (int)(imageWidth * 0.9);
                }
            }else if(imageHeight < imagePanelHeight || imageWidth < imagePanelWidth){
                while(imageHeight < imagePanelHeight && imageWidth< imagePanelWidth){ 
                    imageHeight = (int)(imageHeight + 10);
                    imageWidth = (int)(imageWidth + 10);
                }
            }
            Image tmp = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            if (panelType == 1){
                imageLabelUncompressed.setIcon(imageIcon);
                imageLabelUncompressed.revalidate();
                imageLabelUncompressed.repaint();
            }else if (panelType == 2){
                imageLabelCompressed.setIcon(imageIcon);
                imageLabelCompressed.revalidate();
                imageLabelCompressed.repaint();
            }
            
        }catch (IOException e){}
    }

    public void labelFileSize(File file, int imageType){
        long bytes = file.length();
        final String[] units = {"B", "KB", "MB", "GB", "TB", "PB"};
        int unitIndex = 0;
        double size = bytes;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        String fileSizeLabel = String.format("%.2f %s", size, units[unitIndex]);
        if (imageType == 1){
            uncompressedFileSizeLabel.setText(fileSizeLabel);
            System.out.println(fileSizeLabel);
        }else if (imageType == 2){
            compressedFileSizeLabel.setText(fileSizeLabel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == newBtn){
            newBtnPressed();
        }else if(e.getSource() == trainBtn){
            if(selectedFile == null || selectedFolder == null){
                JOptionPane.showMessageDialog(null, "Please select an image file and output folder before training.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }else trainBtnPressed();
        }else if (e.getSource() == compressBtn){
            compressBtnPressed();
        }else if (e.getSource() == openBtn){
            openBtnPressed();
        }
    }

    // getters for buttons
    public JButton getNewBtn(){
        return newBtn;
    }

    public JButton getTrainBtn(){
        return trainBtn;
    }


    public JButton getCompressBtn(){
        return compressBtn;
    }

    public JButton getOpenBtn(){
        return openBtn;
    }
}
