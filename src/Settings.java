import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Settings extends JPanel {

    private final GridBagLayout gb_layout;
    private final GridBagConstraints gbc;

    public File selected_folder, selected_zip;

    DataHandler data_handler;

    public Settings(DataHandler data_handler){

        this.data_handler = data_handler;

        this.setPreferredSize(new Dimension(250, 800));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        gb_layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5,0, 5);
        

        GUI.Panels.MAP_SETTINGS.setLayout(gb_layout);
        GUI.Panels.MAP_SETTINGS.setBackground(Color.BLACK);

        //adding components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        GUI.Panels.MAP_SETTINGS.add(GUI.Labels.MAP_SETTINGS, gbc);

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

        gbc.gridx = 2;
        GUI.Panels.MAP_SETTINGS.add(GUI.Buttons.NEW, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        GUI.Panels.MAP_SETTINGS.add(GUI.Labels.TILE_SETTINGS, gbc);

        add(GUI.Panels.MAP_SETTINGS);

        GUI.Buttons.RESIZE.addActionListener(new Resizer(data_handler));
        GUI.Buttons.SAVE.addActionListener(new Saver(data_handler));
        GUI.Buttons.LOAD.addActionListener(new Loader(data_handler));
        GUI.Buttons.NEW.addActionListener(new NewListener(data_handler));
    }
    
}
