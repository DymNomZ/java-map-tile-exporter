
import java.awt.Graphics;

public class Grid {
    
    private int tile_size;
    private int map_length, map_height;
    Tile[][] tiles;
    
    public Grid(int col, int row){

        initialize_grid(col, row);
    }

    public Tile[][] get_map_data(){
        return tiles;
    }

    //fill grid array with void tiles
    public void initialize_grid(int col, int row){
        //System.out.println(col + " " + row);
        tiles = new Tile[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                tiles[i][j] = new Tile("void.png", 0, "void");
            }
        }
    }

    void display(
        Graphics G, Camera cam, 
        int scale, int def_tile_size,
        int max_map_col, int max_map_row,
        Tile tile, MouseHandler mouse
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
                                //replacing the tile in the tiles array that will draw on the grid

                                //handle drawing mode
                                if(!mouse.middle_pressed){
                                    mouse.is_clicked = false;
                                }
                            }
                        }
                        
                        G.drawImage(
                            tiles[grid_row][grid_col].image, 
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
