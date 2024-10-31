import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class TileList extends JPanel {

    private final GridBagLayout gb_layout;
    private final GridBagConstraints gbc;

    JScrollPane scroll_pane;

    DataHandler data_handler;
    TileListHandler TL_handler;

    Set<Tile> tiles = new HashSet<>();
    
    public TileList(DataHandler data_handler){

        this.data_handler = data_handler;
        this.TL_handler = new TileListHandler(this);

        this.setPreferredSize(new Dimension(250, 800));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());

        gb_layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5,0, 5);
        
        GUI.Panels.TILE_LIST_HEADER.setLayout(gb_layout);

        //adding components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Labels.TILE_LIST, gbc);

        gbc.gridy = 1;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Buttons.ADD, gbc);
        
        add(GUI.Panels.TILE_LIST_HEADER, BorderLayout.NORTH);
        GUI.Buttons.ADD.addActionListener(new AddListener(data_handler, TL_handler));

        GUI.Panels.TILE_LIST.setLayout(new GridLayout(0, 5, 10, 10));

        //make tile list scrollable when there are too many tiles;
        scroll_pane = new JScrollPane();
        scroll_pane.setViewportView(GUI.Panels.TILE_LIST);
        scroll_pane.getVerticalScrollBar().setUnitIncrement(20);
        add(scroll_pane, BorderLayout.CENTER);
        
    }

    public void addTiles(ArrayList<Tile> tiles){

        //Display tiles from tile list handler via JLabels
        for(Tile t : tiles){

            if(!this.tiles.contains(t)){
                JLabel tile_holder = new JLabel(new ImageIcon(t.image));
                tile_holder.addMouseListener(new TileHandler(data_handler, t));
                
                GUI.Panels.TILE_LIST.add(tile_holder);

                this.tiles.add(t);
            }
        }

        revalidate();
    }

    public void loadTiles(ArrayList<Tile> tiles){

        //Clear Tile List for loaded map
        GUI.Panels.TILE_LIST.removeAll();
        this.tiles.removeAll(tiles);

        for(Tile t : tiles){
            JLabel tile_holder = new JLabel(new ImageIcon(t.image));
            tile_holder.addMouseListener(new TileHandler(data_handler, t));
            
            GUI.Panels.TILE_LIST.add(tile_holder);

            this.tiles.add(t);
        }

        revalidate();
    }

}
