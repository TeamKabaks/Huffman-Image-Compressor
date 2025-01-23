import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonOperations implements ActionListener{
    private JButton newBtn, trainBtn, compressBtn, openBtn;
    private Operations operations;

    public ButtonOperations(){
        this.operations = new Operations(null);
        newBtn = new JButton("NEW");
        trainBtn = new JButton("TRAIN");
        compressBtn = new JButton("COMPRESS");
        openBtn = new JButton("OPEN");
        addButtonListeners();
    }

    public final void addButtonListeners(){
        newBtn.addActionListener(this);
        trainBtn.addActionListener(this);
        compressBtn.addActionListener(this);
        openBtn.addActionListener(this);
    }

    public void newBtnPressed(){
        System.out.println("NEW BUTTON PRESSED");
        operations.openImage();
    }

    public void trainBtnPressed(){
        System.out.println("TRAIN BUTTON PRESSED");
    }

    public void compressBtnPressed(){
        System.out.println("COMPRESS BUTTON PRESSED");
    }

    public void openBtnPressed(){
        System.out.println("OPEN BUTTON PRESSED");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newBtn) {
            newBtnPressed();
        } else if (e.getSource() == trainBtn) {
            trainBtnPressed();
        } else if (e.getSource() == compressBtn) {
            compressBtnPressed();
        } else if (e.getSource() == openBtn){
            openBtnPressed();
        }
    }

    // Getters 
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
