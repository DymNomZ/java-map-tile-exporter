import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TileList extends JPanel {

    private final GridBagLayout gb_layout;
    private final GridBagConstraints gbc;

    JScrollPane scroll_pane;

    DataHandler data_handler;
    TileListHandler TL_handler;
    
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
        GUI.Panels.TILE_LIST_HEADER.setBackground(Color.BLACK);

        //adding components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Labels.TILE_LIST, gbc);

        gbc.gridy = 1;
        GUI.Panels.TILE_LIST_HEADER.add(GUI.Buttons.ADD, gbc);
        
        add(GUI.Panels.TILE_LIST_HEADER, BorderLayout.NORTH);
        GUI.Buttons.ADD.addActionListener(new ClickListener(data_handler, TL_handler));

        GUI.Panels.TILE_LIST.setLayout(new GridLayout(0, 5, 10, 10));
        GUI.Panels.TILE_LIST.setBackground(Color.BLACK);

        //make tile list scrollable when there are too many tiles;
        scroll_pane = new JScrollPane();
        scroll_pane.setViewportView(GUI.Panels.TILE_LIST);
        add(scroll_pane, BorderLayout.CENTER);
        
    }

    public void loadTiles(ArrayList<Tile> tiles){
        
        //Display tiles from tile list handler via JLabels
        for(Tile t : tiles){

            JLabel tile_holder = new JLabel(new ImageIcon(t.image));
            tile_holder.addMouseListener(new CursorHandler(data_handler, t));
            
            GUI.Panels.TILE_LIST.add(tile_holder);
        }

        revalidate();
    }

}
