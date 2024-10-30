import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cursor {

    private BufferedImage blank = null;
    private int tile_size, scale, prev_x, prev_y;
    public int x_pos, y_pos, screen_x = 0, screen_y = 0;
    public int map_length, map_height;
    
    public Cursor(
        int SCREEN_WIDTH, int SCREEN_HEIGHT, 
        int tile_size, int scale, 
        int map_length, int map_height
    ){
        this.tile_size = tile_size;
        this.scale = scale;

        //handle tile selected position, this is the tile hovering with the cursor
        x_pos = (map_length * tile_size) / 2;
        y_pos = (map_height * tile_size) / 2;
        prev_x = x_pos;
        prev_y = y_pos;

        //default is void
        try {
            blank = ImageIO.read(getClass().getResourceAsStream("void.png"));
        } catch (IOException e) {
            System.out.println("Error loading void tile");
        }
    }

    public void updatePosition(MouseHandler mouse, int new_scale, int def_tile_size){

        screen_x = mouse.tile_x;
        screen_y = mouse.tile_y;

        int old_scale = scale;
        int old_tile_size = tile_size;
        tile_size = new_scale * def_tile_size;
        scale = new_scale;

        //handle zooming
        if(tile_size != old_tile_size){
           if(tile_size > old_tile_size){
                x_pos += prev_x;
                y_pos += prev_y;
           }else if(tile_size < old_tile_size){
                //check if new scale is larger, just add appropriate amount of pixels to coords
                if(new_scale > old_scale){
                    x_pos += prev_x;
                    y_pos += prev_y;
                }
                //Divide by previous scale and multiply by new scale to adjust
                else{
                    if(x_pos != 0) x_pos = (x_pos /= old_scale) * new_scale;
                    if(y_pos != 0) y_pos = (y_pos /= old_scale) * new_scale;
                    //reset previous coords to downscaled coords
                    prev_x = x_pos;
                    prev_y = y_pos;
                }
           }
            
        }
        
    }

    public void displayTile(
        Graphics G, 
        Tile tile
    ){
        //check if it is not void tile, otherwise, not render, void tile represents nothing is selected
        if(tile.image != blank){
            G.drawImage(tile.image, screen_x, screen_y, tile_size, tile_size, null);
        }
    }
}
