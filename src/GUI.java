import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {

    public static class Panels {

        public static final JPanel MAP_SETTINGS, TOOLS, TILE_PROPERTIES, TILE_LIST_HEADER, TILE_LIST;

        static {
            MAP_SETTINGS = new JPanel();
            TOOLS = new JPanel();
            TILE_PROPERTIES = new JPanel();
            TILE_LIST_HEADER = new JPanel();
            TILE_LIST = new JPanel();

            MAP_SETTINGS.setBackground(Color.BLACK);
            TOOLS.setBackground(Color.BLACK);
            TILE_PROPERTIES.setBackground(Color.BLACK);
            TILE_LIST_HEADER.setBackground(Color.BLACK);
            TILE_LIST.setBackground(Color.BLACK);
        }
    }
    
    public static class Labels {

        public static final JLabel MAP_SETTINGS, MAP_DIMENSIONS, MAP_NAME;
        public static final JLabel TOOLS, PAINT, BUCKET;
        public static final JLabel TILE_PROPERTIES, TILE_NAME, TILE_IMAGE, INDEX, SOLID, ANIMATED;
        public static final JLabel TILE_LIST;

        private static BufferedImage def = null;
        private static Image img = null;
        

        static {
            MAP_SETTINGS = new JLabel("Map Settings");
            MAP_DIMENSIONS = new JLabel("Set Length and Height");
            MAP_NAME = new JLabel("Map Name");
            
            MAP_SETTINGS.setForeground(Color.WHITE);
            MAP_SETTINGS.setHorizontalAlignment(SwingConstants.CENTER);
            MAP_DIMENSIONS.setForeground(Color.WHITE);
            MAP_NAME.setForeground(Color.WHITE);
            MAP_NAME.setHorizontalAlignment(SwingConstants.CENTER);

            TOOLS = new JLabel("Modes:");
            PAINT = new JLabel("Off");
            BUCKET = new JLabel("Off");

            TOOLS.setForeground(Color.WHITE);
            TOOLS.setHorizontalAlignment(SwingConstants.CENTER);
            PAINT.setForeground(Color.RED);
            PAINT.setHorizontalAlignment(SwingConstants.CENTER);
            BUCKET.setForeground(Color.RED);
            BUCKET.setHorizontalAlignment(SwingConstants.CENTER);

            try {
                def = ImageIO.read(new ByteArrayInputStream(Tile.void_image));
                img = def.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            } catch (IOException e) {
            }
            

            TILE_PROPERTIES = new JLabel("Tile Properties");
            TILE_IMAGE = new JLabel(new ImageIcon(img));
            TILE_NAME = new JLabel("Void");
            INDEX = new JLabel("Tile Index");
            SOLID = new JLabel("Solid");
            ANIMATED = new JLabel("Animated");

            TILE_PROPERTIES.setForeground(Color.WHITE);
            TILE_PROPERTIES.setHorizontalAlignment(SwingConstants.CENTER);
            TILE_NAME.setForeground(Color.WHITE);
            TILE_NAME.setHorizontalAlignment(SwingConstants.CENTER);
            INDEX.setForeground(Color.WHITE);
            INDEX.setHorizontalAlignment(SwingConstants.CENTER);
            SOLID.setForeground(Color.WHITE);
            SOLID.setHorizontalAlignment(SwingConstants.CENTER);
            ANIMATED.setForeground(Color.WHITE);
            ANIMATED.setHorizontalAlignment(SwingConstants.CENTER);

            TILE_LIST = new JLabel("Tile List");
            TILE_LIST.setForeground(Color.WHITE);
            TILE_LIST.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public static class TextFields {

        public static final JTextField MAP_LENGTH, MAP_HEIGHT, MAP_NAME;
        public static final JTextField TILE_INDEX;

        static {
            MAP_LENGTH = new JTextField();
            MAP_HEIGHT = new JTextField();
            MAP_NAME = new JTextField();

            MAP_LENGTH.setText("25");
            MAP_HEIGHT.setText("25");

            MAP_LENGTH.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_LENGTH.setBackground(Color.BLACK);
            MAP_LENGTH.setForeground(Color.WHITE);

            MAP_HEIGHT.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_HEIGHT.setBackground(Color.BLACK);
            MAP_HEIGHT.setForeground(Color.WHITE);

            MAP_NAME.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_NAME.setBackground(Color.BLACK);
            MAP_NAME.setForeground(Color.WHITE);

            TILE_INDEX = new JTextField("0");
            TILE_INDEX.setFont(new Font("Arial", Font.PLAIN, 15));
            TILE_INDEX.setBackground(Color.BLACK);
            TILE_INDEX.setForeground(Color.WHITE);
            TILE_INDEX.setEditable(false);
        }
    }

    public static class Buttons {

        public static final JButton RESIZE, SAVE, LOAD, NEW, ADD;
        public static final JButton PAINT, BUCKET;
        public static final JButton SAVE_CHANGES;

        static {
            RESIZE = new JButton("Resize");
            SAVE = new JButton("Save");
            LOAD = new JButton("Load");
            NEW = new JButton("New");
            ADD = new JButton("Add Tiles");

            PAINT = new JButton("Paint");
            BUCKET = new JButton("Bucket");

            SAVE_CHANGES = new JButton("Save Changes");

            RESIZE.setBackground(Color.BLACK);
            RESIZE.setForeground(Color.WHITE);
            SAVE.setBackground(Color.BLACK);
            SAVE.setForeground(Color.WHITE);
            LOAD.setBackground(Color.BLACK);
            LOAD.setForeground(Color.WHITE);
            NEW.setBackground(Color.BLACK);
            NEW.setForeground(Color.WHITE);
            ADD.setBackground(Color.BLACK);
            ADD.setForeground(Color.WHITE);

            PAINT.setBackground(Color.BLACK);
            PAINT.setForeground(Color.WHITE);
            BUCKET.setBackground(Color.BLACK);
            BUCKET.setForeground(Color.WHITE);

            SAVE_CHANGES.setBackground(Color.BLACK);
            SAVE_CHANGES.setForeground(Color.WHITE);

            RESIZE.setFocusable(false);
            LOAD.setFocusable(false);
            SAVE.setFocusable(false);
            ADD.setFocusable(false);
            NEW.setFocusable(false);
            PAINT.setFocusable(false);
            BUCKET.setFocusable(false);
            SAVE_CHANGES.setFocusable(false);
        }
    }

    public static class Checkboxes {

        public static final JCheckBox SOLID, ANIMATED;

        static {
            SOLID = new JCheckBox();
            ANIMATED = new JCheckBox();

            SOLID.setBackground(Color.BLACK);
            ANIMATED.setBackground(Color.BLACK);

            SOLID.setFocusable(false);
            ANIMATED.setFocusable(false);
        }
    }

}
