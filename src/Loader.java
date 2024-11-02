import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JFileChooser;

public class Loader implements ActionListener {

    int[][] loaded_map_indexes = null;
    int[][] tile_data_indexes = null;
    ArrayList<Tile> loaded_tiles = new ArrayList<>();
    ArrayList<ZipEntry> png_entries = new ArrayList<>();
    
    DataHandler data_handler;

    public Loader(DataHandler data_handler) {
        this.data_handler = data_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JFileChooser file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int return_value = file_chooser.showOpenDialog(null);
        if (return_value == JFileChooser.APPROVE_OPTION) {
            
            File selected_zip = file_chooser.getSelectedFile();
            System.out.println("Selected zip: " + selected_zip);

            if(selected_zip != null){
                // Create a ZipFile object
                ZipFile zip_file;
                try {
                    zip_file = new ZipFile(selected_zip.getAbsolutePath());

                    // Get an enumeration of the entries in the zip file
                    Enumeration<? extends ZipEntry> entries = zip_file.entries();

                    // Iterate over the entries
                    System.out.println("Zip contents: ");
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();

                        //read operations:
                        //get tile data indexes
                        if(entry.getName().endsWith("data.txt")){
                            tile_data_indexes = data_handler.readTileData(zip_file, entry);
                        }
                        if(entry.getName().endsWith("$.txt")){
                            //set map name text field to display map file name
                            String map_name = entry.getName();
                            int map_name_len = map_name.length() - 5; // $.txt = 5
                            map_name = map_name.substring(0, map_name_len);

                            GUI.TextFields.MAP_NAME.setText(map_name);
                            loaded_map_indexes = data_handler.readMap(zip_file, entry);
                        }
                    }

                    //store the pngs in an ArrayList first
                    entries = zip_file.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();

                        if(entry.getName().endsWith(".png")){
                            png_entries.add(entry);
                        }
                    }

                    //Iterate until png matches index
                    for(int i = 0; i < tile_data_indexes.length; i++){
                        for(ZipEntry z : png_entries){
                            //extract index at the beginning of the png name
                            String image_index = z.getName().substring(0, z.getName().lastIndexOf('$'));

                            if(Integer.parseInt(image_index) == tile_data_indexes[i][0]){
                                loaded_tiles.add(data_handler.readImage(zip_file, z, tile_data_indexes, i));
                            }
                        }
                    }
                        

                    //Check print
                    System.out.println(loaded_tiles.size());
                    for(Tile t : loaded_tiles){
                        System.out.println(
                            t.name + " " + t.index + " " 
                            + t.is_solid + " " + t.is_animated);
                    }

                    zip_file.close();

                } catch (IOException ex) {
                    System.out.println("Error loading map zip file");
                }

                //clear previous stacks
                data_handler.panel.grid.undo.clear();
                data_handler.panel.grid.redo.clear();

                //build loaded map on grid via data handler
                data_handler.panel.grid.loadMapTiles(loaded_map_indexes, loaded_tiles);
                //load tiles for tile list but validate first via tile handler
                data_handler.panel.tile_list.loadTiles(loaded_tiles);

            }else System.out.println("Canceled loading...");
        }
    }

}
