import java.util.LinkedList;
import java.util.Random;

public class Rogue extends Character{
    public Rogue(){
        //Rogue e echilibrat
        maxHealth = 100;
        maxMana = 100;
        currentHealth = maxHealth;
        currentMana = maxMana;
        currentLevel = 1;
        currentExp = 0;
        earthProtection = true;
        i.maxWeight = 100;
        str = 1;
        dex = 3;
        cha = 2;
        Abilities = new LinkedList<Spell>();
        Abilities.add(new Fire());
        Abilities.add(new Earth());
        Abilities.add(new Ice());
    }

    int getDamage() {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            return dex * 5;
        }
        else{
            return dex * 10;
        }
    }

    void receiveDamage(int damage) {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            currentHealth = currentHealth - (damage * ( str + cha) / 20);
        }
        else{
            currentHealth = currentHealth - (damage * ( str + cha) / 40);
        }
    }
}