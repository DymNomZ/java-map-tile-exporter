import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewListener implements ActionListener {

    DataHandler data_handler;

    public NewListener(DataHandler data_handler) {
        this.data_handler = data_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == GUI.Buttons.NEW){
            String l = GUI.TextFields.MAP_LENGTH.getText();
            String h = GUI.TextFields.MAP_HEIGHT.getText();
            //check if both text fields have values in them
            if(l.length() != 0 && h.length() != 0){

                data_handler.panel.grid.has_changes = false;

                Saver temp = new Saver(data_handler);
                temp.save();

                int len = Integer.parseInt(l);
                int hei = Integer.parseInt(h);

                data_handler.panel.grid.initializeGrid(len, hei);
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
