import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class Saver implements ActionListener {

    DataHandler data_handler;

    public Saver(DataHandler data_handler) {
        this.data_handler = data_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        data_handler.panel.grid.has_changes = false;
        save();
    }

    public void save(){
        JFileChooser file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int return_value = file_chooser.showOpenDialog(null);
        if (return_value == JFileChooser.APPROVE_OPTION) {
        
            File selected_folder = file_chooser.getSelectedFile();
            System.out.println("Selected folder: " + selected_folder);
            
            //get finalized map from grid via data handler
            Tile[][] grid_tiles = data_handler.panel.grid.getTiles();
            //get images of used tiles on the grid
            ArrayList<Tile> used_tiles = data_handler.getusedTiles();

            //get map name from textfield
            String map_name = GUI.TextFields.MAP_NAME.getText();

            //validate
            if(map_name.equals("")) map_name = "no_name";

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

                for (int i = 0; i < grid_tiles.length; i++) {
                    for(int j = 0; j < grid_tiles[i].length; j++) {

                        //write to txt
                        writer.write(grid_tiles[i][j].index + " ");

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

            //ZipEntry for tile data and pngs
            try {
                File temp_file = new File("temp.txt");
                FileWriter writer = new FileWriter(temp_file);

                for(Tile t: used_tiles){

                    try {
                        zos.putNextEntry(new ZipEntry(t.index + "$" + t.name + ".png"));
                        // Write the image data to the ZIP file
                        ImageIO.write(t.image, "png", zos);
                        zos.closeEntry();
                    } catch (IOException e1) {
                        System.out.println("Error creating map zip file");
                    }

                    //write tile index | solid | animated
                    writer.write(t.index + " ");
                    if(t.is_solid) writer.write("1 ");
                    else writer.write("0 ");

                    if(t.is_animated) writer.write("1 ");
                    else writer.write("0 ");

                    writer.write("\n");
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
