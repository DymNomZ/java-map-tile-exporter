import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class DataHandler {

    public TileHandler queued_tile_handler;
    public boolean is_undoing = false, is_redoing = false;

    Panel panel;
    
    public DataHandler(Panel panel){
        this.panel = panel;
    }

    public boolean isDigitOnly(String str) {
        return Pattern.matches("[0-9]+", str);
    }

    public int checkMax(int value, int max, String message){
        if(value > max){
            JOptionPane.showMessageDialog(null, message);
            return max;
        }
        else{
            return value;
        }
    }

    public ArrayList<Tile> getusedTiles(){

        ArrayList<Tile> used_tiles = new ArrayList<>();
        Tile[][] grid_tiles = panel.grid.getTiles();

        //only add used tiles
        for(int i = 0; i < grid_tiles.length; i++){
            for(int j = 0; j < grid_tiles[0].length; j++){
                if(!used_tiles.contains(grid_tiles[i][j])){
                    used_tiles.add(grid_tiles[i][j]);
                }
            }
        }

        System.out.println("Used Tile Size: " + used_tiles.size());

        return used_tiles;
    }

    public Tile readImage(ZipFile zip, ZipEntry image, int[][] tile_data_indexes, int curr_idx){

        InputStream image_data_stream;
        BufferedImage tile_image = null;
        String tile_name = image.getName().substring(image.getName().lastIndexOf("$")+1, image.getName().lastIndexOf('.'));

        try {
            image_data_stream = zip.getInputStream(image);
            tile_image = ImageIO.read(image_data_stream);
            image_data_stream.close();

        } catch (IOException ex) {
            System.out.println("Error reading image");
        }

        int tile_index = tile_data_indexes[curr_idx][0];
        boolean solid_state = tile_data_indexes[curr_idx][1] == 1;
        boolean animated_state = tile_data_indexes[curr_idx][2] == 1;

        return new Tile(tile_image, tile_name, tile_index, solid_state, animated_state);

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
            reader.close();

            tile_data_stream = zip.getInputStream(tile_data);
            reader = new BufferedReader(new InputStreamReader(tile_data_stream));

            tile_data_indexes = new int[td_h][td_l];

            for(int i = 0; i < td_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < td_l; j++) {
                    tile_data_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            tile_data_stream.close();

        } catch (IOException ex) {
            System.out.println("Error reading tile data file");
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

            int map_h = 0;
            int map_l = reader.readLine().split(" ").length; //previous implementation was faulty due to not taking into account double digits or more which affects spacing

            do{
                map_h++;
            }while ((reader.readLine()) != null);
            reader.close();

            //update length and height text fields
            GUI.TextFields.MAP_LENGTH.setText(String.format("%d", map_l));
            GUI.TextFields.MAP_HEIGHT.setText(String.format("%d", map_h));

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