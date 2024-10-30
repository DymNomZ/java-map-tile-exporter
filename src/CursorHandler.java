import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CursorHandler implements MouseListener {
    private final DataHandler data_handler;
    private Tile tile;

    public CursorHandler(DataHandler data_handler, Tile tile){
        this.data_handler = data_handler;
        this.tile = tile;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Selected Tile: " + tile.name);

        System.out.println("Assigned index: " + tile.index);
        data_handler.panel.get_selected_tile(tile, tile.index);
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
