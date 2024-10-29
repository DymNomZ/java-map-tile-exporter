import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {

    public static class Panels {

        public static final JPanel MAP_SETTINGS, TILE_LIST;

        static {
            MAP_SETTINGS = new JPanel();
            TILE_LIST = new JPanel();
        }
    }
    
    public static class Labels {

        public static final JLabel HEADER, MAP_DIMENSIONS, MAP_NAME;

        static {
            HEADER = new JLabel("Map Settings");
            MAP_DIMENSIONS = new JLabel("Set Height and Length");
            MAP_NAME = new JLabel("Map Name");
            
            HEADER.setForeground(Color.WHITE);
            HEADER.setHorizontalAlignment(SwingConstants.CENTER);
            MAP_DIMENSIONS.setForeground(Color.WHITE);
            MAP_NAME.setForeground(Color.WHITE);
            MAP_NAME.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public static class TextFields {

        public static final JTextField MAP_LENGTH, MAP_HEIGHT, MAP_NAME;

        static {
            MAP_LENGTH = new JTextField();
            MAP_HEIGHT = new JTextField();
            MAP_NAME = new JTextField();

            MAP_LENGTH.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_LENGTH.setBackground(Color.BLACK);
            MAP_LENGTH.setForeground(Color.WHITE);

            MAP_HEIGHT.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_HEIGHT.setBackground(Color.BLACK);
            MAP_HEIGHT.setForeground(Color.WHITE);

            MAP_NAME.setFont(new Font("Arial", Font.PLAIN, 15));
            MAP_NAME.setBackground(Color.BLACK);
            MAP_NAME.setForeground(Color.WHITE);
        }
    }

    public static class Buttons {

        public static final JButton RESIZE, SAVE, LOAD;

        static {
            RESIZE = new JButton("Resize");
            SAVE = new JButton("Save");
            LOAD = new JButton("Load");

            RESIZE.setBackground(Color.BLACK);
            RESIZE.setForeground(Color.WHITE);
            SAVE.setBackground(Color.BLACK);
            SAVE.setForeground(Color.WHITE);
            LOAD.setBackground(Color.BLACK);
            LOAD.setForeground(Color.WHITE);
        }
    }

}
