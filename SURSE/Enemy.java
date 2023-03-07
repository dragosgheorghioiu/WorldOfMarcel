import java.util.LinkedList;
import java.util.Random;

public class Enemy extends Entity implements CellElement{

    int numberOfSpells;
    public Enemy(){
        Random r = new Random();
        maxHealth = r.nextInt((100 - 25) + 1) + 25;
        currentHealth = maxHealth;
        maxMana = r.nextInt((200 - 10) + 1) + 10;
        fireProtection = r.nextBoolean();
        earthProtection = r.nextBoolean();
        iceProtection = r.nextBoolean();

        currentMana = maxMana;
        Abilities = new LinkedList<Spell>();
        int spellType;
        numberOfSpells = r.nextInt((4 - 2) + 1) + 2;
        for(int i = 0; i < numberOfSpells; i++){
            spellType = r.nextInt((3 - 1) + 1) + 1;
            if(spellType == 1){
                Abilities.add(new Fire());
            }

            if(spellType == 2){
                Abilities.add(new Earth());
            }

            if(spellType == 3){
                Abilities.add(new Ice());
            }
        }
    }

    int getDamage() {
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        int damage = r.nextInt(20 - 1 + 1) + 1;
        if(chance == 1){
            return damage * 2;
        }

        else{
            return damage;
        }
    }

    void receiveDamage(int damageTaken){
        Random r = new Random();
        int chance = r.nextInt(2 - 1 + 1) + 1;
        if(chance == 1){
            currentHealth -= damageTaken;
        }
    }

    public char toCharacter() {
        return 'E';
    }
}
