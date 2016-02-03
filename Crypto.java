/**
 * Created by nsh on 2/3/16.
 */
public class Crypto {
    /*public static void spew(String s)
    {
        for ( int i = 0; i < s.length(); i++)
        {
            System.out.println(s.charAt(i));
            System.out.println(s.charAt(i) == '\n' || s.charAt(i) == ' ');
        }
    }
    */
    public static String alph = new String("abcdefghijklmnopqrstuvwxyz ");
    public static char encrypt(char work, boolean workUp, char key, boolean keyUp)
    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = getCharVal(work, workUp) + getCharVal(key, keyUp);
        //System.out.println(getChar);
        if (getChar >= alph.length())
        {
            getChar -= alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/
        if (! workUp)
        {
            return alph.charAt(getChar);
        }
        return Character.toUpperCase(alph.charAt(getChar));
    }
    public static char decrypt(char work, boolean workUp, char key, boolean keyUp)
    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = getCharVal(work, workUp) - getCharVal(key, keyUp);
        //System.out.println(getChar);
        if (getChar < 0)
        {
            getChar += alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/
        if (! workUp)
        {
            return alph.charAt(getChar);
        }
        return Character.toUpperCase(alph.charAt(getChar));
    }
    public static String sanitize(String keyIn)
    {
        StringBuffer out = new StringBuffer();
        StringBuffer checKey = new StringBuffer();
        for ( int i = 0; i < keyIn.length(); i++)
        {
            checKey.append(Character.toLowerCase(keyIn.charAt(i)));
            if (alph.contains(checKey))
            {
                out.append(checKey);
            }
            checKey.deleteCharAt(0);
        }
        return new String(out);
    }

    public static int getCharVal(char check, boolean isUp)
    {
        //System.out.println(check);
        //System.out.println(checkInt);
        if (isUp)
        {
            check = Character.toLowerCase(check);
        }

        //System.out.println(check);
        //System.out.println(checkInt);
        return alph.indexOf(check);
    }

    public static String controller(String work, String key, boolean choice)
    {
        StringBuffer checking = new StringBuffer();
        StringBuffer out = new StringBuffer();
        int keyPup = 0;
        for ( int i = 0; i < work.length(); i++)
        {
            checking.append(Character.toLowerCase(work.charAt(i)));
            //System.out.println(i);

            if (! Crypto.alph.contains(checking))
            {
                out.append(work.charAt(i));
            }
            else
            {
                if (choice)
                {
                    out.append(Crypto.encrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
                }
                else
                {
                    out.append(Crypto.decrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
                }
                keyPup +=1;
            }
            if (keyPup >= key.length())
            {
                keyPup -= key.length();
            }
            checking.deleteCharAt(0);
        }
        return new String(out);
    }
}
