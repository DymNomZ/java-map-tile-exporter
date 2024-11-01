public class Point {

    public Tile tile;
    public int x, y;
    public boolean is_flooded;

    public Point(int x, int y){ // for bucket
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, Tile tile, boolean is_flooded){ // for undo and redo
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.is_flooded = is_flooded;
    }
    
}
