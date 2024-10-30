import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image = null;
    public String path, name;
    public boolean is_solid = false;
    public int index = 0;

    //main constructor for creating
    public Tile(String path, int index, String name, boolean is_solid){

        //bytes of void image
        final byte[] void_image = {
            -119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 32, 
            0, 0, 0, 32, 8, 6, 0, 0, 0, 115, 122, 122, -12, 0, 0, 0, 1, 115, 82, 71, 66, 
            0, -82, -50, 28, -23, 0, 0, 0, 65, 73, 68, 65, 84, 88, 71, -19, -41, 49, 17, 
            0, 48, 8, 4, -63, -96, 26, 13, -88, 78, -122, 2, 13, -92, 88, 12, -4, -51, 118, 
            68, 102, -34, -77, 120, -47, 1, 85, 21, 27, 13, -67, 45, -128, 0, 1, 2, 4, 8, 16, 
            32, 64, -128, 0, 1, 2, 4, 8, -4, 33, -80, -15, 25, -49, -26, 3, -65, 3, -60, -95, 
            -83, 118, -11, -39, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126
        };

        try {
            if(path.equals("void.png")){
                image = ImageIO.read(new ByteArrayInputStream(void_image));
            }
            else image = ImageIO.read(new File(path));
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
