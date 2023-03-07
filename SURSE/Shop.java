import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement{
    LinkedList <Potion> Potions;
    public Shop(){
        Potions = new LinkedList();
        Random rand = new Random();
        int potionNum = rand.nextInt((4 - 2) + 1) + 2;
        for(int i = 0; i < potionNum; i++){
            if(rand.nextBoolean()){
                HealthPotion h= new HealthPotion();
                Potions.add(h);
            }

            else{
                ManaPotion m= new ManaPotion();
                Potions.add(m);
            }
        }
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

    public char toCharacter() {
        return 'S';
    }

    public void checkPotions(){
        for(int i = 0; i < Potions.size(); i++) {
            if(Potions.get(i).getWeight() == 0){
                Potions.remove(i);
            }
        }
    }
}
