import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image = null;
    public String path, name;
    public int index = 0;

    public Tile(String path, int index, String name){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println("Error loading tile image");
        }
        this.index = index;
        this.name = name;
    }
}
