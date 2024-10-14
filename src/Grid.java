
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Grid {
    
    private int tile_size;
    private int map_length, map_height;
    BufferedImage image = null;
    BufferedImage[][] tiles;
    int[][] indexes;
    
    public Grid(int col, int row){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("void.png"));
        } catch (IOException e) {
            System.out.println("Error loading void tile");
        }

        initialize_grid(col, row);
    }

    public int[][] get_tile_data(){
        return indexes;
    }

    public void initialize_grid(int col, int row){
        //System.out.println(col + " " + row);
        tiles = new BufferedImage[row][col];
        indexes = new int[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                tiles[i][j] = image;
                indexes[i][j] = 0;
            }
        }
    }

    void display(
        Graphics G, Camera cam, 
        int scale, int def_tile_size,
        int max_map_col, int max_map_row,
        BufferedImage tile, MouseHandler mouse, 
        int tile_index
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

                        //handle placing of tiles
                        if(mouse.is_clicked){
                            if(
                                //check if mouse coordinates matches with the tile's screen coords
                                (mouse.tile_x > screen_x && mouse.tile_x < screen_x + tile_size) &&
                                (mouse.tile_y > screen_y && mouse.tile_y < screen_y + tile_size)
                            ){
                                //if so, that means, the mouse is pointing at the tile, place it
                                tiles[grid_row][grid_col] = tile;
                                indexes[grid_row][grid_col] = tile_index;
                                //replacing the tile in the tiles array that will draw on the grid

                                //handle drawing mode
                                if(!mouse.middle_pressed){
                                    mouse.is_clicked = false;
                                }
                            }
                        }
                        
                        G.drawImage(
                            tiles[grid_row][grid_col], 
                            screen_x, screen_y, 
                            tile_size, tile_size, null
                        );
                    }

                grid_col++;
                
            }
            grid_col = 0;
            grid_row++;
        }
    }
}
