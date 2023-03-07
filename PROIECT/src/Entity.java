import java.util.*;

abstract class Entity {
    LinkedList<Spell> Abilities;
    int maxHealth;
    int currentHealth;
    int maxMana;
    int currentMana;
    Boolean fireProtection = false;
    Boolean iceProtection = false;
    Boolean earthProtection = false;

    public void healthRegen(int Heal){
        currentHealth += Heal;
        if(currentHealth > maxHealth){
            currentHealth = maxHealth;
        }
    }

    public void manaRegen(int Mana){
        currentMana += Mana;
        if(currentHealth > maxMana){
            currentMana = maxMana;
        }
    }

    abstract int getDamage();
    abstract void receiveDamage(int damageTaken);
}
