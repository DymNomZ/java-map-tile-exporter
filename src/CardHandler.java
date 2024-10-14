import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class CardHandler implements MouseListener {
    private Panel panel;
    public BufferedImage tile;
    public String tile_name;
    public boolean is_selected = false;

    public CardHandler(Panel panel, BufferedImage tile, String tile_name){
        this.panel = panel;
        this.tile = tile;
        this.tile_name = tile_name;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Selected Tile: " + tile_name);
        panel.get_selected_tile(tile);
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
