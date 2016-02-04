import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class FourSquare
{
    public static List<Integer> facts;
    static int width;
    static int height;
    static int l;
    static String encOne; // Top-left encrypted string
    static String encTwo; // Bottom-right encrypted string
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
    public static void shuffle() //creates encrypted strings
    {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < Vigenere.alph.length(); i++)
        {
            temp.add(Vigenere.alph.charAt(i));
        }
        System.out.println(temp);
        Collections.shuffle(temp);
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < temp.size(); i++)
        {
            buff.append(temp.get(i));
        }
        Collections.shuffle(temp);
        StringBuffer guff = new StringBuffer();
        for (int i = 0; i < temp.size(); i++)
        {
            guff.append(temp.get(i));
        }
        encOne = buff.toString();
        encTwo = guff.toString();
        System.out.println(encOne);
        System.out.println(encTwo);
    }
}