import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class MapSettings extends JFrame {

    private final Panel panel;
    private final JLabel length, height, header;
    private final JTextArea note;
    private final JTextField map_length, map_height;
    private final JButton submit_btn;
    private final JPanel panel1;
    private final ActionListener click_listener;

    public MapSettings(Panel panel){

        this.panel = panel;

        length = new JLabel("Map Length (in Tiles)");
        height = new JLabel("Map Height (in Tiles)");
        header = new JLabel("v Enter map dimenstions v - Max n tiles: 500");
        note = new JTextArea("""
            Note that upon clicking resize,  it will overwrite the current map data. 
            Make sure to save before resizing!
            """);

        length.setForeground(Color.WHITE);
        height.setForeground(Color.WHITE);
        header.setForeground(Color.WHITE);
        note.setForeground(Color.WHITE);
        note.setBackground(Color.BLACK);
        note.setLineWrap(true);
        
        map_length = new JTextField();
        map_height = new JTextField();
        submit_btn = new JButton("Resize");

        submit_btn.setBackground(Color.BLACK);
        submit_btn.setForeground(Color.WHITE);

        map_length.setFont(new Font("Consolas", Font.PLAIN, 25));
        map_height.setFont(new Font("Consolas", Font.PLAIN, 25));
        note.setFont(new Font("Cosolas", Font.PLAIN, 15));

        note.setSize(250, 20);
        note.setAlignmentX(CENTER_ALIGNMENT);

        map_length.setBackground(Color.BLACK);
        map_length.setForeground(Color.WHITE);

        map_height.setBackground(Color.BLACK);
        map_height.setForeground(Color.WHITE);

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 1, 10, 5));
        panel1.setBackground(Color.BLACK);

        setLayout(new FlowLayout());
        add(header);
        panel1.add(length);
        panel1.add(map_length);
        panel1.add(height);
        panel1.add(map_height);
        add(panel1);
        add(submit_btn);
        add(note);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Settings");
        this.setSize(300, 300);
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);

        click_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == submit_btn){
                    String l = map_length.getText();
                    String h = map_height.getText();
                    if(l.length() != 0 && h.length() != 0){
                        int len = Integer.parseInt(l);
                        int hei = Integer.parseInt(h);
                        panel.set_dimensions(len, hei);
                    }
                }
            }
        };

        submit_btn.addActionListener(click_listener);
    }
    
}
