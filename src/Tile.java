import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image = null;
    public String path, name;
    public boolean is_solid = false;
    public int index = 0;

    //main constructor for creating
    public Tile(String path, int index, String name, boolean is_solid){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println("Error loading tile image");
        }
        this.index = index;
        this.name = name;
        this.is_solid = is_solid;
    }

    //Second constructor for loading
    public Tile(int index, boolean is_solid, String name, BufferedImage image){
        this.image = image;
        this.index = index;
        this.name = name;
        this.is_solid = is_solid;
    }
}
