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
    
    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 800;
    public final int DEF_TILE_SIZE = 14;

    public int scale = 1;
    public int tile_size = DEF_TILE_SIZE * scale;
    public int max_map_col = 25, max_map_row = 25;

    private Tile tile = null;
    private Tile blank = null;
    
    //will hold map tiles for finalizing
    private Tile[][] map_tiles;

    //hold data of loaded tiles on tile list
    private ArrayList<TileData> loaded_tile_data = new ArrayList<>();
    private ArrayList<TileData> final_tile_data = new ArrayList<>();
    private int loaded_tile_data_idx = 0;
    private boolean loaded_tile_solid_state = false;

    private Camera cam = new Camera(
        SCREEN_WIDTH, SCREEN_HEIGHT, 
        tile_size, scale, 
        max_map_col, max_map_row
        );
    private final Grid grid = new Grid(max_map_col, max_map_row);
    private MouseHandler mouse = new MouseHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
    private TileHandler tile_handler = new TileHandler(
        SCREEN_WIDTH, SCREEN_HEIGHT, 
        tile_size, scale, 
        max_map_col, max_map_row
        );
    
    public Panel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);

        tile = new Tile("void.png", 0, "void", false);
        blank = new Tile("void.png", 0, "void", false);
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
        //System.out.println(col + " " + row);

        //adjust cam position to new map dimensions
        cam.resize_adjust(
            (max_map_col * tile_size) / 2, 
            (max_map_row * tile_size) / 2
        );
    }

    public void repaint_grid(int col, int row){
        //re-initialize_grid, wiping off map
        grid.initialize_grid(col, row);
        repaint();
    }

    public void display_loaded_map_tiles(int[][] map_indexes, ArrayList<TileData> tile_data){

        //update tile data
        this.loaded_tile_data.clear();
        this.loaded_tile_data.addAll(tile_data);

        //update dimensions
        set_dimensions(map_indexes[0].length, map_indexes.length);

        grid.load_map_tiles(map_indexes, tile_data);
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

    public void finalize_tiles(){
        //finalize tiles to handle final index before writing to txt file
        //This is to ensure index is correct

        //Gets the tiles on the grid
        map_tiles = grid.get_map_data();

        //No need to check, since ArrayList, will just not execute if empty
        for(TileData t : loaded_tile_data){
            for(int i = 0; i < map_tiles.length; i++){
                for(int j = 0; j < map_tiles[i].length; j++){

                    if(t.input.getText().length() != 0){
                        loaded_tile_data_idx = Integer.parseInt(t.input.getText());
                    }
                    //loaded_tile_data_idx = t.tile.index;

                    loaded_tile_solid_state = t.solid_state.isSelected();

                    //ensure inputted index in textfield will be applied
                    //if tile exists on the grid
                    if(t.tile == map_tiles[i][j]){
                        if(loaded_tile_data_idx != map_tiles[i][j].index){
                            map_tiles[i][j].index = loaded_tile_data_idx;
                        }

                        if(loaded_tile_solid_state != map_tiles[i][j].is_solid){
                            map_tiles[i][j].is_solid = loaded_tile_solid_state;
                        }
                        System.out.println(t.tile.name);
                        if(!(final_tile_data.contains(t))) final_tile_data.add(t);
                        System.out.println("size: " + final_tile_data.size());
                    }
                    //if not update tile still
                    else {
                        t.tile.index = loaded_tile_data_idx;
                        t.tile.is_solid = loaded_tile_solid_state;
                    }
                }
            }
        }
    }

    public Tile[][] get_map_data(){
        //send finalized map tiles for saving
        finalize_tiles();
        return map_tiles;
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

    public void start_clock(){
        Timer timer = new Timer(10, timer_listener);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        grid.display(g, cam, scale, DEF_TILE_SIZE, 
            max_map_col, max_map_row, tile, mouse
        );
        //cam.debug_display(g, scale, DEF_TILE_SIZE);
        tile_handler.display_tile(g, tile);
    }

}
