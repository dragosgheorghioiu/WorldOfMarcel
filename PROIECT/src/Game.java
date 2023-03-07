import java.io.InputStream;
import java.util.*;

import org.json.*;

public class Game {
    private static Game game;
    LinkedList <Account> Accounts;
    HashMap < Cell.cellType, LinkedList < String >> Stories;

    private Game(){
        Accounts = new LinkedList<Account>();
        Stories = new HashMap<Cell.cellType, LinkedList < String>>();
    }

    public static synchronized Game getInstance() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    //incarc informatiile din fisierele JSON pentru accounts si stories
    public char run(){
        String resourceName = "JSONS/stories.json";
        InputStream is = Main.class.getResourceAsStream(resourceName);
        LinkedList empty = new LinkedList<String>();
        LinkedList enemy = new LinkedList<String>();
        LinkedList shop = new LinkedList<String>();
        LinkedList finish = new LinkedList<String>();

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);


        JSONArray stories = object.getJSONArray("stories");
        for (int i = 0; i < stories.length(); i++) {
            JSONObject jsonobject = stories.getJSONObject(i);
            if(jsonobject.get("type").equals("EMPTY")){
                empty.add((String) jsonobject.get("value"));
            }
            else if(jsonobject.get("type").equals("ENEMY")){
                enemy.add((String) jsonobject.get("value"));
            }
            else if(jsonobject.get("type").equals("SHOP")){
                shop.add((String) jsonobject.get("value"));
            }
            if(jsonobject.get("type").equals("FINISH")){
                finish.add((String) jsonobject.get("value"));
            }
        }

        Stories.put(Cell.cellType.EMPTY, empty);
        Stories.put(Cell.cellType.ENEMY, enemy);
        Stories.put(Cell.cellType.SHOP, shop);
        Stories.put(Cell.cellType.FINISH, finish);

        String resourceName1 = "JSONS/accounts.json";
        InputStream is1 = Main.class.getResourceAsStream(resourceName1);
        JSONTokener tokener1 = new JSONTokener(is1);
        JSONObject object1 = new JSONObject(tokener1);

        JSONArray accounts = object1.getJSONArray("accounts");
        for (int i = 0; i < accounts.length(); i++) {
            JSONObject jsonobject = accounts.getJSONObject(i);
            Account a = new Account(jsonobject.getJSONObject("credentials").getString("email"), jsonobject.getJSONObject("credentials").getString("password"));
            String Name = jsonobject.getString("name");
            String Country = jsonobject.getString("country");
            JSONArray jsonarray = jsonobject.getJSONArray("favorite_games");
            for (int j = 0; j < jsonarray.length(); j++){
                a.i.s.add(jsonarray.getString(j));
            }
            a.i.s.sort(null);
            int maps_completed = jsonobject.getInt("maps_completed");
            a.nr_games = maps_completed;
            a.i.Country = Country;
            a.i.Name = Name;
            //a.s
            JSONArray characters = jsonobject.getJSONArray("characters");
            for (int j = 0; j < characters.length(); j++){
                JSONObject character = characters.getJSONObject(j);
                if(character.getString("profession").equals("Warrior")){
                    Warrior w = new Warrior();
                    w.name = character.getString("name");
                    w.currentExp = character.getInt("experience");
                    w.currentLevel = character.getInt("level");
                    a.s.add(w);
                }

                if(character.getString("profession").equals("Mage")){
                    Mage m = new Mage();
                    m.name = character.getString("name");
                    m.currentExp = character.getInt("experience");
                    m.currentLevel = character.getInt("level");
                    a.s.add(m);
                }

                if(character.getString("profession").equals("Rogue")){
                    Rogue r = new Rogue();
                    r.name = character.getString("name");
                    r.currentExp = character.getInt("experience");
                    r.currentLevel = character.getInt("level");
                    a.s.add(r);
                }
            }
            Accounts.add(a);

        }


