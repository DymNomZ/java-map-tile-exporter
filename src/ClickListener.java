import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ClickListener implements ActionListener {
    
    DataHandler data_handler;

    public ClickListener(DataHandler data_handler){
        this.data_handler = data_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == GUI.Buttons.PAINT){
            if(!data_handler.panel.mouse.paint_control){
                GUI.Labels.PAINT.setForeground(Color.GREEN);
                GUI.Labels.PAINT.setText("On");
                data_handler.panel.mouse.paint_control = true;
            }
            else{
                GUI.Labels.PAINT.setForeground(Color.RED);
                GUI.Labels.PAINT.setText("Off");
                data_handler.panel.mouse.paint_control = false;
            }
        }
        if(e.getSource() == GUI.Buttons.BUCKET){
            if(!data_handler.panel.grid.bucket){
                GUI.Labels.BUCKET.setForeground(Color.GREEN);
                GUI.Labels.BUCKET.setText("On");
                data_handler.panel.grid.bucket = true;
            }
            else{
                GUI.Labels.BUCKET.setForeground(Color.RED);
                GUI.Labels.BUCKET.setText("Off");
                data_handler.panel.grid.bucket = false;
            }
        }
        if(e.getSource() == GUI.Buttons.SAVE_CHANGES){
            if(data_handler.queued_tile_handler != null){
                data_handler.panel.grid.has_changes = true;
                updateSelectedTile();
                //pass to panel via data handler to update on grid and settings
                data_handler.panel.updateSelectedTile(data_handler.queued_tile_handler.tile, true);
                data_handler.panel.settings.editTileProperties(data_handler.queued_tile_handler);
                data_handler.panel.grid.updateGridTiles(data_handler.queued_tile_handler.tile);
                System.out.println("Changes saved!");
            }
        }
    }

    public void displayPopUp(){
        JOptionPane.showMessageDialog(null, "Dude, index must be a number");
    }

    public void updateSelectedTile(){

        //get states for booleans
        boolean new_solid_state = GUI.Checkboxes.SOLID.isSelected();
        boolean new_animated_state = GUI.Checkboxes.ANIMATED.isSelected();

        data_handler.queued_tile_handler.tile.is_solid = new_solid_state;
        data_handler.queued_tile_handler.tile.is_animated = new_animated_state;
    }
}
