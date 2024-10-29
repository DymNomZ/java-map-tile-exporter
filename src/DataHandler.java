import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class DataHandler {

    ArrayList<TileData> loaded_tile_data, final_tile_data;

    Panel panel;
    Grid grid;
    
    public DataHandler(Panel panel, Grid grid){
        this.panel = panel;
        this.grid = grid;

        loaded_tile_data = new ArrayList<>();
        final_tile_data = new ArrayList<>();
    }

    public ArrayList<TileData> getFinalizedTileData(){
        return final_tile_data;
    }

    //finalize tiles to to ensure all data is correct
    public Tile[][] finalizedTiles(){
        
        int loaded_tile_data_idx = 0;
        boolean loaded_tile_solid_state = false;

        //Gets the tiles from the grid
        Tile[][] map_tiles = grid.getMapTiles();

        for(TileData t : loaded_tile_data){
            for(int i = 0; i < map_tiles.length; i++){
                for(int j = 0; j < map_tiles[i].length; j++){

                    if(t.input.getText().length() != 0){
                        loaded_tile_data_idx = Integer.parseInt(t.input.getText());
                    }

                    loaded_tile_solid_state = t.solid_state.isSelected();

                    //if tile exists on the grid
                    if(t.tile == map_tiles[i][j]){
                        if(loaded_tile_data_idx != map_tiles[i][j].index){
                            map_tiles[i][j].index = loaded_tile_data_idx;
                        }

                        if(loaded_tile_solid_state != map_tiles[i][j].is_solid){
                            map_tiles[i][j].is_solid = loaded_tile_solid_state;
                        }
                        System.out.println(t.tile.name);
                        if(!(final_tile_data.contains(t))) final_tile_data.add(t);
                        System.out.println("size: " + final_tile_data.size());
                    }
                    //if not update tile still
                    else {
                        t.tile.index = loaded_tile_data_idx;
                        t.tile.is_solid = loaded_tile_solid_state;
                    }
                }
            }
        }

        return map_tiles;
    }

    public void buildLoadedMap(int[][] map_indexes, ArrayList<TileData> tile_data){

        //clear contents previously used for reading images
        //this.loaded_tile_data.clear();
        //this.loaded_tile_data.addAll(tile_data);

        //update dimensions
        panel.set_dimensions(map_indexes[0].length, map_indexes.length);

        grid.loadMapTiles(map_indexes, loaded_tile_data);
    }

    public ArrayList<TileData> readImages(ZipFile zip, ZipEntry image, int[][] tile_data_indexes){

        InputStream image_data_stream;
        BufferedImage tile_image;
        String tile_name = image.getName().substring(0, image.getName().lastIndexOf('.'));
        int curr_idx = 0;

        try {
            image_data_stream = zip.getInputStream(image);
            tile_image = ImageIO.read(image_data_stream);
            image_data_stream.close();

            loaded_tile_data.add(
                new TileData(
                    new Tile(
                        tile_data_indexes[curr_idx][0], 
                        (tile_data_indexes[curr_idx][1] == 1),
                        tile_name, tile_image), 
                    new JTextField(tile_data_indexes[curr_idx][0]),
                    new JCheckBox("", (tile_data_indexes[curr_idx][1] == 1)) 
                )
            );

            curr_idx++;

        } catch (IOException ex) {
            System.out.println("Error reading images");
        }

        return loaded_tile_data;

    }

    public int[][] readTileData(ZipFile zip, ZipEntry tile_data){
        
        InputStream tile_data_stream;
        BufferedReader reader;
        int[][] tile_data_indexes = null;

        try {
            tile_data_stream = zip.getInputStream(tile_data);
            reader = new BufferedReader(new InputStreamReader(tile_data_stream));

            String line = reader.readLine();
            int td_h = 0;
            int td_l = line.length() / 2;

            do{
                td_h++;
            }while ((reader.readLine()) != null);
            //System.out.println("Tile Data Length: " + td_l + " Height: " + td_h);
            reader.close();

            //new tile_data cause previous inputs were already consumed by reader
            //new reader cause the previous one is at the end of the file = null
            tile_data_stream = zip.getInputStream(tile_data);
            reader = new BufferedReader(new InputStreamReader(tile_data_stream));

            tile_data_indexes = new int[td_h][td_l];

            for(int i = 0; i < td_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < td_l; j++) {
                    //System.out.println(raw_indexes[j]);
                    tile_data_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            tile_data_stream.close();

        } catch (IOException ex) {
            System.out.println("Error reading tile data");
        }

        return tile_data_indexes;
    }

    public int[][] readMap(ZipFile zip, ZipEntry map){

        InputStream map_data_stream;
        BufferedReader reader;
        int[][] loaded_map_indexes = null;

        try {
            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            String line = reader.readLine();
            int map_h = 0;
            int map_l = line.length() / 2; //because of spaces

            do{
                map_h++;
            }while ((reader.readLine()) != null);
            reader.close();

            //new map cause previous inputs were already consumed by reader
            //new reader cause the previous one is at the end of the file = null
            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            loaded_map_indexes = new int[map_h][map_l];

            for(int i = 0; i < map_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < map_l; j++) {
                    loaded_map_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            map_data_stream.close();

        } catch (IOException ex) {
            System.out.println("Error reading map");
        }
        
        return loaded_map_indexes;
    }
}
