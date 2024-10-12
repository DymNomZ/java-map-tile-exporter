
public class Camera {
    
    private int tile_size, delta_x, delta_y;
    public int x_pos, y_pos, screen_x, screen_y, map_length, map_height;
    public int screen_half_x, screen_half_y;

    public Camera(
        int SCREEN_WIDTH, int SCREEN_HEIGHT, 
        int tile_size, 
        int map_length, int map_height
    ){
        this.tile_size = tile_size;
        x_pos = (map_length * tile_size) / 2;
        y_pos = (map_height * tile_size) / 2;
        screen_x = SCREEN_WIDTH / 2;
        screen_y = SCREEN_HEIGHT / 2;
        screen_half_x = SCREEN_WIDTH / 2;
        screen_half_y = SCREEN_HEIGHT / 2;
    }

    public void update_position(MouseHandler mouse){
        
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
            
        }
        
    }

}
