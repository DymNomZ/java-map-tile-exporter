
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class Saver implements ActionListener {

    ArrayList<TileData> loaded_tile_data = new ArrayList<>();

    DataHandler data_handler;

    public Saver(DataHandler data_handler) {
        this.data_handler = data_handler;
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
            Tile[][] map_data = data_handler.panel.grid.finalizedTiles();

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
