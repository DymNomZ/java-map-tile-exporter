
import java.awt.Color;
import java.awt.Graphics;


public class Camera {
    
    private int tile_size, scale, delta_x, delta_y, prev_x, prev_y;
    public int x_pos, y_pos, screen_x, screen_y, map_length, map_height;

    public Camera(
        int SCREEN_WIDTH, int SCREEN_HEIGHT, 
        int tile_size, int scale, 
        int map_length, int map_height
    ){
        this.tile_size = tile_size;
        this.scale = scale;
        x_pos = (map_length * tile_size) / 2;
        y_pos = (map_height * tile_size) / 2;
        prev_x = x_pos;
        prev_y = y_pos;
        screen_x = SCREEN_WIDTH / 2;
        screen_y = SCREEN_HEIGHT / 2;
    }

    public void update_position(MouseHandler mouse, int new_scale, int def_tile_size){

        int old_scale = scale;
        int old_tile_size = tile_size;
        tile_size = new_scale * def_tile_size;
        scale = new_scale;

        //System.out.println(old_scale + " " + new_scale);

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
                }
           }
            
        }

        //Handles dragging movement
        if(mouse.is_dragged && mouse.left_pressed){

            if(mouse.clicked_x > mouse.mouse_x) delta_x = 10;
            else if(mouse.clicked_x < mouse.mouse_x) delta_x = -10;
            else if(mouse.clicked_x == mouse.mouse_x) delta_x = 0;

            if(mouse.clicked_y > mouse.mouse_y) delta_y = 10;
            else if(mouse.clicked_y < mouse.mouse_y) delta_y = -10;
            else if(mouse.clicked_y == mouse.mouse_y) delta_y = 0;  

             if(mouse.mouse_x < mouse.clicked_x - tile_size 
            || mouse.mouse_x > mouse.clicked_x + tile_size){
                x_pos += delta_x;
            }
            
             if(mouse.mouse_y < mouse.clicked_y - tile_size 
            || mouse.mouse_y > mouse.clicked_y + tile_size){
                y_pos += delta_y;
            }
            //store coords as basis for number of pixels prior to scaling
            prev_x = x_pos;
            prev_y = y_pos;
        }
        //System.out.println(x_pos + " " + y_pos + " " + prev_x + " " + prev_y);
        
    }

    void debug_display(Graphics G, int scale, int def_tile_size){

        G.setColor(Color.green);
        G.drawRect(screen_x, screen_y, tile_size, tile_size);
    }

}
