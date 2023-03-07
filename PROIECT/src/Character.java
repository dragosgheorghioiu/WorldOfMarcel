public class Character extends Entity{
    public String name;
    int pos_x;
    int pos_y;
    Inventory i = new Inventory();
    public int currentExp;
    public int currentLevel;
    int str, dex, cha;

    public void buyHealthPotion(HealthPotion p){
        if(i.money < p.getPrice()){
            System.out.println("Too pricy");
        }
        else{
            if(i.remainingWeight() < p.getWeight()){
                System.out.println("Too heavy");
            }
            i.money -= p.getPrice();
            i.addPotion(p);
        }
    }

    public void buyManaPotion(ManaPotion p){
        if(i.money < p.getPrice()){
            System.out.println("Too pricy");
        }
        else{
            if(i.remainingWeight() < p.getWeight()){
                System.out.println("Too heavy");
            }
            else {
                i.money -= p.getPrice();
                i.addPotion(p);
            }
        }
    }

    int getDamage() {
        return 3 * str + 2 * dex +cha;
    }

    void receiveDamage(int damage) {
        currentHealth -= (damage - 3 * str + 2 * dex +cha);
    }

    public void getExp(int Exp){
        currentExp += Exp;
        if(currentExp > 100){
            System.out.println("Level up!");
            currentExp -= 100;
            currentLevel++;
            str = str + currentLevel;
            cha = cha + currentLevel;
            dex = dex + currentLevel;
        }
    }
}
