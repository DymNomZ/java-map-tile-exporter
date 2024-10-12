public class Camera {
    
    private int tile_size;
    public int x_pos, y_pos, screen_x, screen_y;

    public Camera(int x, int y, int tile_size, int map_length, int map_height){
        this.tile_size = tile_size;
        x_pos = (map_length * tile_size) / 2;
        y_pos = (map_height * tile_size) / 2;
        screen_x = x / 2;
        screen_y = y / 2;
    }
}
