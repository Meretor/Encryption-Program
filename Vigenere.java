/**
 * Created by nsh on 2/3/16.
 */
public class Vigenere {
    /*public static void spew(String s)
    {
        for ( int i = 0; i < s.length(); i++)
        {
            System.out.println(s.charAt(i));
            System.out.println(s.charAt(i) == '\n' || s.charAt(i) == ' ');
        }
    }
    */

    public static String alph = new String("abcdefghijklmnopqrstuvwxyz��� ");
    public static char encrypt(char work, boolean workUp, char key) //Encrypts using Vigenere Cipher

    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = Common.alph.indexOf(Character.toLowerCase(work)) + Common.alph.indexOf(Character.toLowerCase(key));
        //System.out.println(getChar);
        if (getChar >= Common.alph.length())
        {
            getChar -= Common.alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/
        if (! workUp) //
        {
            return Common.alph.charAt(getChar);
        }
        return Character.toUpperCase(Common.alph.charAt(getChar));
    }
    private static char decrypt(char work, boolean workUp, char key) //Decrypts using Vigenere Cipher
    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = Common.alph.indexOf(Character.toLowerCase(work)) - Common.alph.indexOf(Character.toLowerCase(key));
        //System.out.println(getChar);
        if (getChar < 0)
        {
            getChar += Common.alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/
        if (! workUp)
        {
            return Common.alph.charAt(getChar);
        }
        return Character.toUpperCase(Common.alph.charAt(getChar));
    }
    
    /*
     * Controller function for Vigenere - The work is the plaintext, key is the key. Choice used to determine
     * whether the program should encrypt / decrypt. true = encrypt, false = decrypt.
     * In addition to determining whether to encrypt / decrypt, it passes through plaintext chars not in the 
     * alph string. Perhaps every kind of cipher should have this?
     */
    public static String controller(String work, String key, boolean choice)
    {
        StringBuffer checking = new StringBuffer(); //Tryna not pollute the string pool?
        StringBuffer out = new StringBuffer(); //Me too thanks.
        int keyPup = 0; //Keeps up with the string, used to loop the key properly. Essentially works as a two-variable forloop.
        for ( int i = 0; i < work.length(); i++) //Cycles through the string.
        {
            checking.append(Character.toLowerCase(work.charAt(i))); //used to check if the character is in the Alph string
            //System.out.println(i);

            if (! Common.alph.contains(checking))
            {
                out.append(work.charAt(i)); //Spits out the character if not in alph string.
            }
            else
            {
                if (choice)
                {
                    out.append(Vigenere.encrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup))); //Encrypts
                }
                else
                {
                    out.append(Vigenere.decrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup))); //Decrypts
                }
                keyPup +=1; //Increments key pos
            }
            if (keyPup >= key.length()) //Loops key pos
            {
                keyPup -= key.length();
            }
            checking.deleteCharAt(0); //Doesn't pollute string pool
        }
        return new String(out);
    }
}
