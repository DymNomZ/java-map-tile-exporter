import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Panel extends JPanel {
    
    public final int SCREEN_WIDTH = 1300;
    public final int SCREEN_HEIGHT = 800;
    public final int DEF_TILE_SIZE = 14;

    public int scale = 1;
    public int tile_size = DEF_TILE_SIZE * scale;
    public int max_map_col = 25, max_map_row = 25;

    private Tile tile = null;
    private Tile blank = null;
    
    //hold data of loaded tiles on tile list
    private ArrayList<TileData> loaded_tile_data = new ArrayList<>();
    private ArrayList<TileData> final_tile_data = new ArrayList<>();

    private Camera cam;
    private final Grid grid;
    private DataHandler data_handler;
    private MouseHandler mouse;
    private TileHandler tile_handler;
    
    Sidebar sidebar;
    
    public Panel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());

        cam = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT, tile_size, scale, max_map_col, max_map_row);
        grid =  new Grid(max_map_col, max_map_row);
        data_handler = new DataHandler(this, grid);
        mouse = new MouseHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
        tile_handler = new TileHandler(SCREEN_WIDTH, SCREEN_HEIGHT, tile_size, scale, max_map_col, max_map_row);

        sidebar = new Sidebar(this, grid);
        //TileList tile_list = new TileList(this, settings);
        //settings.set_tile_list(tile_list);

        this.add(sidebar, BorderLayout.WEST);

        tile = new Tile("void.png", 0, "void", false);
        blank = new Tile("void.png", 0, "void", false);

        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
    }

    public void clear_tile_data(){
        loaded_tile_data.clear();
    }

    public void add_tile_data(Tile tile, JTextField input, JCheckBox solid_state){
        //add tiles and data from tile list
        loaded_tile_data.add(new TileData(tile, input, solid_state));
    }

    public void set_dimensions(int col, int row){
        //update because of new map dimensions
        max_map_col = col;
        max_map_row = row;

        //adjust cam position to new map dimensions
        cam.resize_adjust(
            (max_map_col * tile_size) / 2, 
            (max_map_row * tile_size) / 2
        );
    }

    public void updateGrid(int col, int row){
        //re-initialize_grid, wiping off map
        grid.initializeGrid(col, row);
        repaint();
    }

    public void get_selected_tile(Tile selected_tile, int new_index){
        //check if received tile is the same as previous, meaning, you are deselecting
        if(this.tile == selected_tile){
            this.tile = blank;
        }
        //else assign the new one
        else{
            this.tile = selected_tile;
            this.tile.index = new_index;
        }
    }

    public ArrayList<TileData> get_tile_cards(){
        return final_tile_data;
    }

    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){

            cam.update_position(mouse, scale, DEF_TILE_SIZE);
            tile_handler.update_position(mouse, scale, DEF_TILE_SIZE);

            //dictate how much scale will change when mouse wheel is scrolled
            scale = mouse.get_scale_factor();
            tile_size = scale * DEF_TILE_SIZE;

            repaint();
        }
    };

    public void startClock(){
        Timer timer = new Timer(10, timer_listener);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        grid.display(g, cam, scale, DEF_TILE_SIZE, 
            max_map_col, max_map_row, tile, mouse
        );
        tile_handler.display_tile(g, tile);
    }

}
