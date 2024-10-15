import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;

public class CardHandler implements MouseListener {
    private final Panel panel;
    private final JTextField input;
    public Tile tile;
    public int index = 0;
    public boolean is_selected = false;

    public CardHandler(
        Panel panel, Tile tile, 
        JTextField input
    ){
        this.panel = panel;
        this.input = input;
        this.tile = tile;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Selected Tile: " + tile.name);

        //handle no index inputted, default to zero
        if(!(input.getText().length() == 0)){
            index = Integer.parseInt(input.getText());
        }

        System.out.println("Assigned index: " + index);
        panel.get_selected_tile(tile, index);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
