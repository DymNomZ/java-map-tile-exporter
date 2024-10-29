
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class SidebarListeners {

    ArrayList<TileData> loaded_tile_data;

    Panel panel;
    DataHandler data_handler;

    public SidebarListeners(Panel panel) {
        this.panel = panel;
    }

    public SidebarListeners(Panel panel, DataHandler data_handler) {
        this.panel = panel;
        this.data_handler = data_handler;

        loaded_tile_data = new ArrayList<>();
    }
    
    public static class Resizer extends SidebarListeners implements ActionListener {

        public Resizer(Panel panel){
            super(panel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == GUI.Buttons.RESIZE){
                String l = GUI.TextFields.MAP_LENGTH.getText();
                String h = GUI.TextFields.MAP_HEIGHT.getText();
                //input CHECK
                if(l.length() != 0 && h.length() != 0){
                    int len = Integer.parseInt(l);
                    int hei = Integer.parseInt(h);
                    //we use panel because these values rely on the clock
                    panel.set_dimensions(len, hei);
                    panel.updateGrid(len, hei);
                }
            }
        }
    }

    public static class Saver extends SidebarListeners implements ActionListener {

        DataHandler data_handler;

        public Saver(Panel panel, DataHandler data_handler) {
            super(panel, data_handler);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int return_value = file_chooser.showOpenDialog(null);
            if (return_value == JFileChooser.APPROVE_OPTION) {
          
                File selected_folder = file_chooser.getSelectedFile();
                System.out.println("Selected folder: " + selected_folder);
                
                //get finalized map from grid via data handler
                Tile[][] map_data = data_handler.finalizedTiles();

                //get map name from textfield
                String map_name = GUI.TextFields.MAP_NAME.getText();

                //Zip output to selected directory
                File output_zip = new File(selected_folder, map_name + ".zip");
                
                // Create a ZipOutputStream
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(output_zip);
                } catch (FileNotFoundException e1) {
                    System.out.println("Selected directory does not exist!");
                }
                ZipOutputStream zos = new ZipOutputStream(fos);

                // Create a temporary file to store the integer
                try {
                    File temp_file = new File("temp.txt");
                    FileWriter writer = new FileWriter(temp_file);

                    for (int i = 0; i < map_data.length; i++) {
                        for(int j = 0; j < map_data[i].length; j++) {

                            //write to txt
                            writer.write(map_data[i][j].index + " ");

                        }
                        writer.write("\n");
                    }
                    writer.close();

                    // Create a ZipEntry for the map txt file
                    zos.putNextEntry(new ZipEntry(map_name + "$.txt"));

                    FileInputStream fis = new FileInputStream(temp_file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();

                    zos.closeEntry();
                    temp_file.delete();
                } catch (IOException ex) {
                    System.out.println("Error writing map.txt file");
                }

                //get tile data from tile list via data handler
                ArrayList<TileData> tile_data = data_handler.getFinalizedTileData();

                //ZipEntry for tiles
                for(TileData t : tile_data){
                    try {
                        zos.putNextEntry(new ZipEntry(t.tile.name + ".png"));
                        // Write the image data to the ZIP file
                        ImageIO.write(t.tile.image, "png", zos);
                        zos.closeEntry();
                    } catch (IOException e1) {
                        System.out.println("Error creating map zip file");
                    }
                }

                //ZipEntry for tile data
                try {
                    File temp_file = new File("temp.txt");
                    FileWriter writer = new FileWriter(temp_file);

                    for(TileData t: tile_data){
                        //write tile index | solid |
                        writer.write(t.tile.index + " ");
                        if(t.solid_state.isSelected()){
                            writer.write("1");
                        }else writer.write("0");
                        writer.write(" \n");
                    }
                    writer.close();

                    // Create a ZipEntry for tile data
                    zos.putNextEntry(new ZipEntry("tile_data.txt"));

                    FileInputStream fis = new FileInputStream(temp_file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();

                    zos.closeEntry();
                    temp_file.delete();
                } catch (IOException ex) {
                    System.out.println("Error writing tile_data.txt file");
                }

                // Close ZipOutputStream
                try {
                    zos.close();
                } catch (IOException e1) {
                    System.out.println("Error closing ZipOutputStream");
                }

                System.out.println("Zip created successfully!");
            }
        }
    }

    public static class Loader extends SidebarListeners implements ActionListener {

        public Loader(Panel panel, DataHandler data_handler){
            super(panel, data_handler);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            int return_value = file_chooser.showOpenDialog(null);
            if (return_value == JFileChooser.APPROVE_OPTION) {

                int[][] loaded_map_indexes = null;
                int[][] tile_data_indexes = null;
                
                File selected_zip = file_chooser.getSelectedFile();
                System.out.println("Selected zip: " + selected_zip);

                if(selected_zip != null){
                    //clear previous tile list
                    loaded_tile_data.clear();
                    // Create a ZipFile object
                    ZipFile zip_file;
                    try {

                        zip_file = new ZipFile(selected_zip.getAbsolutePath());

                        // Get an enumeration of the entries in the zip file
                        Enumeration<? extends ZipEntry> entries = zip_file.entries();

                        // Iterate over the entries and print their names
                        System.out.println("Zip contents: ");
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            //System.out.println(entry.getName());

                            //read operations:
                            if(entry.getName().endsWith("data.txt")){
                                tile_data_indexes = data_handler.readTileData(zip_file, entry);
                            }
                            if(entry.getName().endsWith("$.txt")){
                                //set map name textfield to display map file name
                                String map_name = entry.getName();
                                int map_name_len = map_name.length() - 5; // $.txt = 5
                                map_name = map_name.substring(0, map_name_len);

                                GUI.TextFields.MAP_NAME.setText(map_name);
                                loaded_map_indexes = data_handler.readMap(zip_file, entry);
                            }
                        }

                        //load pngs
                        entries = zip_file.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();

                            if(entry.getName().endsWith(".png")){
                                loaded_tile_data = data_handler.readImages(zip_file, entry, tile_data_indexes);
                            }
                        }

                        //Check print
                        System.out.println(loaded_tile_data.size());
                        for(TileData t : loaded_tile_data){
                            System.out.println(
                                t.tile.name + " " + t.tile.index + " " 
                                + t.tile.is_solid + " " + t.input.getText());
                        }

                        zip_file.close();

                    } catch (IOException ex) {
                        System.out.println("Error loading map zip file");
                    }

                    //build loaded map via data handler
                    data_handler.buildLoadedMap(loaded_map_indexes, loaded_tile_data);
                    //tile_list.load_map(loaded_tile_data);

                }else System.out.println("Canceled loading...");
            }
        }
    }
}
