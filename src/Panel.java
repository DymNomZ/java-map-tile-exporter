import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel {
    
    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 800;
    public final int DEF_TILE_SIZE = 14;

    public int scale = 1;
    public int tile_size = DEF_TILE_SIZE * scale;
    public int max_map_col = 25, max_map_row = 25;

    private BufferedImage tile_image = null;
    private BufferedImage blank = null;
    private int tile_index = 0;

    private Camera cam = new Camera(
        SCREEN_WIDTH, SCREEN_HEIGHT, 
        tile_size, scale, 
        max_map_col, max_map_row
        );
    private Grid grid = new Grid(max_map_col, max_map_row);
    private MouseHandler mouse = new MouseHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
    private TileHandler tile = new TileHandler(
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

        try {
            tile_image = ImageIO.read(getClass().getResourceAsStream("void.png"));
            blank = ImageIO.read(getClass().getResourceAsStream("void.png"));
        } catch (IOException e) {
            System.out.println("Error loading void tile");
        }
    }

    public void set_dimensions(int col, int row){
        max_map_col = col;
        max_map_row = row;
        //System.out.println(col + " " + row);
        cam.resize_adjust(
            (max_map_col * tile_size) / 2, 
            (max_map_row * tile_size) / 2
        );
        grid.initialize_grid(col, row);
        repaint();
    }

    public void get_selected_tile(BufferedImage tile_image, int tile_index){
        //check if received tile_image is the same as previous, meaning, you are deselecting
        if(this.tile_image == tile_image){
            this.tile_image = blank;
            this.tile_index = 0;
        }
        //else assign the new one
        else{
            this.tile_image = tile_image;
            this.tile_index = tile_index;
        }
    }

    public int[][] get_tile_data(){
        return grid.get_tile_data();
    }

    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){

            cam.update_position(mouse, scale, DEF_TILE_SIZE);
            tile.update_position(mouse, scale, DEF_TILE_SIZE);

            //dictate how much scale will change when mouse wheel is scrolled
            scale = mouse.get_scale_factor();

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
        grid.display(
            g, cam, scale, DEF_TILE_SIZE, 
            max_map_col, max_map_row, tile_image, mouse, 
            tile_index
        );
        //cam.debug_display(g, scale, DEF_TILE_SIZE);
        tile.display_tile(g, scale, DEF_TILE_SIZE, tile_image);
    }

}
