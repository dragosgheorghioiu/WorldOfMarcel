import java.util.*;

public class Account {
    public Information i;
    public LinkedList <Character> s;
    public int nr_games;

    public Account(String email, String password){
        i = new Information(email, password);
        s = new LinkedList<Character>();
    }

    class Information{
        public Credentials c;
        public LinkedList <String> s;
        public String Country;
        public String Name;

        public Information(String email, String password){
            c = new Credentials(email, password);
            s = new LinkedList<>();
        }

    }
}
