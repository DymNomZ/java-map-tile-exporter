import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Grid {
    
    private int tile_size;
    private int map_length, map_height;
    private final Tile DEFAULT_TILE;

    Tile[][] tiles;
    
    public Grid(int col, int row){

        DEFAULT_TILE = new Tile("void.png", "void", 0, false, false);

        initializeGrid(col, row);
    }

    private Tile[][] prepareGrid(int col, int row){
        Tile[][] temp = new Tile[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                temp[i][j] = DEFAULT_TILE;
            }
        }

        return temp;
    }

    //fill grid array with void tiles
    public void initializeGrid(int col, int row){
        tiles = prepareGrid(col, row);

        map_length = col;
        map_height = row;
    }

    //resize grid
    public void resize(int new_col, int new_row){

        //Do not perform if new values for col and row are jsut the same to save overhead
        if(new_col != map_length || new_row != map_height){

            int base_col = map_length;
            int base_row = map_height;

            //copy previous map to new map array;
            //if a value is bigger, base off the old array
            //else base off new map size
            if(new_col > map_length) base_col = tiles[0].length;
            else base_col = new_col;

            if(new_row > map_height) base_row = tiles.length;
            else base_row = new_row;

            Tile[][] new_map = prepareGrid(new_col, new_row);

            for(int i = 0; i < base_row; i++){
                System.arraycopy(tiles[i], 0, new_map[i], 0, base_col);
            }

            //replace with new sizes
            map_length = new_col;
            map_height = new_row;

            //replace tiles with new array
            tiles = new_map;

        }

    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public void updateGridTiles(Tile updated_tile){

        int tiles_updated = 0; // for validation

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                //update the same tile via name comparing
                if(tiles[i][j].name.equals(updated_tile.name)){
                    tiles[i][j] = updated_tile;
                    tiles_updated++;
                }
            }
        }

        System.out.println("Updating tiles complete! No. of tiles updated: " + tiles_updated);

    }


    public void loadMapTiles(int[][] tile_indexes, ArrayList<Tile> tiles){

        map_length = tile_indexes[0].length;
        map_height = tile_indexes.length;

        this.tiles = new Tile[map_height][map_length];
        for(int i = 0; i < map_height; i++){
            for(int j = 0; j < map_length; j++){
                
                //check which tile in tile data matches index
                if(tile_indexes[i][j] != 0){
                    for(Tile t : tiles){
                        if(t.index == tile_indexes[i][j]){
                            this.tiles[i][j] = t;
                            break;
                        }
                    }
                }else{
                    this.tiles[i][j] = DEFAULT_TILE;
                }
                    
            }
        }
    }

    void display(
        Graphics G, Camera cam, 
        int scale, int def_tile_size,
        Tile tile, MouseHandler mouse
    ){

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

                        //handle drawing mode
                        if(!mouse.paint_control){
                            mouse.is_clicked = false;
                            GUI.Labels.PAINT.setForeground(Color.RED);
                            GUI.Labels.PAINT.setText("Off");
                        }else{
                            GUI.Labels.PAINT.setForeground(Color.GREEN);
                            GUI.Labels.PAINT.setText("On");
                        }

                        //handle placing of tiles
                        if(mouse.is_clicked || mouse.left_pressed){
                            if(
                                //check if mouse coordinates matches with the tile's screen coords
                                (mouse.tile_x > screen_x && mouse.tile_x < screen_x + tile_size) &&
                                (mouse.tile_y > screen_y && mouse.tile_y < screen_y + tile_size)
                            ){
                                //if so, that means, the mouse is pointing at the tile, place it
                                tiles[grid_row][grid_col] = tile;
                                //replacing the tile in the tiles array that will draw on the grid
                                
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
