import java.util.*;

public class Grid extends ArrayList<Cell> {
    int length;
    int width;
    int pos_x;
    int pos_y;
    HashMap<Integer, Shop> shops;

    private Grid(int length, int width){
        this.length = length;
        this.width = width;
        pos_x = 0;
        pos_y = 0;
        shops = new HashMap<Integer, Shop>();
    }

    //se itereaza mai multe versiuni ale gridului pana indeplineste cerintele dorite
    public static Grid generate_grid(int length, int width) {
        Grid g = new Grid(length, width);
        Random r = new Random();
        int numberOfShops = 0;
        int numberOfEnemies = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                String Types = "ESF";
                if(i == 0 && j == 0){
                    Cell cell = new Cell(i, j);
                    g.add(cell);
                }
                else if(i == width - 1 && j == length - 1){
                    Cell cell = new Cell(i, j);
                    cell.Type = Cell.cellType.FINISH;
                    g.add(cell);
                }
                else if (Types.charAt(r.nextInt(Types.length())) == 'E') {
                    Cell Enemy = new Cell(i, j);
                    Enemy.Type = Cell.cellType.ENEMY;
                    g.add(Enemy);
                    numberOfEnemies++;
                }
                else if (Types.charAt(r.nextInt(Types.length())) == 'S') {
                    Cell Shop = new Cell(i, j);
                    Shop.Type = Cell.cellType.SHOP;
                    g.add(Shop);
                    numberOfShops++;
                }
                else{
                    Cell cell = new Cell(i, j);
                    g.add(cell);
                }
            }
        }

        if (numberOfShops >1 && numberOfEnemies > 3) {
                    return g;
        }

        else return generate_grid(length, width);

    }

    //printarea stringului
    public String toString(){
        String result = "";
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                if(!get(i*width + j).visited){
                    //daca o casuta nu este vizitata va aparea ca ?
                    result += '?';
                }
                else {
                    if (get(i * width + j).Type.equals(Cell.cellType.EMPTY)) {
                        if (pos_x == i && pos_y == j) {
                            //daca playerul e pe o casuta goala se va afisa doar P
                            result += 'P';
                        } else result += 'N';

                    }
                    // daca nu se va afisa P si tipul de celula
                    else if (get(i * width + j).Type.equals(Cell.cellType.ENEMY)) {
                        if (pos_x == i && pos_y == j) {
                            result += 'P';
                        }
                        result += 'E';
                    } else if (get(i * width + j).Type.equals(Cell.cellType.SHOP)) {
                        if (pos_x == i && pos_y == j) {
                            result += 'P';
                        }
                        result += 'S';
                    } else if (get(i * width + j).Type.equals(Cell.cellType.FINISH)) {
                        if (pos_x == i && pos_y == j) {
                            result += 'P';
                        }
                        result += 'F';
                    }
                }
                result += ' ';
            }
            result += "\n";
        }
        return result;
    }

    Cell characterPos;
    public void goWest(){
        int check = pos_y - 1;
        if(check < 0){
            System.out.println("");
            //verific daca se iese din grid
            System.out.println("Out of Bounds");
        }
        else {
            pos_y--;
        }

    }
    public void goEast(){
        int check = pos_y + 1;
        if(check == length){
            System.out.println("");
            System.out.println("Out of Bounds");
        }
        else {
            pos_y++;
        }
    }

    public void goNorth(){
        int check = pos_x - 1;
        if(check < 0){
            System.out.println("");
            System.out.println("Out of Bounds");
        }
        else {
            pos_x--;
        }
    }

    public void goSouth(){
        int check = pos_x + 1;
        if(check == width){
            System.out.println("");
            System.out.println("Out of Bounds");
        }
        else {
            pos_x++;
        }
    }
}
