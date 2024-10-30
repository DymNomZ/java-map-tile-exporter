import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {

    public static class Panels {

        public static final JPanel MAP_SETTINGS, TILE_SETTINGS, TILE_LIST_HEADER, TILE_LIST;

        static {
            MAP_SETTINGS = new JPanel();
            TILE_SETTINGS = new JPanel();
            TILE_LIST_HEADER = new JPanel();
            TILE_LIST = new JPanel();
        }
    }
    
    public static class Labels {

        public static final JLabel MAP_SETTINGS, MAP_DIMENSIONS, MAP_NAME;
        public static final JLabel MODES, PAINT, PAINT_TOGGLE, BUCKET, BUCKET_TOGGLE;
        public static final JLabel TILE_SETTINGS, TILE_IMAGE, INDEX, SOLID, ANIMATED;
        public static final JLabel TILE_LIST;

        static {
            MAP_SETTINGS = new JLabel("Map Settings");
            MAP_DIMENSIONS = new JLabel("Set Length and Height");
            MAP_NAME = new JLabel("Map Name");
            
            MAP_SETTINGS.setForeground(Color.WHITE);
            MAP_SETTINGS.setHorizontalAlignment(SwingConstants.CENTER);
            MAP_DIMENSIONS.setForeground(Color.WHITE);
            MAP_NAME.setForeground(Color.WHITE);
            MAP_NAME.setHorizontalAlignment(SwingConstants.CENTER);

            MODES = new JLabel("Modes:");
            PAINT = new JLabel("Paint");
            PAINT_TOGGLE = new JLabel("Off");
            BUCKET = new JLabel("Bucket");
            BUCKET_TOGGLE = new JLabel("Off");

            MODES.setForeground(Color.WHITE);
            MODES.setHorizontalAlignment(SwingConstants.CENTER);
            PAINT.setForeground(Color.WHITE);
            PAINT.setHorizontalAlignment(SwingConstants.CENTER);
            PAINT_TOGGLE.setForeground(Color.RED);
            PAINT_TOGGLE.setHorizontalAlignment(SwingConstants.CENTER);
            BUCKET.setForeground(Color.WHITE);
            BUCKET.setHorizontalAlignment(SwingConstants.CENTER);
            BUCKET_TOGGLE.setForeground(Color.RED);
            BUCKET_TOGGLE.setHorizontalAlignment(SwingConstants.CENTER);

            TILE_SETTINGS = new JLabel("Tile Settings");
            TILE_IMAGE = new JLabel();
            INDEX = new JLabel("Set Index");
            SOLID = new JLabel("Solid");
            ANIMATED = new JLabel("Animated");

            TILE_SETTINGS.setForeground(Color.WHITE);
            TILE_SETTINGS.setHorizontalAlignment(SwingConstants.CENTER);
            INDEX.setForeground(Color.WHITE);
            INDEX.setHorizontalAlignment(SwingConstants.CENTER);
            SOLID.setForeground(Color.RED);
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

            TILE_INDEX = new JTextField();
            TILE_INDEX.setFont(new Font("Arial", Font.PLAIN, 15));
            TILE_INDEX.setBackground(Color.BLACK);
            TILE_INDEX.setForeground(Color.WHITE);
        }
    }

    public static class Buttons {

        public static final JButton RESIZE, SAVE, LOAD, NEW, ADD;

        static {
            RESIZE = new JButton("Resize");
            SAVE = new JButton("Save");
            LOAD = new JButton("Load");
            NEW = new JButton("New");
            ADD = new JButton("Add Tiles");

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
        }
    }

    public static class Checkboxes {

        public static final JCheckBox SOLID, ANIMATED;

        static {
            SOLID = new JCheckBox();
            ANIMATED = new JCheckBox();
        }
    }

}
