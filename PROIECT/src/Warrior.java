import java.util.LinkedList;
import java.util.Random;

public class Warrior extends Character{

     public Warrior(){
         //Warrior are mai mult maxWeight dar mai putin MP
         maxHealth = 100;
         maxMana = 50;
         currentHealth = maxHealth;
         currentMana = maxMana;
         currentLevel = 1;
         currentExp = 0;
         fireProtection = true;
         i.maxWeight = 200;
         str = 3;
         dex = 2;
         cha = 1;
         Abilities = new LinkedList<Spell>();
         Abilities.add(new Fire());
         Abilities.add(new Earth());
         Abilities.add(new Ice());

     }

    int getDamage() {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            return str * 5;
        }
        else{
            return str * 10;
        }
    }

    void receiveDamage(int damage) {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            currentHealth = currentHealth - (damage * ( dex + cha) / 20);
        }
        else{
            currentHealth = currentHealth - (damage * ( dex + cha) / 40);
        }
    }
}
