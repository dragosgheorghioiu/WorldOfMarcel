import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //instantiez Game
        Game.getInstance();
        Game.getInstance().run();
        Scanner sc = new Scanner(System.in);

        //afisez conturile
        for(int i = 0; i < Game.getInstance().Accounts.size(); i++)
            System.out.println(i+1 + ": " + Game.getInstance().Accounts.get(i).i.Name);

        try{
        int name = sc.nextInt();
        if (name >= Game.getInstance().Accounts.size()) {
            throw new InvalidCommandException("Invalid input");
        }
        Account a = Game.getInstance().Accounts.get(name - 1);

        //afisez personajele
        for(int i = 0; i < a.s.size(); i++) {
            System.out.println("Character " + (i+1) +": " + a.s.get(i).name);
            if(a.s.get(i).i.maxWeight == 200){
                System.out.println("    Class: Warrior");
            }
            if(a.s.get(i).i.maxWeight == 100){
                System.out.println("    Class: Rogue");
            }
            if(a.s.get(i).i.maxWeight == 50){
                System.out.println("    Class: Mage");
            }

        }

        int character = sc.nextInt();
        if (name >= Game.getInstance().Accounts.size()) {
            throw new InvalidCommandException("Invalid input");
        }

        //generez gridul
        Character playable = a.s.get(character - 1);
        Grid g = Grid.generate_grid(5,5);
        g.get(0).visited = true;

            while (g.pos_x < g.width - 1 || g.pos_y < g.length - 1) {
                System.out.println("");
                //printez o poveste random daca casuta nu a fost vizitata pana acum
                if (!g.get(g.pos_x * g.width + g.pos_y).visited) {
                    Game.getInstance().readStory(g.get(g.pos_x * g.width + g.pos_y).Type);
                }
                //sansa de 20% de a primi bani cand se ajunge prima oara pe o casuta EMPTY
                if (g.get(g.pos_x * g.width + g.pos_y).Type == Cell.cellType.EMPTY && !g.get(g.pos_x * g.width + g.pos_y).visited) {
                    Random r = new Random();
                    int chance = r.nextInt((5 - 1) + 1) + 1;
                    if (chance == 1) {
                        int money = r.nextInt((10 - 1) + 1) + 1;
                        System.out.println("You have found " + money + " gold coins!");
                        playable.i.money += money;
                    }
                }
                //fac casuta curenta vizitata
                g.get(g.pos_x * g.width + g.pos_y).visited = true;
                //printez gridul
                System.out.println(g.toString());
                //functie de afisare si citire a optionilor din fiecare casuta
                Game.getInstance().readOptions(g, g.get(g.pos_x * g.width + g.pos_y).Type, playable, g.pos_x * g.width + g.pos_y);
                //daca personajul moare se termina jocul
                if(playable.currentHealth < 1){
                    System.out.println("You died");
                    break;
                }
            }
            System.out.println("");
            //daca se ajunge la finish
            if(playable.currentHealth > 1) {
                Game.getInstance().readStory(g.get(g.pos_x * g.width + g.pos_y).Type);
                Game.getInstance().readOptions(g, g.get(g.pos_x * g.width + g.pos_y).Type, playable, g.pos_x * g.width + g.pos_y);
            }
        }
        catch (InvalidCommandException e){
            System.out.println(e);
        }
    }
}

class Test {

    public static void main(String[] args) {

        Game.getInstance();
        Game.getInstance().run();
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < Game.getInstance().Accounts.size(); i++)
            System.out.println(i+1 + ": " + Game.getInstance().Accounts.get(i).i.Name);

        try{
            int name = sc.nextInt();
            if (name >= Game.getInstance().Accounts.size()) {
                throw new InvalidCommandException("Invalid input");
            }
            Account a = Game.getInstance().Accounts.get(name - 1);

            for(int i = 0; i < a.s.size(); i++) {
                System.out.println("Character " + (i+1) +": " + a.s.get(i).name);
                if(a.s.get(i).i.maxWeight == 200){
                    System.out.println("    Class: Warrior");
                }
                if(a.s.get(i).i.maxWeight == 100){
                    System.out.println("    Class: Rogue");
                }
                if(a.s.get(i).i.maxWeight == 50){
                    System.out.println("    Class: Mage");
                }

            }

            int character = sc.nextInt();
            if (name >= Game.getInstance().Accounts.size()) {
                throw new InvalidCommandException("Invalid input");
            }

            Character playable = a.s.get(character - 1);
            playable.i.money = 40;
            Grid g = Grid.generate_grid(5,5);
            for(int i = 0; i < g.width * g.length; i++){
                g.get(i).Type = Cell.cellType.EMPTY;
            }
            g.get(0 * g.width + 3).Type = Cell.cellType.SHOP;
            g.get(1 * g.width + 3).Type = Cell.cellType.SHOP;
            g.get(2 * g.width + 0).Type = Cell.cellType.SHOP;
            g.get(3 * g.width + 4).Type = Cell.cellType.ENEMY;

            g.get(0).visited = true;
            int i = 0;

            while (g.pos_x < g.width - 1 || g.pos_y < g.length - 1) {
                System.out.println("");
                if (!g.get(g.pos_x * g.width + g.pos_y).visited) {
                    Game.getInstance().readStory(g.get(g.pos_x * g.width + g.pos_y).Type);
                }
                if (g.get(g.pos_x * g.width + g.pos_y).Type == Cell.cellType.EMPTY && !g.get(g.pos_x * g.width + g.pos_y).visited) {
                    Random r = new Random();
                    int chance = r.nextInt((5 - 1) + 1) + 1;
                    if (chance == 1) {
                        int money = r.nextInt((10 - 1) + 1) + 1;
                        System.out.println("You have found " + money + " gold coins!");
                        playable.i.money += money;
                    }
                }
                g.get(g.pos_x * g.width + g.pos_y).visited = true;
                System.out.println(g.toString());
                Game.getInstance().readOptions_test(g, g.get(g.pos_x * g.width + g.pos_y).Type, playable, g.pos_x * g.width + g.pos_y, i);
                if(playable.currentHealth < 1){
                    System.out.println("You died");
                    break;
                }
                System.out.println(i);
                i++;
            }
            System.out.println("");
            if(playable.currentHealth > 1) {
                Game.getInstance().readStory(g.get(g.pos_x * g.width + g.pos_y).Type);
            }
        }
        catch (InvalidCommandException e){
            System.out.println(e);
        }
    }
}