import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class FourSquare
{
    public static List<Integer> facts;
    static int width;
    static int height;
    static int l;
    public static void getSize()
    {
        facts = new ArrayList<>();
        l = Vigenere.alph.length();
        for (int i = 1; i < Math.sqrt(l); i++)
        {
            if (l%i==0)
            {
                facts.add(i);
                facts.add(l/i);
            }
        }
        Collections.sort(facts);
        if (facts.size() % 2 == 1)
        {
            width = facts.get(facts.size()-facts.size()/2-1);
            height = facts.get(facts.size()-facts.size()/2-1);
        }
        else
        {
            width = facts.get(facts.size()/2);
            height = facts.get(facts.size()/2-1);
        }
        System.out.println(""+width+height+l+facts);
    }
    public void controller()
    {
    }
}