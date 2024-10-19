import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.imageio.*;
import javax.swing.*;

public class MapSettings extends JFrame {

    private final Panel panel;
    private final JLabel length, height, header, map_name_label, load_map;
    private final JTextArea note;
    private final JTextField map_length, map_height, map_name_input;
    private final JButton resize_btn, save_btn;
    private final JPanel panel1, panel2, panel3;
    private JFileChooser file_chooser = null;
    private final ActionListener resize_listener, save_listener, load_listener;
    private Tile[][] map_data;
    private int[][] loaded_map_indexes, tile_data_indexes;
    private ArrayList<TileData> tile_data, loaded_tile_data;
    private TileList tile_list = null;
    private int curr_idx = 0;
    public File selected_folder, selected_zip;
    public final JButton load_btn;

    public MapSettings(Panel panel){

        this.panel = panel;

        tile_data = new ArrayList<>();
        loaded_tile_data = new ArrayList<>();

        //Start of setting-up components
        length = new JLabel("Map Length (in Tiles)");
        height = new JLabel("Map Height (in Tiles)");
        map_name_label = new JLabel("v Enter map name v");
        load_map = new JLabel("> Load Map >");
        header = new JLabel("v Enter map dimenstions v - Max n tiles: 500");
        note = new JTextArea("""
            Note that upon clicking resize,  it will overwrite the current map data. 
            Make sure to save before resizing!
            """);

        length.setForeground(Color.WHITE);
        height.setForeground(Color.WHITE);
        map_name_label.setForeground(Color.WHITE);
        load_map.setForeground(Color.WHITE);
        header.setForeground(Color.WHITE);
        note.setForeground(Color.WHITE);
        note.setBackground(Color.BLACK);
        note.setLineWrap(true);
        
        map_length = new JTextField();
        map_height = new JTextField();
        map_name_input = new JTextField();
        resize_btn = new JButton("Resize");
        save_btn = new JButton("Save");
        load_btn = new JButton("Load");

        resize_btn.setBackground(Color.BLACK);
        resize_btn.setForeground(Color.WHITE);
        save_btn.setBackground(Color.BLACK);
        save_btn.setForeground(Color.WHITE);
        load_btn.setBackground(Color.BLACK);
        load_btn.setForeground(Color.WHITE);

        map_length.setFont(new Font("Consolas", Font.PLAIN, 25));
        map_height.setFont(new Font("Consolas", Font.PLAIN, 25));
        map_name_input.setFont(new Font("Consolas", Font.PLAIN, 15));
        note.setFont(new Font("Cosolas", Font.PLAIN, 15));

        note.setSize(250, 20);
        note.setAlignmentX(CENTER_ALIGNMENT);

        map_length.setBackground(Color.BLACK);
        map_length.setForeground(Color.WHITE);

        map_height.setBackground(Color.BLACK);
        map_height.setForeground(Color.WHITE);

        map_name_input.setBackground(Color.BLACK);
        map_name_input.setForeground(Color.WHITE);

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 1, 10, 5));
        panel1.setBackground(Color.BLACK);
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 1, 10, 5));
        panel2.setBackground(Color.BLACK);
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1, 1, 10, 5));
        panel3.setBackground(Color.BLACK);

        setLayout(new FlowLayout());
        //end

        //adding components
        add(header);
        panel1.add(length);
        panel1.add(map_length);
        panel1.add(height);
        panel1.add(map_height);
        add(panel1);
        add(resize_btn);
        add(note);
        panel2.add(map_name_label);
        panel2.add(map_name_input);
        add(panel2);
        add(save_btn);
        panel3.add(load_map);
        panel3.add(load_btn);
        add(panel3);
        //end

        //window settings
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Settings");
        this.setSize(300, 425);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLocation(25, 0);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);

        //lambdaed, handle resizing
        resize_listener = (ActionEvent e) -> {
            if(e.getSource() == resize_btn){
                String l = map_length.getText();
                String h = map_height.getText();
                //input CHECK
                if(l.length() != 0 && h.length() != 0){
                    int len = Integer.parseInt(l);
                    int hei = Integer.parseInt(h);
                    panel.set_dimensions(len, hei);
                    panel.repaint_grid(len, hei);
                }
            }
        };

        //lamdaed, handle saving
        save_listener = (ActionEvent e) -> {
            file_chooser = new JFileChooser();
            file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int return_value = file_chooser.showOpenDialog(null);
            if (return_value == JFileChooser.APPROVE_OPTION) {
          
                //no need to check due to return_value check
                selected_folder = file_chooser.getSelectedFile();
                System.out.println("Selected folder: " + selected_folder);
                
                //NO NEED TO CHECK CAUSE WE DON'T LOAD ANYTHING
                //get finalized map from grid
                map_data = panel.get_map_data();

                //get map_name from map name textfield
                String map_name = map_name_input.getText();

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
                //NO NEED TO CHECK
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
                }

                //get tile data from tile list
                tile_data = panel.get_tile_cards();

                //ZipEntry for tiles
                //NO NEED TO CHECK CAUSE ARRAYLIST
                for(TileData t : tile_data){
                    try {
                        zos.putNextEntry(new ZipEntry(t.tile.name + ".png"));
                        // Write the image data to the ZIP file
                        ImageIO.write(t.tile.image, "png", zos);
                        zos.closeEntry();
                    } catch (IOException e1) {
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
                }

                // Close ZipOutputStream
                try {
                    zos.close();
                } catch (IOException e1) {
                }

                System.out.println("Zip created successfully!");
            }
        };

        //lambdaed, handle loading
        load_listener = (ActionEvent e) -> {

            file_chooser = new JFileChooser();
            file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            int return_value = file_chooser.showOpenDialog(null);
            if (return_value == JFileChooser.APPROVE_OPTION) {
                
                selected_zip = file_chooser.getSelectedFile();
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
                                read_tile_data(zip_file, entry);
                            }
                            if(entry.getName().endsWith("$.txt")){
                                //set map name textfield to display map file name
                                String map_name = entry.getName();
                                int map_name_len = map_name.length() - 5; // $.txt = 5
                                map_name = map_name.substring(0, map_name_len);

                                map_name_input.setText(map_name);
                                read_map(zip_file, entry);
                            }
                        }

                        //load for pngs
                        entries = zip_file.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            //System.out.println(entry.getName());

                            if(entry.getName().endsWith(".png")){
                                read_images(zip_file, entry);
                            }
                        }

                        //reset traversing index
                        curr_idx = 0;

                        //Check print
                        System.out.println(loaded_tile_data.size());
                        for(TileData t : loaded_tile_data){
                            System.out.println(
                                t.tile.name + " " + t.tile.index + " " 
                                + t.tile.is_solid + " " + t.input.getText());
                        }

                        zip_file.close();

                    } catch (IOException ex) {
                    }

                    //load the map to the grid
                    panel.display_loaded_map_tiles(loaded_map_indexes, loaded_tile_data);
                    tile_list.load_map(loaded_tile_data);

                }else System.out.println("Canceled loading...");
            }
    
        };

        resize_btn.addActionListener(resize_listener);
        save_btn.addActionListener(save_listener);
        load_btn.addActionListener(load_listener);
    }

    public void set_tile_list(TileList tile_list){
        this.tile_list = tile_list;
    }

    public void read_images(ZipFile zip, ZipEntry image){

        InputStream image_data_stream;
        BufferedImage tile_image;
        String tile_name = image.getName().substring(0, image.getName().lastIndexOf('.'));

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
        }

    }

    public void read_tile_data(ZipFile zip, ZipEntry tile_data){
        
        InputStream tile_data_stream;
        BufferedReader reader;
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
        }
    }

    public void read_map(ZipFile zip, ZipEntry map){

        InputStream map_data_stream;
        BufferedReader reader;
        try {
            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            String line = reader.readLine();
            int map_h = 0;
            int map_l = line.length() / 2; //because of spaces

            do{
                map_h++;
            }while ((reader.readLine()) != null);
            //System.out.println("Map Length: " + map_l + " Height: " + map_h);
            reader.close();

            //new map cause previous inputs were already consumed by reader
            //new reader cause the previous one is at the end of the file = null
            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            loaded_map_indexes = new int[map_h][map_l];

            for(int i = 0; i < map_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < map_l; j++) {
                    //System.out.println(raw_indexes[j]);
                    loaded_map_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            map_data_stream.close();

        } catch (IOException ex) {
        }
        
    }
    
}
