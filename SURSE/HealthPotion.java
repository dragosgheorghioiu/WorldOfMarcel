public class HealthPotion implements Potion{
    int Weight = 75;
    int Usage = 75;
    int Price = 2;

    public int getPrice() { return Price; }

    public int getUsage() { return Usage; }

    public int getWeight() { return Weight; }

    public void usePotion() {
        if (Weight != 0)
            Weight = 0;
    }
}
