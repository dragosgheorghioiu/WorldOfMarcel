import java.util.*;

public class Inventory {
    LinkedList <Potion> Potions;
    int maxWeight;
    int money = 0;

    public Inventory(){
        Potions = new LinkedList<Potion>();
    }

    void addPotion(Potion a){
        Potions.add(a);
    }

    void removePotion(Potion a){
        Potions.remove(a);
    }

    public int remainingWeight(){
        //calculez greutatea ramasa in inventar
        int currentWeight = maxWeight;

        for (int i = 0; i < Potions.size(); i++) {
            currentWeight -= Potions.get(i).getWeight();
        }

        return currentWeight;
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < Potions.size(); i++){
            result += (i+1);
            result += ": ";
            if(Potions.get(i) instanceof HealthPotion ){
                result += "HealthPotion";
            }
            else{
                result += "ManaPotion";
            }
            result += "\n";
        }

        return result;
    }

    public void checkPotions(){
        for(int i = 0; i < Potions.size(); i++) {
            if(Potions.get(i).getWeight() == 0){
                Potions.remove(i);
            }
        }
    }
}
