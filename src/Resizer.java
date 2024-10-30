import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resizer implements ActionListener {

    DataHandler data_handler;

    public Resizer(DataHandler data_Handler){
        this.data_handler = data_Handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == GUI.Buttons.RESIZE){
            String l = GUI.TextFields.MAP_LENGTH.getText();
            String h = GUI.TextFields.MAP_HEIGHT.getText();
            //check if both text fields have values in them
            if(l.length() != 0 && h.length() != 0){
                int len = Integer.parseInt(l);
                int hei = Integer.parseInt(h);

                data_handler.panel.grid.resize(len, hei);
                data_handler.panel.cam.adjustPosition(
                    (len * data_handler.panel.tile_size) / 2, 
                    (hei * data_handler.panel.tile_size) / 2
                );

                GUI.TextFields.MAP_LENGTH.setText(String.format("%d", len));
                GUI.TextFields.MAP_HEIGHT.setText(String.format("%d", hei));
            }
            else {
                //POP-UP DAIALOG HERE
            }
        }
    }
}
