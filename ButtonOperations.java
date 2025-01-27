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
    public JLabel imageLabel;
    public File selectedFile, selectedFolder;

    public ButtonOperations(JLabel imageLabel){
        this.imageLabel = imageLabel;
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
            renderImage(selectedFile);
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
    }

    private void renderImage(File imageFile){
        //ImagePanel height and width (idk pa pano ig kuha OOP wise)
        int imagePanelHeight = 811; //original was 861
        int imagePanelWidth = 1245; //oridinal was 1295
        int imageHeight, imageWidth;
        try{
            BufferedImage image = ImageIO.read(imageFile);
            imageHeight = image.getHeight();
            imageWidth = image.getWidth();
            if(imageHeight>= imagePanelHeight || imageWidth>= imagePanelWidth){
                while(imageHeight>= imagePanelHeight || imageWidth>= imagePanelWidth){ 
                    imageHeight = (imageHeight - 10);
                    imageWidth = (imageWidth - 10);
                }
            }else if(imageHeight < imagePanelHeight || imageWidth < imagePanelWidth){
                while(imageHeight < imagePanelHeight && imageWidth< imagePanelWidth){ 
                    imageHeight = (imageHeight + 10);
                    imageWidth = (imageWidth + 10);
                }
            }
            Image tmp = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageLabel.setIcon(imageIcon);
            imageLabel.revalidate();
            imageLabel.repaint();
        }catch (IOException e){}
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
