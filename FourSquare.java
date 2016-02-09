import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class FourSquare
{
    
    public static int width;
    public static int height;
    public static String encOne; // Top-left encrypted string
    public static String encTwo; // Bottom-right encrypted string
    public static void init()
    {
        getsize();
        shuffle();
        
    }
    private static void getsize()
    {
        List<Integer> facts;
        int l;
        facts = new ArrayList<>();
        l = Common.alph.length();
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
    private static void shuffle() //creates encrypted strings
    {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < Common.alph.length(); i++)
        {
            temp.add(Common.alph.charAt(i));
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
    public String controller( String in, boolean choice)
    {
        if (Common.sanitize(in).length() % 2 != 0)
        {
            in = in + "x";
        }
        StringBuffer out = new StringBuffer();
        int i = 0;
        while (out.length() < in.length())
        {
            StringBuffer duff = new StringBuffer();
            while (duff.length() < 2)
            {
                System.out.println("Balls");
                duff.append(Common.sanitize(Character.toString(in.charAt(i))));
                if (Common.sanitize(Character.toString(in.charAt(i))) == "")
                {
                    out.append(in.charAt(i));
                }
                i++;
            }
            System.out.println("Arse");
            System.out.println(duff);
            if (choice)
            {
                out.append(encrypt(duff));
                //out.append("AA");
            }
            else
            {
                out.append(decrypt(duff));
            }
            duff.delete(0,1);
        }
        return out.toString();
    }
    private StringBuffer encrypt(StringBuffer in)
    {
        StringBuffer huff = new StringBuffer("");
        huff.append(encOne.charAt(Common.alph.indexOf(in.charAt(0))/width*width + Common.alph.indexOf(in.charAt(1))%width));
        huff.append(encTwo.charAt(Common.alph.indexOf(in.charAt(1))/width*width + Common.alph.indexOf(in.charAt(0))%width));
        return huff;
    }
    private StringBuffer decrypt(StringBuffer in)
    {
        StringBuffer puff = new StringBuffer("");
        puff.append(Common.alph.charAt(encOne.indexOf(in.charAt(0))/width*width + encTwo.indexOf(in.charAt(1))%width));
        puff.append(Common.alph.charAt(encTwo.indexOf(in.charAt(1))/width*width + encOne.indexOf(in.charAt(0))%width));
        return puff;
    }
        
}