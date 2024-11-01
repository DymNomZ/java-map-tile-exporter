import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("j2dc Map Editor");
        window.setResizable(false);

        Panel panel = new Panel();
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        WindowHandler WH = new WindowHandler(panel);

        window.addWindowListener(WH);

        panel.startClock();
    }
}