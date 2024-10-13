import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Editor");
        window.setResizable(false);

        Panel panel = new Panel();
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        MapSettings settings = new MapSettings(panel);
        panel.start_clock();

        
    }
}