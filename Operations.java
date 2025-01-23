
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Operations {
  private JLabel imageLabel;

  public Operations(JLabel imageLabel) {
    this.imageLabel = imageLabel;
  }
  public void openImage(){
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Open Image File");
    fileChooser.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
    fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            renderImage(selectedFile); // Call renderImage to display the selected image
        } else {
            System.out.println("Open command canceled");
        }
  }

  public void trainImage(){

  }

  public void compressImage(){

  }

  public void showCompressedImage(){

  }

   public void renderImage(File imageFile) {
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
}






