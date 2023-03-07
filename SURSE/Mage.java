import java.util.LinkedList;
import java.util.Random;

public class Mage extends Character{
    //mageul are mai putin maxWeight dar mai mult MP maxim
    public Mage(){
        maxHealth = 100;
        maxMana = 200;
        currentHealth = maxHealth;
        currentMana = maxMana;
        currentLevel = 1;
        currentExp = 0;
        iceProtection = true;
        i.maxWeight = 50;
        str = 2;
        dex = 1;
        cha = 3;
        Abilities = new LinkedList<Spell>();
        Abilities.add(new Fire());
        Abilities.add(new Earth());
        Abilities.add(new Ice());
    }

    int getDamage() {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            return cha * 5;
        }
        else{
            return cha * 10;
        }
    }

    void receiveDamage(int damage) {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            currentHealth = currentHealth - (damage * ( dex + str) / 20);
        }
        else{
            currentHealth = currentHealth - (damage * ( dex + str) / 40);
        }
    }
}