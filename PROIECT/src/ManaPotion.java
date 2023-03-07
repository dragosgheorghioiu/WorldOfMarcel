public class ManaPotion implements Potion{
    int Weight = 10;
    int Usage = 50;
    int Price = 1;

    public int getPrice() {
        return Price;
    }

    public int getUsage() {
        return Usage;
    }

    public int getWeight() {
        return Weight;
    }

    public void usePotion() {
        if (Weight != 0)
            Weight = 0;
    }
}