        Scanner sc = new Scanner(System.in);

        //se alege modul de joc
        System.out.println("Choose game mode");
        System.out.println("1: Terminal");
        System.out.println("2: GUI");
        char mode = sc.next().charAt(0);

        return mode;
    }

    //functie pentru citirea unei povesti corespunzatoare tipului de casuta
    public void readStory(Cell.cellType type){
        Random r = new Random();
        String Story = Stories.get(type).get(r.nextInt(Stories.get(type).size()));
        System.out.println(Story);
    }

    public void readOptions(Grid g, Cell.cellType type, Character character, int pos) throws InvalidCommandException{
        Scanner sc = new Scanner(System.in);

        //pentru EMPTY se poate realiza deplasarea in cele 4 directii cardinale, verificarea inventarului sau a numarului de bani
        if(type.equals(Cell.cellType.EMPTY)){
            System.out.println("W: Move North");
            System.out.println("S: Move South");
            System.out.println("A: Move West");
            System.out.println("D: Move East");
            System.out.println("I: Open Inventory");
            System.out.println("C: Check Money Pouch");
            System.out.println();
            char input = sc.next().charAt(0);
                if (input == 'w') {
                    g.goNorth();
                }
                if (input == 's') {
                    g.goSouth();
                }
                if (input == 'a') {
                    g.goWest();
                }
                if (input == 'd') {
                    g.goEast();
                }
                if (input == 'i') {
                    System.out.println(character.i.toString());
                }
                if (input == 'c') {
                    System.out.println("You have " + character.i.money + " gold coins");
                }
                if (input == 'e') {
                    System.out.println("You have " + character.currentExp + "exp");
                }
                if (input == 'l') {
                    System.out.println("You are level " + character.currentLevel);
                }
                String keys = "wasdicel";
                if (keys.indexOf(input) == -1) {
                    throw new InvalidCommandException("Invalid input");
                }

        }

        //combat system
        if(type.equals(Cell.cellType.ENEMY)){

            Enemy enemy = new Enemy();
            if(character.currentHealth > 0) {
                while (enemy.currentHealth > 0 && character.currentHealth > 0) {
                    //afisez informatii despre dusman
                    System.out.println("Enemy HP: " + enemy.currentHealth);
                    System.out.println("Enemy MP: " + enemy.currentMana);
                    System.out.println("");

                    //afisez informatii despre player
                    System.out.println("Your HP: " + character.currentHealth);
                    System.out.println("Your MP: " + character.currentMana);
                    System.out.println("");
                    System.out.println("I: Attack");
                    System.out.println("O: Spells");
                    System.out.println("P: Open Inventory");
                    char input = sc.next().charAt(0);
                    String keys = "iop";
                    if (keys.indexOf(input) == -1) {
                        throw new InvalidCommandException("Invalid input");
                    }

                    //pentru atac calculez conform cerintelor din enunt
                    if (input == 'i') {
                        enemy.receiveDamage(character.getDamage());
                    }

                    //deschide inventarul de potiuni
                    if (input == 'p') {
                        if (character.i.Potions.isEmpty()) {
                            System.out.println("You have no potions");
                        } else {
                            //pentru nu a alege o potiune se apasa 0
                            System.out.println("0: Return to map");
                            //pentru a alege o potiune se apasa unul din numere afisate pe ecran
                            System.out.println(character.i.toString());
                            int potionNumber = sc.nextInt();
                            if (potionNumber > character.i.Potions.size()) {
                                throw new InvalidCommandException("Invalid input");
                            }
                            if (potionNumber != 0) {
                                if (character.i.Potions.get(potionNumber - 1) instanceof HealthPotion) {
                                    //efectul potiunii
                                    character.healthRegen(((HealthPotion) character.i.Potions.get(potionNumber - 1)).Usage);
                                    //golirea inventarului
                                    character.i.Potions.get(potionNumber - 1).usePotion();
                                    character.i.checkPotions();
                                    //afisarea unui mesaj cu informatii despre efect
                                    System.out.println("HP + 75");
                                } else {
                                    //efectul potiunii
                                    character.manaRegen(((ManaPotion) character.i.Potions.get(potionNumber - 1)).Usage);
                                    //golirea inventarului
                                    character.i.Potions.get(potionNumber - 1).usePotion();
                                    character.i.checkPotions();
                                    //afisarea unui mesaj cu informatii despre efect
                                    System.out.println("MP + 50");
                                }
                            }
                        }
                    }
                    if (input == 'o') {
                        System.out.println("F: Fire");
                        System.out.println("E: Earth");
                        System.out.println("I: Ice");
                        input = sc.next().charAt(0);
                        String spells = "fei";
                        if (spells.indexOf(input) == -1) {
                            throw new InvalidCommandException("Invalid input");
                        }

                        //apare o lista sperata cu tipurile de spell valabile
                        //verific daca e suficienta mana
                        //verific daca dusmanul este imun la elementul respectiv
                        //consum mana dupa folosirea spellului
                        if (input == 'f') {
                            if (character.currentMana - character.Abilities.get(0).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.fireProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(0).damage);
                                    character.currentMana -= character.Abilities.get(0).manaCost;
                                } else {
                                    System.out.println("Imune to Fire");
                                    character.currentMana -= character.Abilities.get(0).manaCost;
                                }
                            }
                        }
                        if (input == 'e') {
                            if (character.currentMana - character.Abilities.get(1).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.earthProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(1).damage);
                                    character.currentMana -= character.Abilities.get(1).manaCost;
                                } else {
                                    System.out.println("Imune to Earth");
                                    character.currentMana -= character.Abilities.get(1).manaCost;
                                }
                            }
                        }
                        if (input == 'i') {
                            if (character.currentMana - character.Abilities.get(2).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.iceProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(2).damage);
                                    character.currentMana -= character.Abilities.get(2).manaCost;
                                } else {
                                    System.out.println("Imune to ice");
                                    character.currentMana -= character.Abilities.get(2).manaCost;
                                }
                            }
                        }

                    }

                    //dusmanul poate face ce face si playerul mai putin folosirea unei potiuni
                    if (enemy.currentHealth > 0) {
                        Random r = new Random();

                        //se genereaza un numar random pentru a determina tura dusmanului
                        int action = r.nextInt((enemy.numberOfSpells - 0) + 1) + 0;
                        if (action == enemy.numberOfSpells) {
                            character.receiveDamage(enemy.getDamage());
                            System.out.println("Enemy Attacks");
                        } else {
                            if (enemy.Abilities.get(action).damage == 50) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.fireProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Fire");
                                    } else {
                                        System.out.println("You are imune to fire");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                            if (enemy.Abilities.get(action).damage == 60) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.earthProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Earth");
                                    } else {
                                        System.out.println("You are imune to earth");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                            if (enemy.Abilities.get(action).damage == 40) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.iceProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Ice");
                                    } else {
                                        System.out.println("You are imune to ice");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                        }
                    }

                }
                if(character.currentHealth > 0) {
                    //daca personajul castiga se afiseaza un mesaj
                    System.out.println("You Win!");
                    Random r = new Random();
                    int chance = r.nextInt((5 - 1) + 1) + 1;
                    if (chance != 1) {
                        int money = r.nextInt((10 - 1) + 1) + 1;
                        //se ofera bani(80% sanse)
                        System.out.println("You have earned " + money + " gold coins!");
                        character.i.money += money;
                    }
                    //se ofera exp random
                    //se face level up daca este cazul
                    int exp = r.nextInt((90 - 30) + 1) + 1;
                    System.out.println("You have earned " + exp + " exp!");
                    character.getExp(exp);
                    System.out.println("");
                    g.get(g.pos_x * g.width + g.pos_y).Type = Cell.cellType.EMPTY;
                }
            }


        }
        if(type.equals(Cell.cellType.SHOP)){
            if(g.shops.get(pos) == null) {
                Shop shop = new Shop();
                g.shops.put(pos, shop);
            }
            System.out.println("W: Move North");
            System.out.println("S: Move South");
            System.out.println("A: Move West");
            System.out.println("D: Move East");
            System.out.println("I: Open Inventory");
            System.out.println("C: Check Money Pouch");
            System.out.println("O: Open Shop");
            System.out.println();
            char input = sc.next().charAt(0);
            String keys = "wasdico";
            if (keys.indexOf(input) == -1) {
                throw new InvalidCommandException("Invalid input");
            }

            //se poate ignora shopul
            if(input == 'w'){
                g.goNorth();
            }
            if(input == 's'){
                g.goSouth();
            }
            if(input == 'a'){
                g.goWest();
            }
            if(input == 'd'){
                g.goEast();
            }
            if(input == 'i'){
                if(character.i.Potions.isEmpty()){
                    System.out.println("You have no potions");
                }
                else {
                    System.out.println("0: Return to map");
                    System.out.println(character.i.toString());
                    int potionNumber = sc.nextInt();
                    if (potionNumber >= character.i.Potions.size()) {
                        throw new InvalidCommandException("Invalid input");
                    }
                    if(potionNumber != 0) {
                        if (character.i.Potions.get(potionNumber - 1) instanceof HealthPotion) {
                            character.healthRegen(((HealthPotion) character.i.Potions.get(potionNumber - 1)).Usage);
                            character.i.Potions.get(potionNumber - 1).usePotion();
                            character.i.checkPotions();
                        } else {
                            character.manaRegen(((ManaPotion) character.i.Potions.get(potionNumber - 1)).Usage);
                            character.i.Potions.get(potionNumber - 1).usePotion();
                            character.i.checkPotions();
                        }
                    }
                }
            }
            if(input == 'c'){
                System.out.println("You have " + character.i.money + " gold coins");
            }

            //se afiseaza potiunile din stocul shopului si se alege care este de cumparat
            if (input == 'o'){
                Shop shop = g.shops.get(pos);
                if(shop.Potions.isEmpty()){
                    System.out.println("Shop is out of stock");
                }
                else {
                    System.out.println("0: Return to map");
                    System.out.println(shop.toString());
                    int potionNumber = sc.nextInt();
                    if (potionNumber >= shop.Potions.size()) {
                        throw new InvalidCommandException("Invalid input");
                    }
                    if(potionNumber != 0) {
                        if (shop.Potions.get(potionNumber - 1) instanceof HealthPotion) {
                            character.buyHealthPotion((HealthPotion) shop.Potions.get(potionNumber - 1));
                            shop.Potions.get(potionNumber - 1).usePotion();
                            shop.checkPotions();
                        } else {
                            character.buyManaPotion((ManaPotion) shop.Potions.get(potionNumber - 1));
                            shop.Potions.get(potionNumber - 1).usePotion();
                            shop.checkPotions();
                        }
                    }
                }
            }

        }
        if(type.equals(Cell.cellType.FINISH)){
            System.out.println("Congratulations!");
        }

    }

    //functie pentru rularea doar prin apasarea tastei "P"
    public void readOptions_test(Grid g, Cell.cellType type, Character character, int pos, int step){
        Scanner sc = new Scanner(System.in);


        if(type.equals(Cell.cellType.EMPTY)){
            System.out.println("W: Move North");
            System.out.println("S: Move South");
            System.out.println("A: Move East");
            System.out.println("D: Move West");
            System.out.println("I: Open Inventory");
            System.out.println("C: Check Money Pouch");
            System.out.println();
            char input = sc.next().charAt(0);
            if (input == 'p' && step < 3) {
                g.goEast();
            }
            else if (input == 'p' && step <= 10)
                g.goSouth();
        }
        if(type.equals(Cell.cellType.ENEMY)){

            Enemy enemy = new Enemy();
            if(character.currentHealth > 0) {
                while (enemy.currentHealth > 0 && character.currentHealth > 0) {
                    System.out.println("Enemy HP: " + enemy.currentHealth);
                    System.out.println("Enemy MP: " + enemy.currentMana);
                    System.out.println("");
                    System.out.println("Your HP: " + character.currentHealth);
                    System.out.println("Your MP: " + character.currentMana);
                    System.out.println("");
                    System.out.println("I: Attack");
                    System.out.println("O: Spells");
                    System.out.println("P: Open Inventory");
                    char input = sc.next().charAt(0);
                    if (input == 'p' && step == 9) {
                        enemy.receiveDamage(character.getDamage());
                        System.out.println("You attacked");
                    }
                    if (input == 'p' && (step == 10 || step == 11)) {
                        if (character.i.Potions.isEmpty()) {
                            System.out.println("You have no potions");

                        } else {
                            System.out.println("0: Return to map");
                            System.out.println(character.i.toString());
                            char potionNumber = sc.next().charAt(0);
                            if (potionNumber == 'p') {
                                if (step == 10) {
                                    character.healthRegen(((HealthPotion) character.i.Potions.get(0)).Usage);
                                    character.i.Potions.get(0).usePotion();
                                    character.i.checkPotions();
                                    System.out.println("HP + 75");
                                } else {
                                    character.manaRegen(((ManaPotion) character.i.Potions.get(0)).Usage);
                                    character.i.Potions.get(0).usePotion();
                                    character.i.checkPotions();
                                    System.out.println("HP + 50");
                                }
                            }
                        }
                    }
                    if (input == 'p' && step > 11) {
                        System.out.println("F: Fire");
                        System.out.println("E: Earth");
                        System.out.println("I: Ice");
                        input = sc.next().charAt(0);
                        String spells = "fei";
                        if (step == 12) {
                            if (character.currentMana - character.Abilities.get(0).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.fireProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(0).damage);
                                    character.currentMana -= character.Abilities.get(0).manaCost;
                                } else {
                                    System.out.println("Imune to Fire");
                                    character.currentMana -= character.Abilities.get(0).manaCost;
                                }
                            }
                        }
                        if (step == 13) {
                            if (character.currentMana - character.Abilities.get(1).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.earthProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(1).damage);
                                    character.currentMana -= character.Abilities.get(1).manaCost;
                                } else {
                                    System.out.println("Imune to Earth");
                                    character.currentMana -= character.Abilities.get(1).manaCost;
                                }
                            }
                        }
                        if (step == 14) {
                            if (character.currentMana - character.Abilities.get(2).manaCost < 0) {
                                System.out.println("Not enough Mana");

                            } else {
                                if (enemy.iceProtection == true) {
                                    enemy.receiveDamage(character.Abilities.get(2).damage);
                                    character.currentMana -= character.Abilities.get(2).manaCost;
                                } else {
                                    System.out.println("Imune to ice");
                                    character.currentMana -= character.Abilities.get(2).manaCost;
                                }
                            }
                        }

                    }
                    if (input == 'p' && step > 14){
                        enemy.receiveDamage(character.getDamage());
                        System.out.println("You attacked");
                    }
                    if (enemy.currentHealth > 0) {
                        Random r = new Random();
                        int action = r.nextInt((enemy.numberOfSpells - 0) + 1) + 0;
                        if (action == enemy.numberOfSpells) {
                            character.receiveDamage(enemy.getDamage());
                            System.out.println("Enemy Attacks");
                        } else {
                            if (enemy.Abilities.get(action).damage == 50) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.fireProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Fire");
                                    } else {
                                        System.out.println("You are imune to fire");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                            if (enemy.Abilities.get(action).damage == 60) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.earthProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Earth");
                                    } else {
                                        System.out.println("You are imune to earth");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                            if (enemy.Abilities.get(action).damage == 40) {
                                if (enemy.currentMana - enemy.Abilities.get(action).manaCost < 0)
                                    System.out.println("Enemy does not have enough Mana");
                                else {
                                    if (character.iceProtection == false) {
                                        character.receiveDamage(enemy.Abilities.get(action).damage);
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                        System.out.println("Enemy uses Ice");
                                    } else {
                                        System.out.println("You are imune to ice");
                                        enemy.currentMana -= enemy.Abilities.get(action).manaCost;
                                    }
                                }
                            }
                        }
                    }

                    step++;
                }
                if(character.currentHealth > 0) {
                    System.out.println("You Win!");
                    Random r = new Random();
                    int chance = r.nextInt((5 - 1) + 1) + 1;
                    if (chance != 1) {
                        int money = r.nextInt((10 - 1) + 1) + 1;
                        System.out.println("You have earned " + money + " gold coins!");
                        character.i.money += money;
                    }
                    int exp = r.nextInt((90 - 30) + 1) + 1;
                    System.out.println("You have earned " + exp + " exp!");
                    character.getExp(exp);
                    System.out.println("");
                    g.get(g.pos_x * g.width + g.pos_y).Type = Cell.cellType.EMPTY;
                    System.out.println(g.toString());
                }
            }


        }
        if(type.equals(Cell.cellType.SHOP)){
            if(g.shops.get(pos) == null) {
                Shop shop = new Shop();
                shop.Potions.add(new ManaPotion());
                shop.Potions.add(new HealthPotion());
                g.shops.put(pos, shop);
            }
            System.out.println("W: Move North");
            System.out.println("S: Move South");
            System.out.println("A: Move East");
            System.out.println("D: Move West");
            System.out.println("I: Open Inventory");
            System.out.println("C: Check Money Pouch");
            System.out.println("O: Open Shop");
            System.out.println();
            char input = sc.next().charAt(0);
            if (input == 'p' && step == 5) {
                g.goEast();
            }
            if(input == 'w'){
                g.goNorth();
            }
            if(input == 's'){
                g.goSouth();
            }
            if(input == 'a'){
                g.goWest();
            }
            if(input == 'd'){
                g.goEast();
            }
            if(input == 'i'){
                if(character.i.Potions.isEmpty()){
                    System.out.println("You have no potions");
                }
                else {
                    System.out.println("0: Return to map");
                    System.out.println(character.i.toString());
                    char potionNumber = sc.next().charAt(0);
                    if(potionNumber == 'p') {
                        if (step == 3) {
                            character.healthRegen(((HealthPotion) character.i.Potions.getLast()).Usage);
                            character.i.Potions.getLast().usePotion();
                            character.i.checkPotions();
                        } else {
                            character.manaRegen(((ManaPotion) character.i.Potions.getLast()).Usage);
                            character.i.Potions.getLast().usePotion();
                            character.i.checkPotions();
                        }
                    }
                }
            }
            if(input == 'c'){
                System.out.println("You have " + character.i.money + " gold coins");
            }

            if (input == 'p' && step < 5){
                Shop shop = g.shops.get(pos);
                if(shop.Potions.isEmpty()){
                    System.out.println("Shop is out of stock");
                }
                else {
                    System.out.println("0: Return to map");
                    System.out.println(shop.toString());
                    char potionNumber = sc.next().charAt(0);
                    if(potionNumber == 'p') {
                        if (step == 3) {
                            character.buyHealthPotion((HealthPotion) shop.Potions.getLast());
                            shop.Potions.getLast().usePotion();
                            shop.checkPotions();
                        } else {
                            character.buyManaPotion((ManaPotion) shop.Potions.getLast());
                            shop.Potions.getLast().usePotion();
                            shop.checkPotions();
                        }
                    }
                }
            }

        }
        if(type.equals(Cell.cellType.FINISH)){
            System.out.println("Congratulations!");
        }

    }

}
