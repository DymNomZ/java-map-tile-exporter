import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TileList extends JPanel {

    private final GridBagLayout gb_layout;
    private final GridBagConstraints gbc;

    DataHandler data_handler;
    
    public TileList(DataHandler data_handler){

        this.data_handler = data_handler;

        this.setPreferredSize(new Dimension(250, 800));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        gb_layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5,0, 5);
        
        GUI.Panels.TILE_LIST_HEADER.setLayout(gb_layout);
        GUI.Panels.TILE_LIST_HEADER.setBackground(Color.BLACK);

        //adding components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Labels.TILE_LIST, gbc);

        gbc.gridy = 1;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Buttons.ADD, gbc);
        
        add(GUI.Panels.TILE_LIST_HEADER);
        GUI.Buttons.ADD.addActionListener(new ClickListener(data_handler));

        GUI.Panels.TILE_LIST.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        GUI.Panels.TILE_LIST.setBackground(Color.WHITE);

        //make tile list scrollable when there are too many tiles;
        JScrollPane scroll_pane = new JScrollPane();
        scroll_pane.add(GUI.Panels.TILE_LIST);

        add(scroll_pane);
        
    }

    public void loadTiles(ArrayList<TileData> tiles){
        
        //Display tiles via JLabels
        for(TileData t : tiles){

            JLabel tile_holder = new JLabel(new ImageIcon(t.tile.image));

            GUI.Panels.TILE_LIST.add(tile_holder);
        }
    }

}
