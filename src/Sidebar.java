import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Sidebar extends JPanel {

    private final GridBagLayout gb_layout;
    private final GridBagConstraints gbc;

    public File selected_folder, selected_zip;

    Panel panel;
    Grid grid;
    DataHandler data_handler;

    public Sidebar(Panel panel, Grid grid){

        this.panel = panel;
        this.grid = grid;

        data_handler = new DataHandler(panel, grid);

        gb_layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5,0, 5);

        this.setPreferredSize(new Dimension(300, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        GUI.Panels.MAP_SETTINGS.setLayout(gb_layout);
        GUI.Panels.MAP_SETTINGS.setBackground(Color.BLACK);

        //adding components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        GUI.Panels.MAP_SETTINGS.add(GUI.Labels.HEADER, gbc);

        gbc.gridy = 1;
        GUI.Panels.MAP_SETTINGS.add(GUI.Labels.MAP_DIMENSIONS, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        GUI.Panels.MAP_SETTINGS.add(GUI.TextFields.MAP_LENGTH, gbc);

        gbc.gridx = 1;
        GUI.Panels.MAP_SETTINGS.add(GUI.TextFields.MAP_HEIGHT, gbc);

        gbc.gridx = 2;
        GUI.Panels.MAP_SETTINGS.add(GUI.Buttons.RESIZE, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        GUI.Panels.MAP_SETTINGS.add(GUI.Labels.MAP_NAME, gbc);

        gbc.gridy = 4;
        GUI.Panels.MAP_SETTINGS.add(GUI.TextFields.MAP_NAME, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        GUI.Panels.MAP_SETTINGS.add(GUI.Buttons.LOAD, gbc);

        gbc.gridx = 1;
        GUI.Panels.MAP_SETTINGS.add(GUI.Buttons.SAVE, gbc);

        add(GUI.Panels.MAP_SETTINGS);

        GUI.Buttons.RESIZE.addActionListener(new SidebarListeners.Resizer(panel));
        GUI.Buttons.SAVE.addActionListener(new SidebarListeners.Saver(panel, data_handler));
        GUI.Buttons.LOAD.addActionListener(new SidebarListeners.Loader(panel, data_handler));
    }

    public void set_tile_list(TileList tile_list){
        //this.tile_list = tile_list;
    }
    
}
