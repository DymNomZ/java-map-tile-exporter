import java.io.File;
import java.util.ArrayList;

public class TileListHandler {

    ArrayList<Tile> loaded_tiles = new ArrayList<>();

    TileList tile_list;
    
    public TileListHandler(TileList tile_list){
        this.tile_list = tile_list;
    }

    //for Tile list
    public ArrayList<Tile> getLoaded_tiles(){
        return loaded_tiles;
    }

    //prevent adding the same tile in tile list
    public boolean isDuplicate(String check_name){

        //retrun cause tile data is still empty
        if(loaded_tiles.isEmpty()){
            System.out.println("Tile List is empty, no need to check for duplicates");
            return false;
        }

        for(Tile t: loaded_tiles){
            if(t.name.equals(check_name)) return true;
        }

        return false;
    }

    public void loadTiles(File[] files){

        for (File file : files) {

            int dot_idx = file.getName().lastIndexOf('.');
            String texture_name = file.getName().substring(0, dot_idx);

            if(file.getName().endsWith(".png") && !isDuplicate(texture_name)){

                //create tile
                Tile tile = new Tile(file.getAbsolutePath(), texture_name, 0, false, false);

                loaded_tiles.add(tile);
                
            }
        }

        tile_list.addTiles(loaded_tiles);

    }
}
