import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileHandler implements MouseListener {
    private final DataHandler data_handler;
    public Tile tile;

    public TileHandler(DataHandler data_handler, Tile tile){
        this.data_handler = data_handler;
        this.tile = tile;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Selected Tile: " + tile.name);

        System.out.println("Assigned index: " + tile.index);
        System.out.println("Is Solid? " + tile.is_solid);
        System.out.println("Is Animated? " + tile.is_animated);

        data_handler.panel.updateSelectedTile(tile);
        data_handler.panel.settings.editTileProperties(this);
        data_handler.queued_tile_handler = this;
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
