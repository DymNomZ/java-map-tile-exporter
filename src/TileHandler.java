import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class TileHandler implements MouseListener {
    public BufferedImage tile;
    public String tile_name;

    public TileHandler(BufferedImage tile, String tile_name){
        this.tile = tile;
        this.tile_name = tile_name;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(tile_name);
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
