import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class TileData {

    public Tile tile;
    public JTextField input;
    public JCheckBox solid_state;

    public TileData(Tile tile, JTextField input, JCheckBox solid_state){
        this.tile = tile;
        this.input = input;
        this.solid_state = solid_state;
    }
    
}
