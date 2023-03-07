public abstract class Spell {
    int damage;
    int manaCost;
}

//spelurile si damage ul lor
class Fire extends Spell{
    public Fire(){
        damage = 50;
        manaCost = 20;
    }
}

class Earth extends Spell{
    public Earth(){
        damage = 60;
        manaCost = 30;
    }
}

class Ice extends Spell{
    public Ice(){
        damage = 40;
        manaCost = 10;
    }
}