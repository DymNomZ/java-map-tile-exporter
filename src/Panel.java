import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Panel extends JPanel {
    
    public final int SCREEN_WIDTH = 1500;
    public final int SCREEN_HEIGHT = 800;
    public final int DEF_TILE_SIZE = 14;
    public final int DEF_TILE_COL = 25, DEF_TILE_ROW = 25;

    public int scale = 1;
    public int tile_size = DEF_TILE_SIZE * scale;

    private Tile tile = null;
    private Tile blank = null;

    private MouseHandler mouse;
    private Cursor cursor;
    private final DataHandler data_handler;

    public Camera cam;
    public final Grid grid;
    
    public Settings settings;
    public TileList tile_list;
    
    public Panel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());

        cam = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT, tile_size, scale, DEF_TILE_COL, DEF_TILE_ROW);
        grid =  new Grid(DEF_TILE_COL, DEF_TILE_ROW);
        mouse = new MouseHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
        cursor = new Cursor(SCREEN_WIDTH, SCREEN_HEIGHT, tile_size, scale, DEF_TILE_COL, DEF_TILE_ROW);

        data_handler = new DataHandler(this);
        settings = new Settings(data_handler);
        tile_list = new TileList(data_handler);

        this.add(settings, BorderLayout.WEST);
        this.add(tile_list, BorderLayout.EAST);

        tile = new Tile("void.png", 0, "void", false);
        blank = new Tile("void.png", 0, "void", false);

        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
    }

    public void add_tile_data(Tile tile, JTextField input, JCheckBox solid_state){
        //add tiles and data from tile list
        //loaded_tile_data.add(new TileData(tile, input, solid_state));
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

    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){

            cam.updatePosition(mouse, scale, DEF_TILE_SIZE);
            cursor.updatePosition(mouse, scale, DEF_TILE_SIZE);

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
        grid.display(g, cam, scale, DEF_TILE_SIZE, tile, mouse);
        cursor.displayTile(g, tile);
    }

}
