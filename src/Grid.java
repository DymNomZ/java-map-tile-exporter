import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Grid {
    
    private int tile_size;
    private int map_length, map_height;
    private final Tile DEFAULT_TILE;

    public boolean has_changes = false;
    public boolean bucket = false;

    Point current; // to track latest point
    Stack<Point> undo = new Stack<>();
    Stack<Point> redo = new Stack<>();

    int counter = 0;

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
                        if(bucket && mouse.is_pressed){
                            if(
                                //check if mouse coordinates matches with the tile's screen coords
                                (mouse.tile_x > screen_x && mouse.tile_x < screen_x + tile_size) &&
                                (mouse.tile_y > screen_y && mouse.tile_y < screen_y + tile_size)
                            ){
                                // current = new Point(
                                //     grid_col, grid_row, 
                                //     tiles[grid_row][grid_col], 
                                //     true
                                // );
                                handleBucket(grid_col, grid_row, tile, mouse);
                                has_changes = true;
                            }
                        }
                        else if(mouse.is_clicked || mouse.left_pressed){
                            if(
                                //check if mouse coordinates matches with the tile's screen coords
                                (mouse.tile_x > screen_x && mouse.tile_x < screen_x + tile_size) &&
                                (mouse.tile_y > screen_y && mouse.tile_y < screen_y + tile_size)
                            ){
                                //add old tile to undo stack
                                Point newp = new Point(
                                                    grid_col, grid_row, 
                                                    tiles[grid_row][grid_col], 
                                                    false
                                                );

                                //only add once, previously multiple copies were added due to clock speed (milliseconds)
                                if(!checkIfDuplicate(newp) && !bucket){
                                    undo.push(newp);
                                    System.out.println("Undo Pushed: " + newp.x + " " + newp.y + " " 
                                    + newp.tile.name + " " + newp.tile.is_solid + " " + newp.tile.is_animated);
                                }

                                counter++;
                                

                                //check if redo is not empty, if it is not empty therefore the past is changed delete the future
                                if(!redo.isEmpty()){
                                    redo.clear();
                                    System.out.println("Redo cleared! Redo size: " + redo.size());
                                }
                                
                                //if so, that means, the mouse is pointing at the tile, place it
                                tiles[grid_row][grid_col] = tile;
                                //replacing the tile in the tiles array that will draw on the grid

                                //assign to current 
                                current = newp;
                                
                                //detect changes
                                if(!tile.name.equals("void")) has_changes = true;
                            }
                        }else counter = 0;
                        
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

    private boolean checkIfDuplicate(Point check){

        if(undo.isEmpty()) return false;

        Point p = undo.peek();
       
        return (p.x == check.x && p.y == check.y && p.tile.name.equals(check.tile.name) && p.is_flooded == check.is_flooded);
       
    }

    public void performUndo(){

        //only perform if undo stack is not empty
        if(!undo.isEmpty()){
            //handle both flooded and unflooded tiles
            do{
                Point p = undo.pop();
                
                //place present into redo
                redo.push(current);
                // System.out.println("Redo Pushed from Undo: " + p.x + " " + p.y + " " 
                //                     + p.tile.name + " " + p.tile.is_solid + " " + p.tile.is_animated);
                // System.out.println("Redo size: " + redo.size());
                //undo on grid
                if(p.y < 0 || p.y >= map_height || p.x < 0 || p.x >= map_length) continue;
                else tiles[p.y][p.x] = p.tile;
                //replace current
                current = p;
                if(undo.isEmpty()) break;
            }while(undo.peek().is_flooded);
        }

        System.out.println("Undo complete! Undo size: " + undo.size());
        
    }

    public void performRedo(){

        //only perform if redo stack is not empty
        if(!redo.isEmpty()){
            //handle both flooded and unflooded tiles
            do{
                Point p = redo.pop();

                current = p;
                //place present into undo
                undo.push(current);
                // System.out.println("Undo Pushed from Redo: " + p.x + " " + p.y + " " 
                //                     + p.tile.name + " " + p.tile.is_solid + " " + p.tile.is_animated);
                //redo on grid
                if(p.y < 0 || p.y >= map_height || p.x < 0 || p.x >= map_length) continue;
                else tiles[p.y][p.x] = p.tile;
                //replace current
                
                if(redo.isEmpty()) break;
            }while(redo.peek().is_flooded);
        }

        System.out.println("Redo complete! Redo size: " + redo.size());
    }

    public void handleBucket(int base_x, int base_y, Tile tile, MouseHandler mouse){
        
        //get the clicked, original tile
        Tile old_tile = tiles[base_y][base_x];
        int flooded_tiles = 0;

        //if clicking on the same tile, just return
        if(old_tile.name.equals(tile.name)) return;

        Queue<Point> coordinates = new LinkedList<>();
        coordinates.add(new Point(base_x, base_y, old_tile, true));

        while(!coordinates.isEmpty()){
            //get latest coords to check
            Point check = coordinates.poll();
            current = check;

            if(
                //check if at borders
                check.y < 0 || check.y >= map_height || check.x < 0 || check.x >= map_length || 
                //check if point reaches the "walls" of another color 
                !tiles[check.y][check.x].name.equals(old_tile.name)
            ){
                continue;
            }
            //perform flooding
            else{
                //add old tile to undo stack
                // current.tile = tiles[check.y][check.x];

                //avoid duplocates
                if(!checkIfDuplicate(current)){
                    undo.push(current);
                    System.out.println("Undo Bucket Pushed: " + current.x + " " + current.y + " " 
                    + current.tile.name + " " + current.tile.is_solid + " " + current.tile.is_animated);
                }

                tiles[check.y][check.x] = tile;
                //queue the 4 other points (up, down, left, right)
                if(!checkIfValidCoords(check.x - 1, check.y)){
                    coordinates.add(new Point(check.x - 1, check.y, tiles[check.y][check.x - 1], true));
                }
                if(!checkIfValidCoords(check.x + 1, check.y)){
                    coordinates.add(new Point(check.x + 1, check.y, tiles[check.y][check.x + 1], true));
                }
                if(!checkIfValidCoords(check.x, check.y - 1)){
                    coordinates.add(new Point(check.x, check.y - 1, tiles[check.y - 1][check.x], true));
                }
                if(!checkIfValidCoords(check.x, check.y + 1)){
                    coordinates.add(new Point(check.x, check.y + 1, tiles[check.y + 1][check.x], true));
                }

                flooded_tiles++;
            }
        }

        mouse.is_pressed = false;

        System.out.println("Bucket complete! Total flooded tiles: " + flooded_tiles);
    }

    private boolean checkIfValidCoords(int x, int y){
        return (y < 0 || y >= map_height || x < 0 || x >= map_length);
    }
}
