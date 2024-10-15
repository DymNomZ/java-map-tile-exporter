import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class MapSettings extends JFrame {

    private final Panel panel;
    private final JLabel length, height, header, map_name_label, load_map;
    private final JTextArea note;
    private final JTextField map_length, map_height, map_name_input;
    private final JButton resize_btn, save_btn, load_btn;
    private final JPanel panel1, panel2, panel3;
    private JFileChooser file_chooser = null;
    private final ActionListener resize_listener, save_listener, load_listener;
    private Tile[][] map_data;
    private ArrayList<TileData> tile_data;
    public File selected_folder, selected_zip;

    public MapSettings(Panel panel){

        this.panel = panel;

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
                if(l.length() != 0 && h.length() != 0){
                    int len = Integer.parseInt(l);
                    int hei = Integer.parseInt(h);
                    panel.set_dimensions(len, hei);
                }
            }
        };

        //lamdaed, handle saving
        save_listener = (ActionEvent e) -> {
            file_chooser = new JFileChooser();
            file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int return_value = file_chooser.showOpenDialog(null);
            if (return_value == JFileChooser.APPROVE_OPTION) {
                
                selected_folder = file_chooser.getSelectedFile();
                System.out.println("Selected folder: " + selected_folder);
                
                //get finalized map
                map_data = panel.get_map_data();

                //get map_name from textfield
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
                    zos.putNextEntry(new ZipEntry(map_name + ".txt"));

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

                tile_data = panel.get_tile_cards();

                //ZipEntry for tiles
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
                        writer.write(t.tile.index + "\n");
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
            //load test
            try {
                file_chooser = new JFileChooser();
                file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int return_value = file_chooser.showOpenDialog(null);
                if (return_value == JFileChooser.APPROVE_OPTION) {
                    selected_zip = file_chooser.getSelectedFile();
                    System.out.println("Selected zip: " + selected_zip);

                    String zipFilePath = "src\\testing1.zip";
                    String entryName = "testing1.txt";
                }
                
                
                
    
                ZipFile zipFile = new ZipFile(zipFilePath);
                ZipEntry entry = zipFile.getEntry(entryName);
    
                if (entry != null) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                    inputStream.close(); 
    
                } else {
                    System.out.println("Entry not found in the ZIP file.");
                }
    
                zipFile.close();
            } catch (IOException e3) {
                System.out.println("HMMM");
            }
        };

        resize_btn.addActionListener(resize_listener);
        save_btn.addActionListener(save_listener);
        load_btn.addActionListener(load_listener);
    }
    
}
