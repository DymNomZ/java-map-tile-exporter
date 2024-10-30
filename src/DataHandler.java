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
    
    public DataHandler(Panel panel){
        this.panel = panel;

        loaded_tile_data = new ArrayList<>();
        final_tile_data = new ArrayList<>();
    }

    public ArrayList<TileData> getFinalizedTileData(){
        return final_tile_data;
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

            String line = reader.readLine();
            int map_h = 0;
            int map_l = line.length() / 2; //because of spaces

            do{
                map_h++;
            }while ((reader.readLine()) != null);
            reader.close();

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

    public void load_map(ArrayList<TileData> loaded_tile_data){

        this.loaded_tile_data = loaded_tile_data;

        //refresh_list(true);
    }

//     public void refresh_list(boolean is_editing){

//         System.out.println("Refreshing");
//         if(!is_editing){
//             files = selected_folder.listFiles();
//             load_cards();
//         }else{
//             //clear cards to load tiles of loaded map
//             //panel.clear_tile_data();
//             cards.clear();
//             main_panel.removeAll();
//             main_panel.add(add_btn);
//             loaded_map_cards();
//         }
            
//     }

//     public void loaded_map_cards(){

//         for(TileData td : loaded_tile_data){

//             new_panel = new JPanel();
//             new_panel.setBackground(Color.BLACK);
//             new_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));

//             tile_image = new JLabel(new ImageIcon(td.tile.image));
//             tile_name = new JLabel(td.tile.name);
//             tile_name.setForeground(Color.WHITE);

//             idx_label = new JLabel("idx");
//             idx_label.setForeground(Color.WHITE);

//             idx_input = td.input;
            
//             if(td.tile.index != 0){
//                 idx_input.setText(Integer.toString(td.tile.index));
//             }else{
//                 idx_input.setText("0");
//             }
            
//             idx_input.addKeyListener(char_consumer);

//             solid_label = new JLabel("solid");
//             solid_label.setForeground(Color.WHITE);

//             solid_check = td.solid_state;
//             solid_check.setBackground(Color.BLACK);

//             //lamdaed again, handle checking
//             // solid_check.addItemListener((ItemEvent e) -> {
//             //     if (e.getStateChange() == ItemEvent.SELECTED) {
//             //         System.out.println("Tile is solid");
                    
//             //     } else {
//             //         System.out.println("Tile is not solid");
//             //     }
//             // });

//             new_panel.add(tile_image);
//             new_panel.add(tile_name);

//             mini_grid = new JPanel();
//             mini_grid.setBackground(Color.BLACK);
//             mini_grid.setLayout(new GridLayout(2, 2));
//             mini_grid.add(idx_label);
//             mini_grid.add(solid_label);
//             mini_grid.add(idx_input);
//             mini_grid.add(solid_check);

//             new_panel.add(mini_grid);

//             //Handles what tile is selecting for placing on grid
//             new_panel.addMouseListener(
//                 new CardHandler(
//                     panel, td.tile,
//                     idx_input
//                 )
//             );

//             //add to tile data array in panel for finalizing purposes
//             //panel.add_tile_data(td.tile, idx_input);
//             cards.add(new_panel);

//             //display selection of tiles on window
//             main_panel.add(new_panel);
//         }

//         revalidate();
// }


}
