import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Resizer implements ActionListener {

    DataHandler data_handler;

    public Resizer(DataHandler data_Handler){
        this.data_handler = data_Handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == GUI.Buttons.RESIZE){
            
            data_handler.panel.grid.has_changes = true;

            String l = GUI.TextFields.MAP_LENGTH.getText();
            String h = GUI.TextFields.MAP_HEIGHT.getText();
            //check if both text fields have values in them
            if(l.length() != 0 && h.length() != 0 && data_handler.isDigitOnly(l) && data_handler.isDigitOnly(h)){
                int len = Integer.parseInt(l);
                int hei = Integer.parseInt(h);

                //MAX 500
                len = data_handler.checkMax(len, 500, "Max allowed length is 500, input will be set to 500");
                hei = data_handler.checkMax(hei, 500, "Max allowed height is 500, input will be set to 500");

                data_handler.panel.grid.resize(len, hei);
                data_handler.panel.cam.adjustPosition(
                    (len * data_handler.panel.tile_size) / 2, 
                    (hei * data_handler.panel.tile_size) / 2
                );

                GUI.TextFields.MAP_LENGTH.setText(String.format("%d", len));
                GUI.TextFields.MAP_HEIGHT.setText(String.format("%d", hei));
            }
            else {
                JOptionPane.showMessageDialog(null, "Both fields must have valid inputs");
            }
        }
    }
}
