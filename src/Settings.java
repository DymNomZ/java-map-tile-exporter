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

        add(GUI.Panels.MAP_SETTINGS);

        GUI.Panels.TOOLS.setLayout(gb_layout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        GUI.Panels.TOOLS.add(GUI.Labels.TOOLS, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        GUI.Panels.TOOLS.add(GUI.Buttons.PAINT, gbc);
        GUI.Buttons.PAINT.addActionListener(new ClickListener(data_handler));

        gbc.gridx = 1;
        GUI.Panels.TOOLS.add(GUI.Labels.PAINT, gbc);

        gbc.gridx = 2;
        GUI.Panels.TOOLS.add(GUI.Buttons.BUCKET, gbc);
        GUI.Buttons.BUCKET.addActionListener(new ClickListener(data_handler));

        gbc.gridx = 3;
        GUI.Panels.TOOLS.add(GUI.Labels.BUCKET, gbc);

        add(GUI.Panels.TOOLS);

        GUI.Panels.TILE_PROPERTIES.setLayout(gb_layout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.TILE_PROPERTIES, gbc);

        gbc.gridy = 1;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.TILE_NAME, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.TILE_IMAGE, gbc);

        gbc.gridx = 1;
        gbc.gridheight = 1;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.INDEX, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        GUI.Panels.TILE_PROPERTIES.add(GUI.TextFields.TILE_INDEX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.SOLID, gbc);

        gbc.gridx = 1;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Checkboxes.SOLID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Labels.ANIMATED, gbc);

        gbc.gridx = 1;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Checkboxes.ANIMATED, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        GUI.Panels.TILE_PROPERTIES.add(GUI.Buttons.SAVE_CHANGES, gbc);

        add(GUI.Panels.TILE_PROPERTIES);

        GUI.Buttons.RESIZE.addActionListener(new Resizer(data_handler));
        GUI.Buttons.SAVE.addActionListener(new Saver(data_handler));
        GUI.Buttons.LOAD.addActionListener(new Loader(data_handler));
        GUI.Buttons.NEW.addActionListener(new NewListener(data_handler));
        GUI.Buttons.SAVE_CHANGES.addActionListener(new ClickListener(data_handler));
    }

    public void editTileProperties(TileHandler th){
        //Overwrite current displayed tile properties
        GUI.Labels.TILE_NAME.setText("<html>" + th.tile.name + "<html>");
        GUI.Labels.TILE_IMAGE.setIcon(
            new ImageIcon(th.tile.image.getScaledInstance(64, 64, Image.SCALE_SMOOTH))
        );
        GUI.TextFields.TILE_INDEX.setText(String.format("%d", th.tile.index));
        GUI.Checkboxes.SOLID.setSelected(th.tile.is_solid);
        GUI.Checkboxes.ANIMATED.setSelected(th.tile.is_animated);

        //Update tile on the tile handler to reflect changes on tile list
        // T.tile = tile;
    }
    
}
