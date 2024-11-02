public class Point {

    public Tile old_tile, new_tile;
    public int x, y;
    public boolean is_flooded;

    public Point(int x, int y){ // for bucket
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, Tile old_tile, Tile new_tile, boolean is_flooded){ // for undo and redo
        this.x = x;
        this.y = y;
        this.old_tile = old_tile;
        this.new_tile = new_tile;
        this.is_flooded = is_flooded;
    }
    
}
