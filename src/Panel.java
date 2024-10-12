import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel {
    
    public int scale = 2, def_tile_size = 32;
    public int tile_size = def_tile_size * scale;
    public int max_map_col = 50, max_map_row = 50;

    public final int MAX_SCREEN_ROW = 10, MAX_SCREEN_COL = 20, SCREEN_TILE_SIZE = 64;
    public final int SCREEN_WIDTH = SCREEN_TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = SCREEN_TILE_SIZE * MAX_SCREEN_ROW;

    Camera cam = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT, tile_size, max_map_col, max_map_row);

    Grid grid = new Grid(tile_size, max_map_col, max_map_row);

    MouseHandler mouse = new MouseHandler(tile_size, SCREEN_WIDTH, SCREEN_HEIGHT);
    
    public Panel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
    }

    private ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            cam.update_position(mouse);
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
        grid.display(g, cam);
    }

}
