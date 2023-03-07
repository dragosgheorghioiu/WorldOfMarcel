public class Cell {

    public int pos_x;
    public int pos_y;
    public enum cellType{
        EMPTY,
        ENEMY,
        SHOP,
        FINISH
    }
    public cellType Type;
    public Boolean visited;
    public CellElement content;

    public Cell(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        Type = cellType.EMPTY;
        visited = false;
    }

}


