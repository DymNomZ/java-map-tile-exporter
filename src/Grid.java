
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Grid {
    
    private int tile_size;
    private int map_length, map_height;
    BufferedImage image = null;
    
    public Grid(){
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("test.png"));
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    

    void display(
        Graphics G, Camera cam, 
        int scale, int def_tile_size,
        int max_map_col, int max_map_row
    ){

        map_length = max_map_col;
        map_height = max_map_row;
        tile_size = scale * def_tile_size;

        int grid_row = 0, grid_col = 0;
        int tile_x, tile_y, screen_x, screen_y;

        while(grid_row < map_height){

            tile_y = grid_row * tile_size;

            while(grid_col < map_length){

                tile_x = grid_col * tile_size;

                    //only draw tiles visible on the screen
                    if 
                    ((tile_x + tile_size > cam.x_pos - cam.screen_x && 
                    tile_x - tile_size < cam.x_pos + cam.screen_x) &&
                    (tile_y + tile_size > cam.y_pos - cam.screen_y && 
                    tile_y - tile_size < cam.y_pos + cam.screen_y))
                    {
                        screen_x = tile_x - cam.x_pos + cam.screen_x;    
                        screen_y = tile_y - cam.y_pos + cam.screen_y; 

                        G.drawRect(screen_x, screen_y, tile_size, tile_size);
                        
                        //G.drawImage(image, screen_x, screen_y, tile_size, tile_size, null);
                    }

                grid_col++;
                
            }
            grid_col = 0;
            grid_row++;
        }
    }
}
